package com.liuxi.service.impl;

import com.liuxi.mapper.ShopCartMapper;
import com.liuxi.pojo.vo.ShopCartVo;
import com.liuxi.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/20 3:49
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Override
    public List<ShopCartVo> selectCartsBySpecId(String itemSpecIds) {
        String[] strArr = itemSpecIds.split(",");
        return shopCartMapper.selectCartsBySpecId(Arrays.asList(strArr));
    }
}
