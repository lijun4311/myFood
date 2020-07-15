package com.mhs66.service;

import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface IUsersService extends BaseService<Users> {
    @ArgsNotEmpty
    boolean queryUsernameIsExist(String username);

    @Transactional(propagation = Propagation.REQUIRED)
    Users createUser(UserBO userBO);

    @Transactional(propagation = Propagation.SUPPORTS)
    Users queryUserForLogin(String username, String password);
}
