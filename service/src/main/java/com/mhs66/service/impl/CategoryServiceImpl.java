package com.mhs66.service.impl;

import com.mhs66.consts.CategoryConsts;
import com.mhs66.mapper.CategoryMapper;
import com.mhs66.mapper.CategoryMapperCustom;
import com.mhs66.pojo.Category;
import com.mhs66.pojo.vo.CategoryVO;
import com.mhs66.pojo.vo.NewItemsVO;
import com.mhs66.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements ICategoryService {

    private final CategoryMapperCustom categoryMapperCustom;


    @Override
    public List<Category> queryAllRootLevelCat() {
        return list(iLambdaQuery().eq(Category::getType, CategoryConsts.TOP_TYPE));

    }


    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {

        return null;
    }

    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("rootCatId", rootCatId);

        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
