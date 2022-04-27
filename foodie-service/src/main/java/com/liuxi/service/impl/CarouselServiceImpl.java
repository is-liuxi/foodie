package com.liuxi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuxi.mapper.CarouselMapper;
import com.liuxi.pojo.Carousel;
import com.liuxi.service.CarouselService;
import com.liuxi.util.common.ConstantUtils;
import com.liuxi.util.common.JsonUtils;
import com.liuxi.util.common.RedisUtils;
import com.liuxi.util.enums.YesOrNoEnum;
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
        // 查看是否存在缓存 key，存在，直接返回缓存中得数据
        Boolean exist = RedisUtils.hasKey(ConstantUtils.CAROUSEL_CART_REDIS_KEY);
        if (exist) {
            String cacheValue = RedisUtils.get(ConstantUtils.CAROUSEL_CART_REDIS_KEY);
            return JsonUtils.jsonToList(cacheValue, Carousel.class);
        }

        List<Carousel> carouselList = carouselMapper.queryCarousel(YesOrNoEnum.YES.type);
        // 设置缓存
        RedisUtils.set(ConstantUtils.CAROUSEL_CART_REDIS_KEY, JsonUtils.writeValueAsString(carouselList), 60 * 60);
        return carouselList;
    }
}
