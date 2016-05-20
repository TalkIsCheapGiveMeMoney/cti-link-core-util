package com.tinet.ctilink.scheduler;

import com.tinet.ctilink.json.JSONObject;

/**
 * @author fengwei //
 * @date 16/5/19 10:26
 */
class SchedulerTask {
    public static final int TASK_TYPE_PERIODIC = 1;

    public static final int TASK_TYPE_TIMED = 2;

    private String taskId;

    private int taskType;
    //任务
    private String taskTriggerName;

    //periodic
    private int period;

    //timed
    private String groupName;

    public SchedulerTask() {}

    public SchedulerTask(String taskId, String taskTriggerName, int period) {
        this.taskId = taskId;
        this.taskTriggerName = taskTriggerName;
        this.period = period;
        this.taskType = TASK_TYPE_PERIODIC;
    }

    public SchedulerTask(String groupName, String taskId, String taskTriggerName) {
        this.taskId = taskId;
        this.taskTriggerName = taskTriggerName;
        this.groupName = groupName;
        this.taskType = TASK_TYPE_TIMED;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskTriggerName() {
        return taskTriggerName;
    }

    public void setTaskTriggerName(String taskTriggerName) {
        this.taskTriggerName = taskTriggerName;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return JSONObject.getJSONString(this);
    }

}