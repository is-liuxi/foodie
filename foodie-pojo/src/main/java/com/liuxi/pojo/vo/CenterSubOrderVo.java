package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/22 21:19
 */
@Data
public class CenterSubOrderVo {
    private Integer buyCounts;
    private Integer price;
    private String specId;
    private String itemId;
    private String itemImg;
    private String itemName;
    private String itemSpecName;
}
