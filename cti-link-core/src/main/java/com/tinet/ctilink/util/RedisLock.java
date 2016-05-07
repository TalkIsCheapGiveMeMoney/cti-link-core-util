package com.tinet.ctilink.util;

import java.util.UUID;

/**
 * 通过Redis实现的分布式锁
 * 
 * @author Jiangsl
 *
 */
public class RedisLock {
	private String key;
	private final UUID uuid;
	private long lockTimeout;

	public RedisLock(String key, UUID uuid, long lockTimeout) {
		this.key = key;
		this.uuid = uuid;
		this.lockTimeout = lockTimeout;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public UUID getUuid() {
		return uuid;
	}

	public long getLockTimeout() {
		return lockTimeout;
	}

	public void setLockTimeout(long lockTimeout) {
		this.lockTimeout = lockTimeout;
	}
}
