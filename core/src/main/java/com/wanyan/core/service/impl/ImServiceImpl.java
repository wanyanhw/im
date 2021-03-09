package com.wanyan.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanyan.controller.BaseResponse;
import com.wanyan.controller.service.ImService;
import com.wanyan.core.dao.IMessageDao;
import com.wanyan.core.entity.MessageEntity;
import com.wanyan.controller.model.MessageModel;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanyanhw
 * @date 2021/2/7 11:20
 */
@Service
public class ImServiceImpl implements ImService {
    @Autowired
    private IMessageDao messageDao;

    @Override
    public BaseResponse saveMsg(Integer type, String from, String to, String msg) {
        MessageModel messageModel = new MessageModel();
        messageModel.setType(type);
        messageModel.setFrom(from);
        messageModel.setTo(to);
        messageModel.setContent(msg);
        BaseResponse baseResponse = new BaseResponse();
        boolean save = messageDao.save(transformModel(messageModel));
        if (save) {
            return baseResponse;
        }
        return baseResponse.setCode(100001).setMsg("保存失败");
    }

    @Override
    public Page<MessageModel> pageMsg(String from, Integer pageNo, Integer pageSize) {
        IPage<MessageEntity> page = messageDao.page(new Page<>(pageNo, pageSize), new LambdaQueryWrapper<MessageEntity>().eq(MessageEntity::getContentFrom, from).eq(MessageEntity::getDeleted, 0));
        return new Page<MessageModel>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(transformEntityList(page.getRecords()));
    }

    private List<MessageModel> transformEntityList(List<MessageEntity> records) {
        return records.stream().map(this::transformEntity).collect(Collectors.toList());
    }

    private MessageModel transformEntity(MessageEntity entity) {
        MessageModel model = new MessageModel();
        model.setType(entity.getContentType());
        model.setFrom(entity.getContentFrom());
        model.setTo(entity.getContentTo());
        model.setContent(entity.getContent());
        model.setTime(entity.getSendTime());
        return model;
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
