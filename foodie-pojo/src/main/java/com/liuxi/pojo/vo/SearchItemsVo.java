package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/19 21:21
 */
@Data
public class SearchItemsVo {
    /**
     * 商品id
     */
    private String itemId;
    /**
     * 商品名
     */
    private String itemName;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 销量
     */
    private Integer sellCounts;
    /**
     * 商品图片
     */
    private String imgUrl;
}
