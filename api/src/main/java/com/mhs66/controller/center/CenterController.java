package com.mhs66.controller.center;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.pojo.Users;
import com.mhs66.service.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("center")
@AllArgsConstructor
public class CenterController {


    private final IUsersService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("userInfo")
    @BusinessObjectNotEmpty
    public WebResult userInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {

        Users user = centerUserService.getById(userId);
        user.setPassword("");
        return WebResult.okData(user);
    }


}
