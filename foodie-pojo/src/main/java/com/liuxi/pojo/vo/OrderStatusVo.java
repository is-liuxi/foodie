package com.liuxi.pojo.vo;

import lombok.Data;

/**
 * <p>
 *  订单状态
 * </P>
 * @author liu xi
 * @date 2022/4/22 3:16
 */
@Data
public class OrderStatusVo {
    /**
     * 待付款
     */
    private Integer waitPayCounts;
    /**
     * 待发货
     */
    private Integer waitDeliverCounts;
    /**
     * 待收货
     */
    private Integer waitReceiveCounts;
    /**
     * 待评价
     */
    private Integer waitCommentCounts;

}
