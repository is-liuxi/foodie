package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.OrderStatus;
import com.liuxi.pojo.vo.OrderStatusVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 1:44
 */
public interface OrderStatusMapper extends BaseMapper<OrderStatus> {

    /**
     * 查询各种订单状态个数
     * @param userId
     * @return
     */
    OrderStatusVo queryOrderStatusCounts(String userId);

    /**
     * 查询订单趋势，未支付与交易关闭的不显示
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    List<OrderStatus> queryOrderTrend(@Param("userId") String userId, @Param("page") int page, @Param("pageSize") int pageSize);

    /**
     * 查询订单趋势个数
     * @param userId
     * @return
     */
    int queryOrderTrendCount(String userId);
}
