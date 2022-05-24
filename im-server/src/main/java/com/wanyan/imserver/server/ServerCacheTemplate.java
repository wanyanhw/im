package com.wanyan.imserver.server;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanyanhw
 * @date 2022/5/6 17:26
 */
public class ServerCacheTemplate {

    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public Set<String> onlineList() {
        return channelMap.keySet();
    }

    public void saveChannel(String name, Channel channel) {
        channelMap.put(name, channel);
    }

    public Channel getChannel(String name) {
        return channelMap.get(name);
    }

    public String sendMsg(String msg, Channel channel) {
        if (channel.isActive()) {
            channel.writeAndFlush(msg);
            return "success";
        }
        return "fail";
    }

    public void sendToAll(String msg) {
        channelMap.values().forEach(channel -> {
            channel.writeAndFlush(msg);
        });
    }

    public void clear() {
        channelMap.clear();
    }

    public String getClientName(String channelId) {
        for (Map.Entry<String, Channel> channelMap : channelMap.entrySet()) {
            Channel channel = channelMap.getValue();
            if (channelId.equals(channel.id().asLongText())) {
                return channelMap.getKey();
            }
        }
        return null;
    }
}
