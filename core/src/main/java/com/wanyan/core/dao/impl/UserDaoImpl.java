package com.wanyan.core.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanyan.core.entity.UserEntity;
import com.wanyan.core.mapper.UserMapper;
import com.wanyan.core.dao.IUserDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
        queryWrapper.eq(UserEntity::getDeleted, 0).eq(UserEntity::getUserNo, userId).eq(UserEntity::getPassword, password);
        return getOne(queryWrapper);
    }

    @Override
    public List<UserEntity> listAllUser() {
        return list(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getDeleted, 0));
    }

    @Override
    public UserEntity getOneByUserNo(String userNo) {
        return getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getDeleted, 0).eq(UserEntity::getUserNo, userNo));
    }
}
