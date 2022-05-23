package com.wanyan.imclient.client;

/**
 * @author wanyanhw
 * @date 2022/5/23 19:43
 */
public class CacheUtil {

    private static String clientName = null;

    public static void setClientName(String name) {
        clientName = name;
    }

    public static String getClient() {
        return clientName;
    }
}
