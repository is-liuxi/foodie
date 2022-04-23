package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 * 购物车生成订单 VO
 * </P>
 * @author liu xi
 * @date 2022/4/20 23:53
 */
@Data
public class ShopCartCreateOrderVo {
    /**
     * 收货地址 id
     */
    private String addressId;
    /**
     * 规格 id
     */
    private String itemSpecIds;
    /**
     * 订单备注信息
     */
    private String leftMsg;
    /**
     * 支付方式
     */
    private Integer payMethod;
    /**
     * 用户 id
     */
    private String userId;
}
