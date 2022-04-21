package com.liuxi.service.impl;

import com.liuxi.mapper.*;
import com.liuxi.pojo.OrderItems;
import com.liuxi.pojo.OrderStatus;
import com.liuxi.pojo.Orders;
import com.liuxi.pojo.UserAddress;
import com.liuxi.pojo.vo.ShopCartCreateOrderVo;
import com.liuxi.pojo.vo.ShopCartVo;
import com.liuxi.service.OrderService;
import com.liuxi.util.enums.OrderStatusEnum;
import com.liuxi.util.enums.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 0:05
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserAddressMapper addressMapper;
    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private ItemSpecMapper itemSpecMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String createOrder(ShopCartCreateOrderVo vo) {
        // 查询订单中商品的信息
        String[] specIds = vo.getItemSpecIds().split(",");
        List<String> specIdList = Arrays.asList(specIds);
        List<ShopCartVo> itemList = shopCartMapper.selectCartsBySpecId(specIdList);
        // 检查 spu 库存
        for (ShopCartVo item : itemList) {
            if (item.getSpecStock() <= 0) {
                throw new RuntimeException("订单未能提交成功：您所购买的\n\t【" + item.getItemName() + " 】\n库存不足");
            }
        }

        Orders order = new Orders();
        Date date = new Date();
        // 设置订单基本信息
        this.setOrderBasicInfo(order, vo, date);
        // TODO 订单中价格+数量
        // 订单总价格
        int priceDiscount = itemList.stream().mapToInt(ShopCartVo::getPriceDiscount).reduce(Integer::sum).orElse(0);
        // 实际支付价格
        int priceNormal = itemList.stream().mapToInt(ShopCartVo::getPriceNormal).reduce(Integer::sum).orElse(0);
        order.setRealPayAmount(priceNormal);
        order.setTotalAmount(priceDiscount);
        // 保存到数据库中
        orderMapper.insert(order);

        // 拿到新增的订单 id
        String orderId = order.getId();
        // 订单、商品关联表数据新增，并检查库存
        this.saveOrderItemData(itemList, orderId);
        // 交易状态
        this.saveOrderStatus(orderId, date);
        // TODO 删除购物车中商品
        return orderId;
    }

    /**
     * 新增订单状态表数据
     * @param orderId
     * @param date
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveOrderStatus(String orderId, Date date) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAITING_PAYMENT.type);
        orderStatus.setCreatedTime(date);
        orderStatusMapper.insert(orderStatus);
    }

    /**
     * 订单、商品关联表新增
     * @param itemList
     * @param orderId
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveOrderItemData(List<ShopCartVo> itemList, String orderId) {
        for (ShopCartVo item : itemList) {
            int buyCount = 1;
            if (item.getSpecStock() - buyCount <= 0) {
                throw new RuntimeException("订单未能提交成功：您所购买的\n\t【" + item.getItemName() + " 】\n库存不足");
            }
            OrderItems orderItems = new OrderItems();
            orderItems.setOrderId(orderId);
            orderItems.setItemId(item.getItemId());
            orderItems.setItemImg(item.getItemImgUrl());
            orderItems.setItemName(item.getItemName());
            orderItems.setItemSpecId(item.getSpecId());
            orderItems.setItemSpecName(item.getSpecName());
            orderItems.setBuyCounts(1);
            // TODO 价格、数量
            orderItems.setPrice(item.getPriceDiscount());
            orderItemMapper.insert(orderItems);
            // 减去库存。使用乐观锁
            int checkStock = itemSpecMapper.decrItemStock(buyCount, item.getSpecId());
            if (checkStock == 0) {
                // 库存不足
                throw new RuntimeException("库存不足：" + item.getItemName());
            }
        }
    }

    /**
     * 设置订单基本信息
     * @param order
     * @param vo
     * @param date
     * @return
     */
    private void setOrderBasicInfo(Orders order, ShopCartCreateOrderVo vo, Date date) {
        order.setUserId(vo.getUserId());
        // 支付方式
        order.setPayMethod(vo.getPayMethod());
        // 订单备注
        order.setLeftMsg(vo.getLeftMsg());

        // 查询订单选择的收货地址
        UserAddress address = addressMapper.selectById(vo.getAddressId());
        // 收货地址
        order.setReceiverName(address.getReceiver());
        order.setReceiverMobile(address.getMobile());
        order.setReceiverAddress(address.getProvince() + " " + address.getCity() + " " + address.getDistrict() + " " + address.getDetail());

        // 设置订单默认值
        // 邮费
        order.setPostAmount(0);
        // 是否评价
        order.setIsComment(YesOrNoEnum.NO.type);
        // 是否删除，逻辑删除
        order.setIsDelete(YesOrNoEnum.NO.type);
        order.setCreatedTime(date);
        order.setUpdatedTime(date);
    }
}
