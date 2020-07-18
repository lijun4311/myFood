package com.mhs66.pojo.vo;


import com.mhs66.pojo.Items;
import com.mhs66.pojo.ItemsImg;
import com.mhs66.pojo.ItemsParam;
import com.mhs66.pojo.ItemsSpec;
import lombok.*;

import java.util.List;


/**
 *description:
 * 商品详情VO
 *@author 76442
 *@date 2020-07-19 2:23
 */
@Data
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

}
