package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.consts.UserConsts;
import com.mhs66.mapper.UsersMapper;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;
import com.mhs66.service.IUsersService;
import com.mhs66.utils.IBeanUtil;
import com.mhs66.utils.Md5Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 用户表  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
@AllArgsConstructor
public class UsersServiceImpl extends BaseServiceImpl<UsersMapper, Users> implements IUsersService {


    @Override
    @ArgsNotEmpty
    public boolean queryUsernameIsExist(String username) {
        return this.count(Wrappers.lambdaQuery(Users.class).eq(Users::getUsername, username)) > 0;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        assert !IBeanUtil.isValueNotBank(userBO, UserBO::getUsername, Users::getPassword);
        String userId = UUID.randomUUID().toString();
        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        user.setPassword(Md5Util.md5EncodeUtf8(userBO.getPassword()));
        // 默认用户昵称同用户名
        user.setNickname(userBO.getNickname());
        // 默认头像
        user.setFace(UserConsts.getDefaultFace());
        // 默认生日
        user.setBirthday(UserConsts.getDefaultBirhday());
        // 默认性别为 保密
        user.setSex(UserConsts.getDefaultSex());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return user;
    }


    @Override
    public Users queryUserForLogin(String username, String password) {
        return this.getOne(Wrappers.lambdaQuery(Users.class).eq(Users::getUsername, username).eq(Users::getPassword, password));
    }

    /**
     * 用户信息字段屏蔽
     *
     * @param userResult 用户对象
     */
    @Override
    public void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreateTime(null);
        userResult.setUpdateTime(null);
        userResult.setBirthday(null);
    }

    @Override
    public Users updateUserInfo(String userId, UserBO centerUserBO) {

        Users updateUser = new Users();
        IBeanUtil.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdateTime(LocalDateTime.now());
        updateUser.setPassword(null);
        updateById(updateUser);

        return getById(userId);
    }


    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdateTime(LocalDateTime.now());

        updateById(updateUser);

        return getById(userId);
    }
}
