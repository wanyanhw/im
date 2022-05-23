package com.wanyan.imclient.client;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanyanhw
 * @date 2022/5/23 15:28
 */
public class LogUtil {

    private static final SimpleDateFormat SIMPLE_DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

    public static String buildLog(String clientId, String msg) {
        String log = "%s 客户端: %s, 消息: %s";
        return String.format(log, SIMPLE_DATA_FORMAT.format(new Date()), clientId, msg);
    }
}
