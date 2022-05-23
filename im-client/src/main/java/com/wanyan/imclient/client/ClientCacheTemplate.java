package com.wanyan.imclient.client;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanyanhw
 * @date 2022/5/6 17:26
 */
public class ClientCacheTemplate {
    private ClientCacheTemplate() {}

    private final static ClientCacheTemplate INSTANCE = new ClientCacheTemplate();

    public static ClientCacheTemplate instance() {
        return INSTANCE;
    }

    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public void saveChannel(Channel channel) {
        channelMap.putIfAbsent(channel.id().asLongText(), channel);
    }

    public Channel getChannel(String name) {
        return channelMap.get(name);
    }

    public String sendMsg(String msg, Channel channel) {
        if (channel.isActive()) {
            channel.write(msg);
            channel.flush();
            return "success";
        }
        return "fail";
    }

    public Set<String> onlineList() {
        return channelMap.keySet();
    }
}
