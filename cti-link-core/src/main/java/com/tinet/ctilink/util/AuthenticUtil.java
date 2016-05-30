package com.tinet.ctilink.util;

import com.tinet.ctilink.cache.RedisService;
import com.tinet.ctilink.inc.Const;

import java.util.concurrent.TimeUnit;

/**
 * @author fengwei //
 * @date 16/5/30 16:43
 */
public class AuthenticUtil {

    public static boolean validateFrequency(RedisService redisService, String key, String property, int limit) {
        //查询时间
        int second = getSecond(property);

        long count = redisService.incrby(Const.REDIS_DB_NON_CONFIGURE_INDEX, key, 1);
        if (count == 1) {
            redisService.expire(Const.REDIS_DB_NON_CONFIGURE_INDEX, key, second, TimeUnit.SECONDS);
        }
        if (count > limit) {
            //保护代码，万一重置key的超时时间出错，用来补救。
            if (redisService.ttl(Const.REDIS_DB_NON_CONFIGURE_INDEX, key) < 0) {
                redisService.expire(Const.REDIS_DB_NON_CONFIGURE_INDEX, key, second, TimeUnit.SECONDS);
                return true;
            }
            return false;
        }

        return true;
    }


    private static int getSecond(String property) {
        if (property.equals("second")) {
            return 1;
        } else if (property.equals("minute")) {
            return 60;
        } else if (property.equals("hour")) {
            return 60*60;
        } else if (property.equals("day")) {
            return 24*60*60;
        }

        return 60;
    }

    public static boolean isInWhiteIpList(String ip, String[] patternList) {
        for (String pattern : patternList) {
            if (ip.equals(pattern)) {
                return true;
            }

            String[] ipSub = ip.split("\\.");
            String[] patternSub = pattern.split("\\.");
            for (int i = 0; i < 3; i++) {
                if (patternSub[i].equals("*")) {
                    continue;
                }
                try {
                    if (!ipSub[i].equals(patternSub[i])
                            && Integer.parseInt(ipSub[i]) != Integer.parseInt(patternSub[i])) {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
        return false;
    }
}
