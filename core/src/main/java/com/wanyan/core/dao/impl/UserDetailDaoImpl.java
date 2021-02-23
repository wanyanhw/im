package com.wanyan.core.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanyan.core.entity.UserDetailEntity;
import com.wanyan.core.mapper.UserDetailMapper;
import com.wanyan.core.dao.IUserDetailDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户注册详情 服务实现类
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-22
 */
@Service
public class UserDetailDaoImpl extends ServiceImpl<UserDetailMapper, UserDetailEntity> implements IUserDetailDao {

    @Override
    public UserDetailEntity getUserDetail(Integer userId) {
        return getOne(new LambdaQueryWrapper<UserDetailEntity>().eq(UserDetailEntity::getUserId, userId));
    }
}
