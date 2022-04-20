package com.liuxi.service;

import com.liuxi.pojo.vo.ShopCartVo;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/20 3:49
 */
public interface ShopCartService {
    /**
     * 查询购物车中的商品
     * @param itemSpecId
     * @return
     */
    List<ShopCartVo> selectCartsBySpecId(String itemSpecId);
}
