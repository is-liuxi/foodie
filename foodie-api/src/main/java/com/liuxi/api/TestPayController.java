package com.liuxi.api;

import com.liuxi.mapper.OrderStatusMapper;
import com.liuxi.pojo.OrderStatus;
import com.liuxi.util.common.ResultJsonResponse;
import com.liuxi.util.enums.OrderStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 22:26
 */
@RestController
@Api(description = "用于接口调用", tags = "用于方便测试，接口调用")
public class TestPayController {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @GetMapping("testPay")
    @ApiOperation(value = "用于支付接口调用")
    public ResultJsonResponse testPay() {
        return ResultJsonResponse.ok();
    }

    @PutMapping("deliverItem/{orderId}")
    @ApiOperation(value = "发货接口", notes = "用于商品发货接口调用")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultJsonResponse deliverItem(@PathVariable("orderId") String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.SEED_GOODS.type);
        orderStatus.setDeliverTime(new Date());
        orderStatusMapper.updateById(orderStatus);
        return ResultJsonResponse.ok();
    }
}
