package com.wanyan.core.dao.impl;

import com.wanyan.core.entity.MessageEntity;
import com.wanyan.core.mapper.MessageMapper;
import com.wanyan.core.dao.IMessageDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-07
 */
@Service
public class MessageDaoImpl extends ServiceImpl<MessageMapper, MessageEntity> implements IMessageDao {

}
