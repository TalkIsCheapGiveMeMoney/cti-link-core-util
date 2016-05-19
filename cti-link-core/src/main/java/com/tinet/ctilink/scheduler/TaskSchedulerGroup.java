package com.tinet.ctilink.scheduler;

/**
 * @author fengwei //
 * @date 16/5/19 09:55
 */

public class TaskSchedulerGroup {
    private String groupName;

    private int threadCount;

    public TaskSchedulerGroup(String groupName, int threadCount) {
        this.groupName = groupName;
        this.threadCount = threadCount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

}
