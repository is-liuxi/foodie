package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.Carousel;

import java.util.List;

/**
 * <p>
 *  轮播图操作
 * </P>
 * @author LiuXi
 * @date 2022/4/18 16:30
 */
public interface CarouselMapper extends BaseMapper<Carousel> {

    /**
     * 查询轮播图
     * @return
     */
    List<Carousel> queryCarousel(int isShow);
}
