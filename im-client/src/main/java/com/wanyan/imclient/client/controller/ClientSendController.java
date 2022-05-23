package com.wanyan.imclient.client.controller;

import com.wanyan.imclient.client.ClientCacheTemplate;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author wanyanhw
 * @date 2022/5/6 17:09
 */
@RestController
@RequestMapping("/im/client")
public class ClientSendController {

    private ClientCacheTemplate clientCacheTemplate = ClientCacheTemplate.instance();

    @PostMapping("/send")
    public String send(String name, String msg) {
        Channel channel = clientCacheTemplate.getChannel(name);
        return clientCacheTemplate.sendMsg(msg, channel);
    }

    @GetMapping("/online")
    public Set<String> online() {
        return clientCacheTemplate.onlineList();
    }

}
