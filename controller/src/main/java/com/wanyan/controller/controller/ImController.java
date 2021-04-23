package com.wanyan.controller.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanyan.controller.BaseResponse;
import com.wanyan.controller.model.MessageModel;
import com.wanyan.controller.service.ImService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@Api(value = "ImController", tags = {"消息中心"})
@RestController
@RequestMapping("/im")
public class ImController {
    @Reference
    private ImService imService;

    @ApiOperation("接收消息")
    @GetMapping("/message/save/{from}")
    public BaseResponse sendMsg(
            @PathVariable("from") String from,
            @ApiParam(name = "to", value = "接收者", required = true) @RequestParam(value = "to") String to,
            @ApiParam(name = "type", value = "消息类型", required = true) @RequestParam(value = "type") Integer type,
            @ApiParam(name = "content", value = "消息内容", required = true) @RequestParam(value = "content") String content) {
        try {
            return imService.saveMsg(type, from, to, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
