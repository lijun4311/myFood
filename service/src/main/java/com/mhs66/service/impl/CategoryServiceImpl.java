package com.mhs66.service.impl;

import com.mhs66.mapper.CategoryMapper;
import com.mhs66.pojo.Category;
import com.mhs66.service.ICategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品分类  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
