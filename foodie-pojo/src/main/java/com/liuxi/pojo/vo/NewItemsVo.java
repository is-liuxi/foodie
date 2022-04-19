package com.liuxi.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *  最新商品 VO
 * </P>
 * @author liu xi
 * @date 2022/4/18 20:48
 */
@Data
public class NewItemsVo {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVo> simpleItemList;
}
