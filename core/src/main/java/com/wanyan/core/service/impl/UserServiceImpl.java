package com.wanyan.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wanyan.core.config.BaseResponse;
import com.wanyan.core.dao.IUserDao;
import com.wanyan.core.dao.IUserDetailDao;
import com.wanyan.core.entity.UserDetailEntity;
import com.wanyan.core.entity.UserEntity;
import com.wanyan.core.model.AccountBaseModel;
import com.wanyan.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wanyanhw
 * @date 2021/2/22 10:32
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserDetailDao userDetailDao;

    @Override
    public BaseResponse subscribe(AccountBaseModel baseModel) {
        BaseResponse<AccountBaseModel> baseResponse = new BaseResponse<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNo(baseModel.getUserNo());
        userEntity.setPassword(baseModel.getPassword());
        userDao.save(userEntity);
        UserDetailEntity userDetailEntity = transformModel(baseModel);
        userDetailEntity.setUserId(userEntity.getId());
        boolean save = userDetailDao.save(userDetailEntity);
        if (save) {
            return baseResponse.setData(baseModel);
        }
        return baseResponse.setCode(100001).setMsg("注册失败");
    }

    @Override
    public BaseResponse unSubscribe(String userId, String password) {
        boolean update = userDao.update(new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getUserNo, userId)
                .eq(UserEntity::getPassword, password)
                .set(UserEntity::getDeleted, 1));
        BaseResponse baseResponse = new BaseResponse();
        if (update) {
            return baseResponse;
        }
        return baseResponse.setCode(100001).setMsg("注销失败，请联系服务人员");
    }

    @Override
    public BaseResponse login(String userNo, String password) {
        BaseResponse<AccountBaseModel> baseResponse = new BaseResponse<>();
        UserEntity userEntity = userDao.getOne(userNo, password);
        if (userEntity == null) {
            return baseResponse.setCode(100002).setMsg("登录失败，账号或密码错误");
        }
        AccountBaseModel accountBaseModel = transformEntity(userDetailDao.getUserDetail(userEntity.getId()));
        accountBaseModel.setPassword(userEntity.getPassword());
        return baseResponse.setData(accountBaseModel);
    }

    private AccountBaseModel transformEntity(UserDetailEntity userDetailEntity) {
        AccountBaseModel accountBaseModel = new AccountBaseModel();
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
        return userDetailEntity;
    }
}
