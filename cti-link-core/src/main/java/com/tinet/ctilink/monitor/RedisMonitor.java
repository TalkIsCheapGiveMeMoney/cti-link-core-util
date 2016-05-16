package com.tinet.ctilink.monitor;

import java.lang.reflect.Field;

import com.tinet.ctilink.jedis.CtiLinkJedisConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * 通过JMX暴露Redis连接池统计信息，用于监控
 * 
 * @author Jiangsl
 *
 */
public class RedisMonitor {

	@Autowired
	private CtiLinkJedisConnectionFactory jedisConnectionFactory;

	private Pool<Jedis> pool;

	public int getActive() {
		return getPool().getNumActive();
	}

	public int getIdle() {
		return getPool().getNumIdle();
	}

	public int getWaiters() {
		return getPool().getNumWaiters();
	}

	public long getMaxBorrowWaitTimeMillis() {
		return getPool().getMaxBorrowWaitTimeMillis();
	}

	public long getMeanBorrowWaitTimeMillis() {
		return getPool().getMeanBorrowWaitTimeMillis();
	}

	public long getMaxTotal() {
		return jedisConnectionFactory.getPoolConfig().getMaxTotal();
	}

	public long getMaxIdle() {
		return jedisConnectionFactory.getPoolConfig().getMaxIdle();
	}

	public long getMinIdle() {
		return jedisConnectionFactory.getPoolConfig().getMinIdle();
	}

	/**
	 * JedisConnectionFactory中的连接池对象是private的，不能直接访问，只好通过反射获取
	 * 
	 * @return
	 */
	private Pool<Jedis> getPool() {
		if (pool != null) {
			return pool;
		}

		try {
			Field field = CtiLinkJedisConnectionFactory.class.getDeclaredField("pool");
			field.setAccessible(true);
			pool = (Pool<Jedis>) field.get(jedisConnectionFactory);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return pool;
	}

}
