package com.mhs66.pojo.bo;

import com.mhs66.consts.SystemConsts;
import lombok.Data;

/**
 * @author lijun
 * @date 2020-06-30 15:57
 * description 自定义分页接收对象
 * @since version-1.0
 */
@Data
public class IPageBo {
    int current = SystemConsts.getPageCurrent();
    int size = SystemConsts.getPageSize();
    int isAsc = SystemConsts.getAscend();
    String orderBy;
    String fuzzySearchKey;
    String fuzzySearchValue;
    String accurateSearchKey;
    String accurateSearchValue;
    Integer searchId;

}
