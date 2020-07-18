package com.mhs66.pojo.bo;


import com.mhs66.consts.PageConsts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijun
 * @date 2020-06-30 15:57
 * description 自定义分页接收对象
 * @since version-1.0
 */
@Data
@ApiModel(value = "自定义分页接收对象", description = "从客户端，自定义分页接收对象")
public class PageBo {
    @ApiModelProperty(value = "当前页", example = "1")
    int page = PageConsts.getPage();
    @ApiModelProperty(value = "页容量", example = "10")
    int pageSize = PageConsts.getPageSize();
    @NotBlank(message = "关键字不能为空",groups = {serarch.class})
    @ApiModelProperty(value = "关键字", example = "key")
    String keywords;
    @ApiModelProperty(value = "排序", example = "c")
    String sort;

    public interface serarch{

    }
}
