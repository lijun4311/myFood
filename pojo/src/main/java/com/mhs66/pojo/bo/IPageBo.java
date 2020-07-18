package com.mhs66.pojo.bo;


import com.mhs66.consts.PageConsts;
import lombok.Data;

/**
 * @author lijun
 * @date 2020-06-30 15:57
 * description 自定义分页接收对象
 * @since version-1.0
 */
@Data
public class IPageBo {
    int current = PageConsts.getPageCurrent();
    int size = PageConsts.getPageSize();
    int isAsc = PageConsts.getAscend();
    String orderBy;
    String fuzzySearchKey;
    String fuzzySearchValue;
    String accurateSearchKey;
    String accurateSearchValue;
    Integer searchId;

}
