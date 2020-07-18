package com.mhs66.service;

import com.mhs66.pojo.Category;
import com.mhs66.pojo.vo.CategoryVO;
import com.mhs66.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * <p>
 * 商品分类  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface ICategoryService extends BaseService<Category> {
    /**
     * 获取商品分类
     * @return 分类
     */
    List<Category> queryAllRootLevelCat();
    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId top id
     * @return vo
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);
    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
