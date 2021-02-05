package com.wanyan.core.config.websocekt;

import com.wanyan.core.em.MessageTypeEnum;
import com.wanyan.core.exception.EnumNoParamException;
import com.wanyan.core.model.MessageModel;
import com.wanyan.util.JsonUtil;
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
        log.info("[{}] 建立socket连接成功", sid);
    }

    @OnClose
    public void onClose() {
        clientMap.remove(this.sid);
        log.info("[{}] 退出socket连接", this.sid);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("socket连接异常，{}", error.getMessage());
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        log.info("from: [{}], msg: [{}]", this.sid, msg);
        MessageModel messageModel = JsonUtil.parseJson(msg, MessageModel.class);
        handle(messageModel);
    }

    private void handle(MessageModel messageModel) {
        int type = messageModel.getType();
        String msg = messageModel.getContent();
        MessageTypeEnum typeEnum = null;
        try {
            typeEnum = MessageTypeEnum.getByCode(type);
        } catch (EnumNoParamException e) {
            e.printStackTrace();
            return;
        }
        switch (typeEnum) {
            case GROUP:
                sendMsg2All(msg);
                break;
            case PERSONAL:
                sendMsg(msg);
                break;
            default:
                break;
        }
    }

    private void sendMsg2All(String msg) {
        // TODO: 2021/2/5 发送给群组内所有客户
    }

    private void sendMsg(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}