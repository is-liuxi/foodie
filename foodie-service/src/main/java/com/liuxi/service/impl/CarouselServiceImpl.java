package com.liuxi.service.impl;

import com.liuxi.mapper.CarouselMapper;
import com.liuxi.pojo.Carousel;
import com.liuxi.service.CarouselService;
import com.liuxi.util.enums.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 16:35
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryCarousel() {
        return carouselMapper.queryCarousel(YesOrNo.YES.type);
    }
}
