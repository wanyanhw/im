package com.wanyan.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{sid}")
@Slf4j
public class WebSocket {

    private Map<String, WebSocket> client = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session) {
        this.session = session;
        client.put(sid, this);
    }

    @OnClose
    public void onClose(Session session, Throwable error) {
        log.error("连接异常，{}", error.getMessage());
    }

    @OnError
    public void onError() {

    }

    @OnMessage
    public void onMessage(String message) {

    }

}