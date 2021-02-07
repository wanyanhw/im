package com.wanyan.core.service.impl;

import com.wanyan.core.dao.IMessageDao;
import com.wanyan.core.entity.MessageEntity;
import com.wanyan.core.model.MessageModel;
import com.wanyan.core.service.ImService;
import org.springframework.stereotype.Service;

/**
 * @author wanyanhw
 * @date 2021/2/7 11:20
 */
@Service
public class ImServiceImpl implements ImService {
    private IMessageDao messageDao;
    ImServiceImpl(IMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public boolean saveMsg(Integer type, String from, String to, String msg) {
        MessageModel messageModel = new MessageModel();
        messageModel.setType(type);
        messageModel.setFrom(from);
        messageModel.setTo(to);
        messageModel.setContent(msg);
        return messageDao.save(transformModel(messageModel));
    }

    private MessageEntity transformModel(MessageModel messageModel) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContentType(messageModel.getType());
        messageEntity.setContentFrom(messageModel.getFrom());
        messageEntity.setContentTo(messageModel.getTo());
        messageEntity.setContent(messageModel.getContent());
        messageEntity.setSendTime(messageModel.getTime());
        return messageEntity;
    }
}
