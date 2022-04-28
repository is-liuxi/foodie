package com.liuxi.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxi.mapper.OrderStatusMapper;
import com.liuxi.pojo.OrderStatus;
import com.liuxi.util.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 定时任务，处理超时未支付的订单
 * </P>
 * @author liu xi
 * @date 2022/4/22 0:00
 */
@Component
public class CloseTimeoutNotPayOrderTask {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    /**
     * 每二十四小时修改未支付状态
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void autoCloseOrder() {
        // 查询所有待付款的订单
        LambdaQueryWrapper<OrderStatus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderStatus::getOrderStatus, OrderStatusEnum.WAITING_PAYMENT.type);
        List<OrderStatus> orderStatusList = orderStatusMapper.selectList(queryWrapper);
        for (OrderStatus item : orderStatusList) {
            // 订单创建时间
            Date createdTime = item.getCreatedTime();
            // 将 date 转换为 LocalDateTime
            LocalDateTime localDateTimeCreate = dateToLocalDateTime(createdTime);
            LocalDateTime now = LocalDateTime.now();
            Duration durationTime = Duration.between(localDateTimeCreate, now);
            // 订单创建到现在的时间(分钟)
            long hours = durationTime.toMinutes();
            // 三十分钟未支付订单，设置订单状态为关闭
            if (hours > 30) {
                String orderId = item.getOrderId();
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setOrderId(orderId);
                orderStatus.setOrderStatus(OrderStatusEnum.TRAN_FAIL.type);
                orderStatus.setCloseTime(new Date());
                orderStatusMapper.updateById(orderStatus);
            }
        }
    }

    /**
     * Date 转换为 LocalDateTime
     * @param date
     * @return
     */
    public LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        // 系统默认
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * LocalDateTime 转换为 Date
     * @param localDateTime
     * @return
     */
    public Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
}
