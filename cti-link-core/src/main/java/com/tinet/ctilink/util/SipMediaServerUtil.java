package com.tinet.ctilink.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author fengwei //
 * @date 16/5/11 17:04
 */
public class SipMediaServerUtil {

    public static Integer getInstanceId(String uniqueId) {
        if (StringUtils.isEmpty(uniqueId)) {
            return null;
        }
        String[] uniqueIdArr = uniqueId.split("-");
        if (uniqueIdArr.length > 1 && StringUtils.isNumeric(uniqueIdArr[1])) {
            return Integer.parseInt(uniqueIdArr[1]);
        }

        return null;
    }
}
