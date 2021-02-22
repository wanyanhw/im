package com.wanyan.core.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanyan.core.entity.UserEntity;
import com.wanyan.core.mapper.UserMapper;
import com.wanyan.core.dao.IUserDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户注册表 服务实现类
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-22
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserDao {

    @Override
    public UserEntity getOne(String userId, String password) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        return getOne(queryWrapper.eq(UserEntity::getDeleted, 0));
    }
}
