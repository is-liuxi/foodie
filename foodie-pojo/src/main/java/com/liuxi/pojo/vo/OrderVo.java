package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 21:15
 */
@Data
public class OrderVo {

    /**
     * 手机扫码显示地址
     */
    String qrCodeUrl;
    /**
     * 订单 id
     */
    String orderId;
    /**
     * 支付价格
     */
    String amount;
}
