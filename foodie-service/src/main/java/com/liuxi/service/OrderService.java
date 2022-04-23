package com.liuxi.service;

import com.liuxi.pojo.ItemsComments;
import com.liuxi.pojo.OrderItems;
import com.liuxi.pojo.OrderStatus;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.*;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/21 0:05
 */
public interface OrderService {

    /**
     * 创建订单
     * @param shopCartCreateOrderVo
     * @return
     */
    String createOrder(ShopCartCreateOrderVo shopCartCreateOrderVo);

    /**
     * 查询订单价格，生成二维码，跳转地址
     * @param userId
     * @param orderId
     * @return
     */
    OrderVo queryOrderByIdAndUserId(String userId, String orderId);

    /**
     * 支付成功，修改订单状态
     * @param orderId
     */
    void updateOrderStatus(String orderId);

    /**
     * 查询订单状态，是否已支付
     * @param orderId
     * @return
     */
    OrderStatus getPaidOrderInfo(String orderId);

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
    PageResult<OrderStatus> queryOrderTrend(String userId, int page, int pageSize);

    /**
     * 查询个人所有订单
     * @param userId
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<CenterOrderVo> queryOrderByUserIdAndOrderStatus(String userId, Integer status, int page, int pageSize);

    /**
     * 确认收货
     * @param userId
     * @param orderId
     */
    void confirmReceive(String userId, String orderId);

    /**
     * 查询子订单信息，用于评价商品
     * @param userId
     * @param orderId
     * @return
     */
    List<OrderItems> queryCommentByUserIdAndOrderId(String userId, String orderId);

    /**
     * 根据用户 id 查询评论
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<ItemCommentVo> queryCommentByUserId(String userId, int page, int pageSize);

    /**
     * 发布评论
     * @param userId
     * @param orderId
     * @param itemsComments
     */
    void publishComments(String userId, String orderId, List<ItemsComments> itemsComments);
}
