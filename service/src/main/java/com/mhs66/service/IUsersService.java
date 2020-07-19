package com.mhs66.service;

import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;

/**
 * <p>
 * 用户表  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface IUsersService extends BaseService<Users> {
    /**
     * 查询用户名是否重复
     * @param username 用户名
     * @return boolean
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO 用户数据对象
     * @return 用户对象
     */
    Users createUser(UserBO userBO);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    Users queryUserForLogin(String username, String password);

    void setNullProperty(Users userResult);

    /**
     * 修改用户信息
     * @param userId
     * @param userBO
     */
    public Users updateUserInfo(String userId, UserBO userBO);

    /**
     * 用户头像更新
     * @param userId
     * @param faceUrl
     * @return
     */
    public Users updateUserFace(String userId, String faceUrl);
}
