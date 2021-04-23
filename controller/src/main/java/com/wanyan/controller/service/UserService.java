package com.wanyan.controller.service;

import com.wanyan.controller.BaseResponse;
import com.wanyan.controller.model.AccountBaseModel;

/**
 * @author wanyanhw
 * @date 2021/2/22 10:30
 */
public interface UserService {
    /**
     * 用户注册申请
     * @param baseModel 用户注册信息
     * @return BaseResponse
     */
    BaseResponse subscribe(AccountBaseModel baseModel) throws Exception;

    /**
     * 用户注销申请
     * @param userId 用户账号id
     * @param password 用户密码
     * @return BaseResponse
     */
    BaseResponse unSubscribe(String userId, String password);

    /**
     * 用户登录
     * @param userNo 用户账号
     * @param password 用户密码
     * @return BaseResponse
     */
    BaseResponse login(String userNo, String password);

    /**
     * 验证账号合法性
     * @param userNo 用户账号
     * @return BaseResponse
     */
    BaseResponse verify(String userNo);
}
