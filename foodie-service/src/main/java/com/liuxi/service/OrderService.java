package com.liuxi.service;

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
}
