package com.liuxi.service;

import com.liuxi.pojo.Carousel;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/18 16:35
 */
public interface CarouselService {

    /**
     * 查询轮播图
     * @return
     */
    List<Carousel> queryCarousel();
}
