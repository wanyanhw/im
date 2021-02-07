package com.wanyan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanyan.core.model.MessageModel;
import com.wanyan.core.service.ImService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/im")
@Api(tags = {"消息中心"})
public class ImController {
    @Autowired
    private ImService imService;

    @ApiOperation("接收消息")
    @GetMapping("/message/save/{from}")
    public String sendMsg(
            @PathVariable("from") String from,
            @ApiParam(name = "to", value = "接收者", required = true) @RequestParam(value = "to") String to,
            @ApiParam(name = "type", value = "消息类型", required = true) @RequestParam(value = "type") Integer type,
            @ApiParam(name = "content", value = "消息内容", required = true) @RequestParam(value = "content") String content) {
        boolean b = imService.saveMsg(type, from, to, content);
        return b ? "success" : "fail";
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
