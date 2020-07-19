package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.enums.BusinessStatus;
import com.mhs66.mapper.UserAddressMapper;
import com.mhs66.pojo.UserAddress;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.AddressBO;
import com.mhs66.service.IUserAddressService;
import com.mhs66.utils.IBeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Override
    public List<UserAddress> queryAll(String userId) {
        return list(iLambdaQuery().eq(UserAddress::getUserId, userId));
    }

    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        // 1. 判断当前用户是否存在地址，如果没有，则新增为‘默认地址’
        int isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (IBeanUtil.isRequired(addressList)) {
            isDefault = 1;
        }
        String addressId = UUID.randomUUID().toString();

        // 2. 保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreateTime(LocalDateTime.now());
        newAddress.setUpdateTime(LocalDateTime.now());

        save(newAddress);
    }

    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();

        UserAddress pendingAddress = new UserAddress();
        IBeanUtil.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressId);
        pendingAddress.setUpdateTime(LocalDateTime.now());
        updateById(pendingAddress);
    }

    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        // 1. 查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(BusinessStatus.YES.type);
        List<UserAddress> list = list(Wrappers.lambdaQuery(queryAddress));
        for (UserAddress ua : list) {
            lambdaUpdate(ua).set(UserAddress::getIsDefault, BusinessStatus.NO.type);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        lambdaUpdate(defaultAddress).set(UserAddress::getIsDefault, BusinessStatus.YES);
    }

    /**
     * 用户信息字段屏蔽
     *
     * @param userResult 用户对象
     */
    private void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreateTime(null);
        userResult.setUpdateTime(null);
        userResult.setBirthday(null);
    }
}
