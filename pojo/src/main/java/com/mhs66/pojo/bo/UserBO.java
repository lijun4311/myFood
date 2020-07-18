package com.mhs66.pojo.bo;

import com.mhs66.pojo.Users;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:
 * 用户传入对象
 *
 * @author 76442
 * @date 2020-07-18 16:07
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户对象", description = "从客户端，由用户传入的数据封装在此entity中")
@Data
public class UserBO extends Users {

    @ApiModelProperty(value = "确认密码", example = "123456")
    private String confirmPassword;


    public interface Login {
    }
    public interface Regist{

    }

}
