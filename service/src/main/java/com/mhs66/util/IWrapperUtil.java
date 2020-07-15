package com.mhs66.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mhs66.consts.SystemConsts;
import com.mhs66.pojo.bo.IPageBo;
import com.mhs66.utis.IBeanUtil;
import com.mhs66.utis.IStringUtil;


/**
 * @author lijun
 * @date 2020-06-30 15:54
 * @description 自定义mp查询对象
 * @error
 * @since version-1.0
 */
public class IWrapperUtil {
    /**
     * 通过分页对象获得筛选条件
     *
     * @param iPageBo 分页对象
     * @param tClass 传入对象类型
     * @return QueryWrapper 查询条件对象
     */
    public static <T> QueryWrapper<T> getQueryWrapperPage(IPageBo iPageBo, Class<T> tClass, QueryWrapper<T> queryWrapper) {
        assert iPageBo != null;
        queryWrapper = IBeanUtil.isRequired(queryWrapper) ? new QueryWrapper<>() : queryWrapper;
        queryWrapper.like(
                IBeanUtil.isFieldExist(iPageBo.getFuzzySearchKey(), tClass)
                        && !IBeanUtil.isRequired(iPageBo.getFuzzySearchValue()),
                IStringUtil.camelToUnderline(iPageBo.getFuzzySearchKey()),
                iPageBo.getFuzzySearchValue());
        queryWrapper.eq(IBeanUtil.isFieldExist(iPageBo.getAccurateSearchKey(), tClass)
                        && !IBeanUtil.isRequired(iPageBo.getAccurateSearchValue()),
                IStringUtil.camelToUnderline(iPageBo.getAccurateSearchKey()),
                iPageBo.getAccurateSearchValue());
        queryWrapper.orderBy(IBeanUtil.isFieldExist(iPageBo.getOrderBy(), tClass),
                iPageBo.getIsAsc() == SystemConsts.ASCEND,
                IStringUtil.camelToUnderline(iPageBo.getOrderBy()));

        return queryWrapper;
    }
}
