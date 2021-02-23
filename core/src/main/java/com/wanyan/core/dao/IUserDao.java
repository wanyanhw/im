package com.wanyan.core.dao;

import com.wanyan.core.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户注册表 服务类
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-22
 */
public interface IUserDao extends IService<UserEntity> {

    /**
     * 根据账号密码获取客户
     * @param userId 账号
     * @param password 密码
     * @return UserEntity
     */
    UserEntity getOne(String userId, String password);

    /**
     * 遍历所有账号
     * @return List
     */
    List<UserEntity> listAllUser();

    /**
     * 根据用户账号获取用户信息
     * @param userNo 账号
     * @return UserEntity
     */
    UserEntity getOneByUserNo(String userNo);
}
