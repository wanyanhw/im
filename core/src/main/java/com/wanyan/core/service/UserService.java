package com.wanyan.core.service;

import com.wanyan.core.model.AccountBaseModel;

/**
 * @author wanyanhw
 * @date 2021/2/22 10:30
 */
public interface UserService {
    /**
     * 用户注册申请
     * @param baseModel 用户注册信息
     * @return String
     */
    String subscribe(AccountBaseModel baseModel);

    /**
     * 用户注销申请
     * @param userId 用户账号id
     * @param password 用户密码
     * @return String
     */
    String unSubscribe(String userId, String password);

    /**
     * 用户登录
     * @param userId 用户账号
     * @param password 用户密码
     * @return AccountBaseModel 账号详情
     */
    AccountBaseModel login(String userId, String password);
}
