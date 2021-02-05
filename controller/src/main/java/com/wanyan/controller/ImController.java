package com.wanyan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/im")
@Api(tags = {"消息中心"})
public class ImController {

    @ApiOperation("发送消息")
    @GetMapping("/websocket/{name}")
    public String sendMsg(
            @PathVariable String name,
            @ApiParam(name = "msg", required = true) @RequestParam(value = "msg") String msg) {
        return "hello, " + name;
    }
}
