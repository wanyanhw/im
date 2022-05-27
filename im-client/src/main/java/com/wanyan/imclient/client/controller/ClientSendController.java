package com.wanyan.imclient.client.controller;

import com.wanyan.imclient.client.CacheUtil;
import com.wanyan.imclient.client.ImClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wanyanhw
 * @date 2022/5/6 17:09
 */
@RestController
@RequestMapping("/im/client")
public class ClientSendController {

    @Resource
    private ImClient client;

    @GetMapping("/login")
    public String login(String name, String password) {
        // 1、校验账号密码
        // 2、校验账号登录状态
        // 3、连接服务器
        // 4、通知服务器：客户端已上线，并绑定账号和通道关系
        CacheUtil.setClientName(name);
        client.run("192.168.100.10", 8888);
        return name;
    }

    @PostMapping("/send")
    public String send(String msg) {
        msg = "{\"msg\":\"" + msg +"\"}";
        client.sendMsg(msg);
        return "success";
    }

    @GetMapping("/logout")
    public void logout() {
        // 1、通知服务器：客户端将要离线，解除账号与通道绑定关系
        // 2、关闭通道
        client.closeChannel();
    }
}
