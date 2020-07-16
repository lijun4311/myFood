package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.mapper.UsersMapper;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;
import com.mhs66.service.IUsersService;
import com.mhs66.utis.IDateUtil;
import com.mhs66.utis.Md5Util;
import lombok.AllArgsConstructor;

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


   // private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @ArgsNotEmpty
    public boolean queryUsernameIsExist(String username) {
        return this.count(Wrappers.lambdaQuery(Users.class).eq(Users::getNickname, username)) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        String userId = UUID.randomUUID().toString();//sid.nextShort();
        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        user.setPassword(Md5Util.md5EncodeUtf8(userBO.getPassword()));
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace("");
        // 默认生日
        user.setBirthday(IDateUtil.strToDate("1900-01-01"));
        // 默认性别为 保密
        user.setSex(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        return this.getOne(Wrappers.lambdaQuery(Users.class).eq(Users::getNickname, username).eq(Users::getPassword, password));
    }
}
