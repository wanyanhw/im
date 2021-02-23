package com.wanyan.controller;

import com.wanyan.core.config.BaseResponse;
import com.wanyan.core.model.AccountBaseModel;
import com.wanyan.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanyanhw
 * @date 2021/2/11 11:11
 */
@Api(tags = {"账户中心"})
@RestController
@RequestMapping("/account")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @return String
     */
    @PostMapping("/subscribe")
    public BaseResponse register(@RequestBody AccountBaseModel account) {
        return userService.subscribe(account);
    }

    /**
     * 注销账户
     * @return String
     */
    @GetMapping("/unsubscribe")
    public BaseResponse unsubscribe(
            @ApiParam(name = "userId", value = "用户账号", required = true) @RequestParam(value = "userId") String userId,
            @ApiParam(name = "password", value = "密码", required = true) @RequestParam(value = "password") String password) {
        return userService.unSubscribe(userId, password);
    }

    @GetMapping("/login")
    public BaseResponse login(
            @ApiParam(name = "userNo", value = "账号", required = true) @RequestParam(value = "userNo") String userNo,
            @ApiParam(name = "password", value = "密码", required = true) @RequestParam(value = "password") String password) {
        return userService.login(userNo, password);
    }

}
