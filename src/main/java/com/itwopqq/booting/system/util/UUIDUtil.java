package com.itwopqq.booting.system.util;

import java.util.UUID;

/**
 * @author fanzhen
 * @desx
 * @date 2020-03-31
 */
public class UUIDUtil {

    public static String getUUID(){

        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
