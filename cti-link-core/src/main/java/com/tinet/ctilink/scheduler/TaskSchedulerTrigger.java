package com.tinet.ctilink.scheduler;

import java.util.Map;

/**
 * @author fengwei //
 * @date 16/5/19 13:04
 */
public interface TaskSchedulerTrigger {
    void taskTriggered(String taskId, Map<String, Object> param);
}
