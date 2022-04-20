package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 商品规格
 * </P>
 * @author LiuXi
 * @date 2022/4/19 0:11
 */
public interface ItemSpecMapper extends BaseMapper<ItemsSpec> {

    /**
     * 减库存
     * @param byCount
     * @param specId
     */
    @Update("UPDATE items_spec SET stock = stock - #{buyCount} WHERE id = #{specId}")
    void decrItemStock(@Param("buyCount") int byCount, @Param("specId") String specId);
}
