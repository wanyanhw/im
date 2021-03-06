package com.wanyan.controller.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanyan.controller.BaseResponse;
import com.wanyan.controller.model.MessageModel;

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
     * @return BaseResponse
     */
    BaseResponse saveMsg(Integer type, String from, String to, String msg) throws Exception;

    /**
     * 分页查询消息记录
     * @param from 发送者
     * @param pageNo 页码
     * @param pageSize 页大小
     * @return page
     */
    Page<MessageModel> pageMsg(String from, Integer pageNo, Integer pageSize);
}
