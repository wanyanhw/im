package com.wanyan.controller;

import com.wanyan.core.model.AccountBaseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanyanhw
 * @date 2021/2/11 11:11
 */
@Api(tags = {"账户中心"})
@RestController
@RequestMapping("/account")
public class RegisterController {

    /**
     * 用户注册
     * @return String
     */
    @PostMapping("/subscribe")
    public String register(@RequestBody AccountBaseModel account) {
        // TODO: 2021/2/11 用户申请注册
        return "";
    }

    /**
     * 注销账户
     * @return String
     */
    @GetMapping("/unsubscribe")
    public String unsubscribe(@ApiParam(name = "userId", required = true) @RequestParam("userId") String userId) {
        // TODO: 2021/2/11  用户发起注销操作
        return "";
    }

}
