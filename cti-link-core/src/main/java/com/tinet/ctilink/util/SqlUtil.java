package com.tinet.ctilink.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author fengwei //
 * @date 16/4/19 13:44
 */
public class SqlUtil {

    /**
     * sql escape
     * 其它字符待完善
     */
    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }
}
