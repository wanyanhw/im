package com.wanyan.imclient.client.controller;

import com.wanyan.imclient.client.CacheUtil;
import com.wanyan.imclient.client.ClientCacheTemplate;
import com.wanyan.imclient.client.ImClient;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanyanhw
 * @date 2022/5/6 17:09
 */
@RestController
@RequestMapping("/im/client")
public class ClientSendController {

    private ClientCacheTemplate clientCacheTemplate = ClientCacheTemplate.instance();

    @GetMapping("/login")
    public String login(String name) {
        CacheUtil.setClientName(name);
        new Thread(() -> {
            try {
                new ImClient().run("127.0.0.1", 8888);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return name;
    }

    @PostMapping("/send")
    public String send(String name, String msg) {
        Channel channel = clientCacheTemplate.getChannel(name);
        return clientCacheTemplate.sendMsg(msg, channel);
    }

    @GetMapping("/logout")
    public void logout() {
        new ImClient().close();
    }
}
