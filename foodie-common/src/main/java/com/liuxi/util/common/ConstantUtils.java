package com.liuxi.util.common;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 17:35
 */
public interface ConstantUtils {

    /**
     * 购物车 Cookie key
     */
    String SHOP_CART_COOKIE_KEY = "shopcart";

    /**
     * 购物车缓存 key
     */
    String SHOP_CART_REDIS_KEY = "shopCart:";

    /**
     * 轮播图缓存 key
     */
    String CAROUSEL_REDIS_KEY = "carousel";

    /**
     * 商品一级分类缓存 key
     */
    String CATEGORY_REDIS_KEY = "category";

    /**
     * 商品二级分类缓存 key
     */
    String SUB_CATEGORY_REDIS_KEY = "sub_category:";

}
