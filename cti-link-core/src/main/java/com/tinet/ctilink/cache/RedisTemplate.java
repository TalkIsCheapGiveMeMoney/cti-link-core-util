package com.tinet.ctilink.cache;

import com.tinet.ctilink.jedis.CtiLinkJedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author fengwei //
 * @date 16/5/11 11:18
 */
public class RedisTemplate extends StringRedisTemplate {
    public static ThreadLocal<Integer> LOCAL_DB_INDEX = new ThreadLocal<>();

    //
    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        try {
            Integer dbIndex = RedisTemplate.LOCAL_DB_INDEX.get();
            //如果设置了dbIndex
            if (dbIndex != null) {
                if (connection instanceof CtiLinkJedisConnection) {
                    if (((CtiLinkJedisConnection) connection).getDbIndex() != dbIndex) {
                        connection.select(dbIndex);
                    } else {
                        //System.out.println("no selectdb");
                    }
                } else {
                    connection.select(dbIndex);
                }
            } else {
                connection.select(0);
            }
        } finally {
            RedisTemplate.LOCAL_DB_INDEX.remove();
        }
        return super.preProcessConnection(connection, existingConnection);
    }
}
