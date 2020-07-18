package com.mhs66.service;

import com.mhs66.pojo.ItemsImg;

/**
 * <p>
 * 商品图片  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface IItemsImgService extends BaseService<ItemsImg> {

    String queryItemMainImgById(String itemId);
}
