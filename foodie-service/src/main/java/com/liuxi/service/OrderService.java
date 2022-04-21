package com.liuxi.service;

import com.liuxi.pojo.OrderStatus;
import com.liuxi.pojo.vo.OrderVo;
import com.liuxi.pojo.vo.ShopCartCreateOrderVo;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/21 0:05
 */
public interface OrderService {

    /**
     * 创建订单
     * @param shopCartCreateOrderVo
     * @return
     */
    String createOrder(ShopCartCreateOrderVo shopCartCreateOrderVo);

    /**
     * 查询订单价格，生成二维码，跳转地址
     * @param userId
     * @param orderId
     * @return
     */
    OrderVo queryOrderByIdAndUserId(String userId, String orderId);

    /**
     * 支付成功，修改订单状态
     * @param orderId
     */
    void updateOrderStatus(String orderId);

    /**
     * 查询订单状态，是否已支付
     * @param orderId
     * @return
     */
    OrderStatus getPaidOrderInfo(String orderId);
}