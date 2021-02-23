package com.wanyan.core.dao;

import com.wanyan.core.entity.UserDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户注册详情 服务类
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-22
 */
public interface IUserDetailDao extends IService<UserDetailEntity> {

    /**
     * 获取用户详情
     * @param userId 用户ID
     * @return UserDetailEntity
     */
    UserDetailEntity getUserDetail(Integer userId);
}
