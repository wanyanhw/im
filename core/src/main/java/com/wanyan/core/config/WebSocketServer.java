package com.wanyan.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{sid}")
@Slf4j
public class WebSocketServer {

    private Map<String, WebSocketServer> clientMap = new ConcurrentHashMap<>();

    private Session session;

    private String sid;

    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session) {
        this.session = session;
        clientMap.put(sid, this);
        this.sid = sid;
        log.info("[{}] 连接成功", sid);
    }

    @OnClose
    public void onClose() {
        clientMap.remove(this.sid);
        log.info("from: [{}] 退出连接", this.sid);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("连接异常，{}", error.getMessage());
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        log.info("from: [{}], msg: [{}]", this.sid, msg);
        sendMsg(msg);
        // TODO: 2021/2/5 处理收到的消息
    }

    private void sendMsg(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}