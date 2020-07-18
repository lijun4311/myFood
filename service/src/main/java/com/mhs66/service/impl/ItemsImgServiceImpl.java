package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.enums.BusinessStatus;
import com.mhs66.mapper.ItemsImgMapper;
import com.mhs66.pojo.ItemsImg;
import com.mhs66.service.IItemsImgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品图片  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class ItemsImgServiceImpl extends BaseServiceImpl<ItemsImgMapper, ItemsImg> implements IItemsImgService {

    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(BusinessStatus.YES.type);
        ItemsImg result = getOne(Wrappers.lambdaQuery(itemsImg));
        return result != null ? result.getUrl() : "";
    }
}
