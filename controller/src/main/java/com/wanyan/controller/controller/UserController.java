package com.wanyan.controller.controller;

import com.wanyan.controller.BaseResponse;
import com.wanyan.controller.model.AccountBaseModel;
import com.wanyan.controller.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wanyanhw
 * @date 2021/2/11 11:11
 */
@Api(value = "UserController", tags = {"账户中心"})
@RestController
@RequestMapping("/account")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @return String
     */
    @PostMapping("/subscribe")
    public BaseResponse subscribe(@RequestBody AccountBaseModel account) throws Exception {
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

    /**
     * 验证账号合法性
     * @return String
     */
    @GetMapping("/account/verify")
    public BaseResponse verify(@ApiParam(name = "userNo", value = "账号") @RequestParam(value = "userNo") String userNo) {
        return userService.verify(userNo);
    }

}
