package com.tinet.ctilink.scheduler.clock;

import com.tinet.ctilink.cache.RedisService;

import java.util.Calendar;

/**
 * @author fengwei //
 * @date 16/5/19 09:58
 */
public class RedisClock implements Clock {
    private RedisService redisService;

    @Override
    public Calendar now() {
        long time = redisService.time();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
