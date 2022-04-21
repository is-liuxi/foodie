package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.Orders;
import com.liuxi.pojo.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/21 0:06
 */
public interface OrderMapper extends BaseMapper<Orders> {

    /**
     * 查询订单
     * @param userId
     * @param orderId
     * @return
     */
    OrderVo queryOrderByIdAndUserId(@Param("userId") String userId, @Param("orderId") String orderId);
}
