package com.wanyan.core.service;

import com.wanyan.core.model.MessageModel;

/**
 * @author wanyanhw
 * @date 2021/2/7 11:22
 */
public interface ImService {
    /**
     * 消息入库
     * @param type 消息类型
     * @param from 消息来源
     * @param to 消息接收
     * @param msg 消息内容
     * @return boolean
     */
    boolean saveMsg(Integer type, String from, String to, String msg);
}
