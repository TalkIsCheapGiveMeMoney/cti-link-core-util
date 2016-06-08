package com.tinet.ctilink.mq;

import java.util.List;

/**
 * @author fengwei //
 * @date 16/6/7 14:52
 */
public interface MessageQueue {
    <T> Boolean sendMessage(T t);

    <T> List<T> receiveMessage(Class<T> clazz);

    Integer getWaitCount();

    Integer getDealingCount();
}
