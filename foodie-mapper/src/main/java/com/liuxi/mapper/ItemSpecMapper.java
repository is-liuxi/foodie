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
     * 减库存。使用乐观锁
     * @param byCount
     * @param specId
     * @return
     */
    @Update("UPDATE items_spec SET stock = stock - #{buyCount} WHERE id = #{specId} AND stock >= #{buyCount}")
    int decrItemStock(@Param("buyCount") int byCount, @Param("specId") String specId);
}
