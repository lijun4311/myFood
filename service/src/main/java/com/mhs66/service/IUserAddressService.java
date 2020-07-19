package com.mhs66.service;

import com.mhs66.pojo.UserAddress;
import com.mhs66.pojo.bo.AddressBO;

import java.util.List;


/**
 * <p>
 * 用户地址表  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface IUserAddressService extends BaseService<UserAddress> {

    UserAddress queryUserAddres(String userId, String addressId);

    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);

    void updateUserAddress(AddressBO addressBO);

    void updateUserAddressToBeDefault(String userId, String addressId);
}
