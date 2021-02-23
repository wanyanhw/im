package com.wanyan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanyan.core.config.BaseResponse;
import com.wanyan.core.model.MessageModel;
import com.wanyan.core.service.ImService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"消息中心"})
@RestController
@RequestMapping("/im")
public class ImController {
    @Autowired
    private ImService imService;

    @ApiOperation("接收消息")
    @GetMapping("/message/save/{from}")
    public BaseResponse sendMsg(
            @PathVariable("from") String from,
            @ApiParam(name = "to", value = "接收者", required = true) @RequestParam(value = "to") String to,
            @ApiParam(name = "type", value = "消息类型", required = true) @RequestParam(value = "type") Integer type,
            @ApiParam(name = "content", value = "消息内容", required = true) @RequestParam(value = "content") String content) {
        return imService.saveMsg(type, from, to, content);
    }

    @ApiOperation("消息记录")
    @GetMapping("/message/page/{from}")
    public Page<MessageModel> pageMsg(
            @PathVariable("from") String from,
            @ApiParam(name = "pageNo", value = "页码", required = true) @RequestParam(value = "pageNo") Integer pageNo,
            @ApiParam(name = "pageSize", value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        return imService.pageMsg(from, pageNo, pageSize);
    }


}
