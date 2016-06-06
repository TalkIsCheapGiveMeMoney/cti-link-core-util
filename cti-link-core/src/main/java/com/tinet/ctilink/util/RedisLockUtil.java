package com.tinet.ctilink.util;

import java.util.Collections;
import java.util.UUID;

import com.tinet.ctilink.cache.RedisTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * 通过Redis实现的分布式锁
 * 
 * @author Jiangsl
 *
 */
public class RedisLockUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisLockUtil.class);
	
	private static final long DEFAULT_LOCK_TIME_OUT = 3000; // 锁的过期时间，单位毫秒
	private static final long DEFAULT_TRY_LOCK_TIME_OUT = 0;// 争抢锁的超时时间，单位毫秒，0代表永不超时（一直抢到锁为止）
	private static final String LUA_SCRIPT_LOCK = "return redis.call('SET', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2]) ";
	private static final String LUA_SCRIPT_UNLOCK = 
			"if (redis.call('GET', KEYS[1]) == ARGV[1]) then "
			+ "return redis.call('DEL',KEYS[1]) " 
			+ "else " + "return 0 " + "end";

	private static RedisScript<String> scriptLock = new DefaultRedisScript<String>(LUA_SCRIPT_LOCK, String.class);
	private static RedisScript<String> scriptUnlock = new DefaultRedisScript<String>(LUA_SCRIPT_UNLOCK,
			String.class);

	/**
	 * 获取锁
	 * 
	 * @param key
	 * @return
	 */
	public static RedisLock lock(String key) {
		return lock(key, DEFAULT_LOCK_TIME_OUT, DEFAULT_TRY_LOCK_TIME_OUT);
	}

	/**
	 * 获取锁，指定锁过期时间
	 * 
	 * @param key
	 * @param lockTimeout
	 * @return
	 */
	public static RedisLock lock(String key, long lockTimeout) {
		return lock(key, lockTimeout, DEFAULT_TRY_LOCK_TIME_OUT);
	}

	/**
	 * 获取锁，指定锁过期时间/争抢锁超时时间
	 * 
	 * @param key
	 * @param lockTimeout
	 * @param tryLockTimeout
	 * @return
	 */
	public static RedisLock lock(String key, long lockTimeout, long tryLockTimeout) {
		key = key + ".lock";
		long timestamp = System.currentTimeMillis();
		UUID uuid = UUID.randomUUID();
		RedisTemplate redisTemplate =  ContextUtil.getBean(RedisTemplate.class);
		while (tryLockTimeout == 0 || (System.currentTimeMillis() - timestamp) < tryLockTimeout) {
			String result = redisTemplate.execute(scriptLock, redisTemplate.getStringSerializer(),redisTemplate.getStringSerializer(),Collections.singletonList(key),uuid.toString(),
					String.valueOf(lockTimeout));
			if (result != null && result.equals("OK")) {
				return new RedisLock(key, uuid, lockTimeout);
			} else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		logger.error("Fail to get lock key:{},lockTimeout:{},tryLockTimeout:{}", key, lockTimeout, tryLockTimeout);
		return null;
	}

	/**
	 * 释放锁
	 * 
	 * @param lock
	 */
	public static void unLock(RedisLock lock) {
		RedisTemplate redisTemplate =  ContextUtil.getBean(RedisTemplate.class);
		redisTemplate.execute(scriptUnlock,redisTemplate.getStringSerializer(),redisTemplate.getStringSerializer(), Collections.singletonList(lock.getKey()), lock.getUuid().toString());
	}
}
