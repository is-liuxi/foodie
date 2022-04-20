package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.Items;
import com.liuxi.pojo.vo.ShopCartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/20 3:48
 */
public interface ShopCartMapper extends BaseMapper<Items> {
    /**
     * 查询购物车中的商品
     * @param itemSpecIds
     * @return
     */
    List<ShopCartVo> selectCartsBySpecId(@Param("itemSpecIds") List<String> itemSpecIds);
}
