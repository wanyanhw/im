package com.wanyan.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wanyan.core.config.BaseResponse;
import com.wanyan.core.dao.IUserDao;
import com.wanyan.core.dao.IUserDetailDao;
import com.wanyan.core.entity.UserDetailEntity;
import com.wanyan.core.entity.UserEntity;
import com.wanyan.core.model.AccountBaseModel;
import com.wanyan.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wanyanhw
 * @date 2021/2/22 10:32
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserDetailDao userDetailDao;

    @Override
    public BaseResponse subscribe(AccountBaseModel baseModel) {
        BaseResponse<AccountBaseModel> baseResponse = new BaseResponse<>();
        String userNo = null;
        // 用户自定义账号
        String customUserNo = baseModel.getUserNo();
        if (StringUtils.hasLength(customUserNo)) {
            BaseResponse verifyResult = this.verify(customUserNo);
            if (verifyResult.getCode() != 0) {
                return verifyResult;
            }
            userNo = customUserNo;
        }
        if (!StringUtils.hasLength(userNo)) {
            userNo = generateUserNo();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNo(userNo);
        userEntity.setPassword(baseModel.getPassword());
        userDao.save(userEntity);
        UserDetailEntity userDetailEntity = transformModel(baseModel);
        userDetailEntity.setUserId(userEntity.getId());
        boolean save = userDetailDao.save(userDetailEntity);
        if (save) {
            baseModel.setUserNo(userNo);
            return baseResponse.setData(baseModel);
        }
        return baseResponse.setCode(100001).setMsg("注册失败");
    }

    /**
     * 生成用户账号
     * @return userNo
     */
    private String generateUserNo() {
        List<UserEntity> allUserList = userDao.listAllUser();
        if (allUserList.isEmpty()) {
            return "11111";
        }
        String userNo = allUserList.stream().max((v1, v2) -> {
            try {
                Long v1UserNo = Long.parseLong(v1.getUserNo());
                Long v2UserNo = Long.parseLong(v2.getUserNo());
                if (v1UserNo > v2UserNo) {
                    return 1;
                }
                return -1;
            } catch (NumberFormatException ignore) {
                return 0;
            }
        }).get().getUserNo();

        try {
            int maxUserNo = Integer.parseInt(userNo);
            return String.valueOf(maxUserNo + 1000);
        } catch (NumberFormatException ignore) {
            log.info("账号[{}]数值转换异常", userNo);
            return String.valueOf(System.currentTimeMillis());
        }
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
        AccountBaseModel accountBaseModel = fillUserDetailInfo(userEntity.getId());
        return baseResponse.setData(accountBaseModel);
    }

    private AccountBaseModel fillUserDetailInfo(Integer userId) {
        UserEntity userEntity = userDao.getById(userId);
        AccountBaseModel accountBaseModel = transformEntity(userDetailDao.getUserDetail(userId));
        accountBaseModel.setUserNo(userEntity.getUserNo());
        accountBaseModel.setPassword(userEntity.getPassword());
        return accountBaseModel;
    }

    @Override
    public BaseResponse verify(String userNo) {
        BaseResponse<AccountBaseModel> baseResponse = new BaseResponse<>();
        UserEntity userEntity = userDao.getOneByUserNo(userNo);
        if (userEntity != null) {
            return baseResponse.setCode(100003).setMsg("账号已存在");
        }
        return baseResponse;
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
