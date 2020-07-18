package com.mhs66.service.impl;

import com.mhs66.mapper.UserAddressMapper;
import com.mhs66.pojo.UserAddress;
import com.mhs66.service.IUserAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户地址表  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class UserAddressServiceImpl extends BaseServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {
    @Override
    public UserAddress queryUserAddres(String userId, String addressId) {
        return getOne(lambdaQuery().eq(UserAddress::getUserId, userId).eq(UserAddress::getId, addressId));
    }

}
