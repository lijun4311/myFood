package com.mhs66.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.pojo.UserAddress;
import com.mhs66.pojo.bo.AddressBO;
import com.mhs66.service.IUserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
import java.util.List;

@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("address")
@RestController
@AllArgsConstructor
public class AddressController {

    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */


    private final IUserAddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    @BusinessObjectNotEmpty
    public WebResult list(@RequestParam String userId) {
        List<UserAddress> list = addressService.queryAll(userId);
        return WebResult.okData(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public WebResult add(@RequestBody @Validated AddressBO addressBO, BindingResult result) {
        addressService.addNewUserAddress(addressBO);
        return WebResult.ok();
    }


    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessObjectNotEmpty
    public WebResult update(@RequestBody @Validated AddressBO addressBO, BindingResult result) {

        addressService.updateUserAddress(addressBO);

        return WebResult.ok();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessObjectNotEmpty
    public WebResult delete(
            @RequestParam String userId,
            @RequestParam String addressId) {

        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setId(addressId);
        addressService.remove(Wrappers.lambdaQuery(address));
        return WebResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    @BusinessObjectNotEmpty
    public WebResult setDefalut(
            @RequestParam String userId,
            @RequestParam String addressId) {
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return WebResult.ok();
    }
}
