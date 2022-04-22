package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.Orders;
import com.liuxi.pojo.vo.CenterOrderVo;
import com.liuxi.pojo.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询个人所有订单
     * @param userId
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    List<CenterOrderVo> queryOrderByUserIdAndOrderStatus(@Param("userId") String userId, @Param("status") Integer status,
                                                         @Param("page") int page, @Param("pageSize") int pageSize);

    /**
     * 个人所有订单数量，用来分页
     * @param userId
     * @param status
     * @return
     */
    int queryOrderByUserIdAndOrderStatusCount(@Param("userId") String userId, @Param("status") Integer status);
}
