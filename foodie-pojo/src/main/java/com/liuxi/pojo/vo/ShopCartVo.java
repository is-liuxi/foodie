package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 * 购物车
 * </P>
 * @author liu xi
 * @date 2022/4/20 3:04
 */
@Data
public class ShopCartVo {
    String itemId;
    String itemName;
    String itemImgUrl;
    String specId;
    String specName;
    Integer specStock;
    Integer buyCounts;
    Integer priceNormal;
    Integer priceDiscount;
}
