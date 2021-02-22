package com.wanyan.controller;

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
    public String register(@RequestBody AccountBaseModel account) {
        String result = userService.subscribe(account);
        return "申请成功，请牢记账号：" + result;
    }

    /**
     * 注销账户
     * @return String
     */
    @GetMapping("/unsubscribe")
    public String unsubscribe(
            @ApiParam(name = "userId", value = "用户账号", required = true) @RequestParam(value = "userId") String userId,
            @ApiParam(name = "password", value = "密码", required = true) @RequestParam(value = "password") String password) {
        return userService.unSubscribe(userId, password);
    }

    @GetMapping("/login")
    public AccountBaseModel login(
            @ApiParam(name = "userId", value = "账号", required = true) @RequestParam(value = "userId") String userId,
            @ApiParam(name = "password", value = "密码", required = true) @RequestParam(value = "password") String password) {
        return userService.login(userId, password);
    }

}
