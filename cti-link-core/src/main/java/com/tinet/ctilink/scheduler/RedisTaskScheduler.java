package com.tinet.ctilink.scheduler;

import com.tinet.ctilink.cache.RedisService;
import com.tinet.ctilink.inc.Const;
import com.tinet.ctilink.json.JSONObject;
import com.tinet.ctilink.scheduler.clock.Clock;
import com.tinet.ctilink.scheduler.clock.StandardClock;
import com.tinet.ctilink.util.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;

public class RedisTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(RedisTaskScheduler.class);

    private static final String SCHEDULE_KEY = "redis-scheduler.%s";
    private static final String DEFAULT_SCHEDULER_NAME = "redisTaskScheduler";
    private Map<String, ExecutorService> timedPools = new ConcurrentHashMap<>();
    private Map<String, ExecutorService> periodicPools = new ConcurrentHashMap<>();

    private Map<String, String> periodicTasks = new ConcurrentHashMap<>();

    /**
     * clock
     */
    private Clock clock = new StandardClock();

    private RedisService redisService;

    //多实例, 必须都注册
    private Map<String, TaskSchedulerGroup> taskSchedulerGroupMap = new ConcurrentHashMap<>();

    /**
     * Delay between each polling of the scheduled tasks. The lower the value, the best precision in triggering tasks.
     * However, the lower the value, the higher the load on Redis.
     */
    private int pollingDelayMillis = 10000;

    /**
     * If you need multiple schedulers for the same application, customize their names to differentiate in logs.
     */
    private String schedulerName = DEFAULT_SCHEDULER_NAME;

    private PollingThread pollingThread;
    private int maxRetriesOnConnectionFailure = 1;


    /**
     * 周期性调用
     * @param taskId  任务id, 必须唯一
     * @param taskTriggerName  回调的trigger
     * @param period  周期, 毫秒
     * @param threadCount 任务的执行线程数
     */
    public void schedulePeriod(String taskId, String taskTriggerName, Map taskTriggerParam, int period, int threadCount) {
        String taskJson = new SchedulerTask(taskId, taskTriggerName, taskTriggerParam, period).toString();
        //创建周期性任务
        redisService.zadd(Const.REDIS_DB_CTI_INDEX, keyForScheduler(), taskJson, clock.now().getTimeInMillis() + period);
        //初始化线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        periodicPools.put(taskId, executorService);
        periodicTasks.put(taskId, taskJson);
    }

    /**
     * 注册group, 用户定时调用
     * @param taskSchedulerGroup, groupName和线程数
     */
    public void registerTaskSchedulerGroup(TaskSchedulerGroup taskSchedulerGroup) {
        if (getTaskSchedulerGroup(taskSchedulerGroup.getGroupName()) != null) {
            throw new RuntimeException("schedulerTask scheduler group exist");
        }
        this.taskSchedulerGroupMap.put(taskSchedulerGroup.getGroupName(), taskSchedulerGroup);
        //初始化线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSchedulerGroup.getThreadCount());
        timedPools.put(taskSchedulerGroup.getGroupName(), pool);
    }

    /**
     * 定时调用
     * @param groupName 使用前需要注册
     * @param taskId  任务id, 必须唯一
     * @param taskTriggerName  回调的trigger
     * @param time 执行时间, 精确到毫秒
     */
    public void scheduleTimed(String groupName, String taskId, String taskTriggerName, Map taskTriggerParam, long time) {
        TaskSchedulerGroup taskSchedulerGroup = getTaskSchedulerGroup(groupName);
        if (taskSchedulerGroup == null) {
            throw new RuntimeException("schedulerTask scheduler group not exist");
        }
        String taskJson = new SchedulerTask(groupName, taskId, taskTriggerName, taskTriggerParam).toString();
        //创建定时任务
        redisService.zadd(Const.REDIS_DB_CTI_INDEX, keyForScheduler(), taskJson, time);
    }

    @SuppressWarnings("unchecked")
    public void unschedule(String taskId) {
        if (periodicTasks.containsKey(taskId)) {
            redisService.zrem(Const.REDIS_DB_CTI_INDEX, keyForScheduler(), periodicTasks.get(taskId));
        } else {
            String pattern = "{\"taskId\":\"" + taskId + "\",*";
            ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();

            Cursor<ZSetOperations.TypedTuple<String>> cursor
                    = redisService.zscan(Const.REDIS_DB_CTI_INDEX, keyForScheduler(), scanOptions);
            try {
                while (cursor.hasNext()) {
                    ZSetOperations.TypedTuple<String> stringTypedTuple = cursor.next();
                    String taskJson = stringTypedTuple.getValue();
                    SchedulerTask schedulerTask = JSONObject.getBean(taskJson, SchedulerTask.class);
                    if (schedulerTask.getTaskId().equals(taskId)) {
                        redisService.zrem(Const.REDIS_DB_CTI_INDEX, keyForScheduler(), taskJson);
                        return;
                    }
                }
            } catch (Exception e) {
                log.error("unschedule error, taskId:" + taskId, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void unscheduleAllTasks() {
        redisService.delete(Const.REDIS_DB_CTI_INDEX, keyForScheduler());
    }

    @PostConstruct
    public void initialize() {
        pollingThread = new PollingThread();
        pollingThread.setName(schedulerName + "-polling");

        pollingThread.start();

        log.info(String.format("[%s] Started Redis Scheduler (polling freq: [%sms])", schedulerName, pollingDelayMillis));
    }

    @PreDestroy
    public void destroy() {
        if (pollingThread != null) {
            pollingThread.requestStop();
        }
        //关闭线程池
        for (Map.Entry<String, ExecutorService> entry : periodicPools.entrySet()) {
           entry.getValue().shutdown();
        }

        for (Map.Entry<String, ExecutorService> entry : timedPools.entrySet()) {
            entry.getValue().shutdown();
        }
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public void setPollingDelayMillis(int pollingDelayMillis) {
        this.pollingDelayMillis = pollingDelayMillis;
    }

    public void setMaxRetriesOnConnectionFailure(int maxRetriesOnConnectionFailure) {
        this.maxRetriesOnConnectionFailure = maxRetriesOnConnectionFailure;
    }

    private TaskSchedulerGroup getTaskSchedulerGroup(String groupName) {
        return taskSchedulerGroupMap.get(groupName);
    }

    private String keyForScheduler() {
        return String.format(SCHEDULE_KEY, schedulerName);
    }

    @SuppressWarnings("unchecked")
    private boolean triggerNextTaskIfFound() {

        return (Boolean) redisService.execute(Const.REDIS_DB_CTI_INDEX, new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                boolean taskWasTriggered = false;
                final String key = keyForScheduler();

                redisOperations.watch(key);

                String taskJson = findFirstTaskDueForExecution(redisOperations);

                if (taskJson == null) {
                    redisOperations.unwatch();
                } else {
                    redisOperations.multi();
                    //获取执行时间
                    redisOperations.opsForZSet().score(key, taskJson);
                    redisOperations.opsForZSet().remove(key, taskJson);
                    List<Object> resultList = redisOperations.exec();
                    boolean executionSuccess = (resultList != null);

                    if (executionSuccess) {
                        long time = ((Double) resultList.get(0)).longValue();
                        SchedulerTask schedulerTask = JSONObject.getBean(taskJson, SchedulerTask.class);
                        if (schedulerTask != null) {
                            //周期任务, 重新放到list  定时任务将任务删除
                            if (schedulerTask.getTaskType() == SchedulerTask.TASK_TYPE_PERIODIC) {
                                redisOperations.opsForZSet().add(key, taskJson, time + schedulerTask.getPeriod());
                            }

                            log.debug(String.format("[%s] Triggering execution of schedulerTask [%s]", schedulerName, taskJson));
                            tryTaskExecution(taskJson, schedulerTask, redisOperations);
                        }

                        taskWasTriggered = true;
                    } else {
                        log.warn(String.format("[%s] Race condition detected for triggering of schedulerTask [%s]. " +
                                "The schedulerTask has probably been triggered by another instance of this application.", schedulerName, taskJson));
                    }
                }

                return taskWasTriggered;
            }
        });
    }


    private void tryTaskExecution(String taskJson, SchedulerTask schedulerTask, RedisOperations redisOperations) {
        ExecutorService pool;
        try {
            if (schedulerTask.getTaskType() == SchedulerTask.TASK_TYPE_PERIODIC) {
                pool = periodicPools.get(schedulerTask.getTaskId());
            } else {
                pool = timedPools.get(schedulerTask.getGroupName());
            }
            if (pool != null) {
                pool.submit(() -> {
                        TaskSchedulerTrigger taskSchedulerTrigger =
                                (TaskSchedulerTrigger) ContextUtil.getContext().getBean(schedulerTask.getTaskTriggerName());
                        taskSchedulerTrigger.taskTriggered(schedulerTask.getTaskId(), schedulerTask.getTaskTriggerParam());
                });
            } else {
                //删除任务
                redisOperations.opsForZSet().remove(keyForScheduler(), taskJson);
                log.error("pool is null, remove task, taskId:" + schedulerTask.getTaskId());
            }
        } catch (Exception e) {
            log.error(String.format("[%s] Error during execution of schedulerTask [%s]", schedulerName, schedulerTask.getTaskId()), e);
        }
    }

    @SuppressWarnings("unchecked")
    private String findFirstTaskDueForExecution(RedisOperations ops) {
        final long minScore = 0;
        final long maxScore = clock.now().getTimeInMillis();

        // we unfortunately need to go wild here, the default API does not allow us to limit the number
        // of items returned by the ZRANGEBYSCORE operation.
        Set<byte[]> found = (Set<byte[]>) ops.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String key = keyForScheduler();
                return redisConnection.zRangeByScore(key.getBytes(), minScore, maxScore, 0, 1);
            }
        });

        String foundTask = null;
        if (found != null && !found.isEmpty()) {
            byte[] valueRaw = found.iterator().next();
            Object valueObj = ops.getValueSerializer().deserialize(valueRaw);
            foundTask = (valueObj != null) ? valueObj.toString() : null;
        }

        return foundTask;
    }

    private class PollingThread extends Thread {
        private boolean stopRequested = false;
        private int numRetriesAttempted = 0;

        public void requestStop() {
            stopRequested = true;
        }

        @Override
        public void run() {
            try {
                while (!stopRequested && !isMaxRetriesAttemptsReached()) {

                    try {
                        attemptTriggerNextTask();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            } catch (Exception e) {
                log.error(String.format(
                        "[%s] Error while polling scheduled tasks. " +
                                "No additional scheduled schedulerTask will be triggered until the application is restarted.", schedulerName), e);
            }

            if (isMaxRetriesAttemptsReached()) {
                log.error(String.format("[%s] Maximum number of retries (%s) after Redis connection failure has been reached. " +
                        "No additional scheduled schedulerTask will be triggered until the application is restarted.", schedulerName, maxRetriesOnConnectionFailure));
            } else {
                log.info("[%s] Redis Scheduler stopped");
            }
        }

        private void attemptTriggerNextTask() throws InterruptedException {
            try {
                boolean taskTriggered = triggerNextTaskIfFound();

                // if a schedulerTask was triggered, we'll try again immediately. This will help to speed up the execution
                // process if a few tasks were due for execution.
                if (!taskTriggered) {
                    sleep(pollingDelayMillis);
                }

                resetRetriesAttemptsCount();
            } catch (RedisConnectionFailureException e) {
                incrementRetriesAttemptsCount();
                log.warn(String.format("Connection failure during scheduler polling (attempt %s/%s)", numRetriesAttempted, maxRetriesOnConnectionFailure));
            }
        }

        private boolean isMaxRetriesAttemptsReached() {
            return numRetriesAttempted >= maxRetriesOnConnectionFailure;
        }

        private void resetRetriesAttemptsCount() {
            numRetriesAttempted = 0;
        }

        private void incrementRetriesAttemptsCount() {
            numRetriesAttempted++;
        }
    }
}
