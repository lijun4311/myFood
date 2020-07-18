package com.mhs66.pojo.vo;


import lombok.Data;

import java.util.List;

/**
 * @author lijun
 * @date 2020-06-30 19:09
 * @description 自定义分页封装对象
 * @error
 * @since version-1.0
 */
@Data
public class PageVo<T> {
    /**
     * 当前页数
     */
    private int page;
    /**
     * 总页数
     */
    private int total;
    /**
     * 总记录数
     */
    private long records;
    /**
     * 每行显示的内容
     */
    private List<T> rows;


}
