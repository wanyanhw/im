package com.wanyan.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wanyan.core.dao.IUserDao;
import com.wanyan.core.dao.IUserDetailDao;
import com.wanyan.core.entity.UserDetailEntity;
import com.wanyan.core.entity.UserEntity;
import com.wanyan.core.model.AccountBaseModel;
import com.wanyan.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanyanhw
 * @date 2021/2/22 10:32
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserDetailDao userDetailDao;

    @Override
    public String subscribe(AccountBaseModel baseModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNo("555555");
        userEntity.setPassword("666");
        boolean save;
        save = userDao.save(userEntity);
        if (!save) {
            return "0";
        }
        baseModel.setUserId(userEntity.getId().toString());
        save = userDetailDao.save(transformModel(baseModel));
        return save ? userEntity.getUserNo() : "0";
    }

    @Override
    public String unSubscribe(String userId, String password) {
        boolean update = userDao.update(new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getUserNo, userId)
                .eq(UserEntity::getPassword, password)
                .set(UserEntity::getDeleted, 1));
        return update ? "ok" : "0";
    }

    @Override
    public AccountBaseModel login(String userId, String password) {
        UserEntity userEntity = userDao.getOne(userId, password);
        if (userEntity == null) {
            return null;
        }
        AccountBaseModel accountBaseModel = transformEntity(userDetailDao.getOne(
                new LambdaQueryWrapper<UserDetailEntity>().eq(UserDetailEntity::getUserId, userEntity.getId())));
        accountBaseModel.setPassword(userEntity.getPassword());
        return accountBaseModel;
    }

    private AccountBaseModel transformEntity(UserDetailEntity userDetailEntity) {
        AccountBaseModel accountBaseModel = new AccountBaseModel();
        accountBaseModel.setUserId(userDetailEntity.getUserId().toString());
        accountBaseModel.setName(userDetailEntity.getName());
        accountBaseModel.setAddress(userDetailEntity.getAddress());
        accountBaseModel.setPhone(userDetailEntity.getPhone());
        accountBaseModel.setSex(userDetailEntity.getSex());
        return accountBaseModel;
    }

    private UserDetailEntity transformModel(AccountBaseModel baseModel) {
        UserDetailEntity userDetailEntity = new UserDetailEntity();
        userDetailEntity.setName(baseModel.getName());
        userDetailEntity.setPhone(baseModel.getPhone());
        userDetailEntity.setAddress(baseModel.getAddress());
        userDetailEntity.setSex(baseModel.getSex());
        userDetailEntity.setUserId(Integer.parseInt(baseModel.getUserId()));
        return userDetailEntity;
    }
}
