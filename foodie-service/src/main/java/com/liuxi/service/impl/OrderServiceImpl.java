package com.liuxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.liuxi.mapper.*;
import com.liuxi.pojo.*;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.*;
import com.liuxi.service.OrderService;
import com.liuxi.util.common.JsonUtils;
import com.liuxi.util.common.RedisUtils;
import com.liuxi.util.enums.OrderStatusEnum;
import com.liuxi.util.enums.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.liuxi.util.common.ConstantUtils.SHOP_CART_REDIS_KEY;

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
    @Autowired
    private ItemCommentMapper itemCommentMapper;

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

        // 从缓存中获取购物车中商品的数量
        String cacheValue = RedisUtils.get(SHOP_CART_REDIS_KEY + vo.getUserId());
        List<ShopCartVo> shopCartVoList = JsonUtils.jsonToList(cacheValue, ShopCartVo.class);
        // specId：{对象}
        Map<String, List<ShopCartVo>> shopCartListMap = Optional.ofNullable(shopCartVoList).map(List::stream).orElseGet(Stream::empty)
                .collect(Collectors.groupingBy(ShopCartVo::getSpecId));
        // 拿到购买商品规格对应的数量的数量
        this.setByCount(itemList, shopCartListMap);
        // 订单总价格
        int priceDiscount = itemList.stream().mapToInt(item -> item.getPriceDiscount() * item.getBuyCounts()).reduce(Integer::sum).orElse(0);
        // 实际支付价格
        int priceNormal = itemList.stream().mapToInt(item -> item.getPriceNormal() * item.getBuyCounts()).reduce(Integer::sum).orElse(0);
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

        // 删除已经生成订单的缓存数据
        for (String specId : specIds) {
            List<ShopCartVo> shopCartVos = shopCartListMap.get(specId);
            if (CollectionUtils.isNotEmpty(shopCartVos)) {
                shopCartListMap.remove(specId);
            }
        }
        // 未结算的购物车数据重新添加到购物车中
        Iterator<Map.Entry<String, List<ShopCartVo>>> iterator = shopCartListMap.entrySet().iterator();
        List<ShopCartVo> newCacheValue = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, List<ShopCartVo>> next = iterator.next();
            List<ShopCartVo> value = next.getValue();
            newCacheValue.addAll(value);
        }
        RedisUtils.set(SHOP_CART_REDIS_KEY + vo.getUserId(), JsonUtils.writeValueAsString(newCacheValue));
        return orderId;
    }

    /**
     * 设置购买的数量
     * @param itemList
     * @param shopCartListMap
     */
    private void setByCount(List<ShopCartVo> itemList, Map<String, List<ShopCartVo>> shopCartListMap) {
        for (ShopCartVo item : itemList) {
            List<ShopCartVo> cartVoList = shopCartListMap.get(item.getSpecId());
            Integer buyCounts = cartVoList.get(0).getBuyCounts();
            item.setBuyCounts(buyCounts);
        }
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
            Integer buyCounts = item.getBuyCounts();
            orderItems.setBuyCounts(buyCounts);
            orderItems.setPrice(item.getPriceDiscount() * buyCounts);
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

    @Override
    public OrderVo queryOrderByIdAndUserId(String userId, String orderId) {
        OrderVo order = orderMapper.queryOrderByIdAndUserId(userId, orderId);
        order.setQrCodeUrl("http://www.baidu.com");
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.PAID.type);
        orderStatus.setPayTime(new Date());
        orderStatusMapper.updateById(orderStatus);
    }

    @Override
    public OrderStatus getPaidOrderInfo(String orderId) {
        return orderStatusMapper.selectById(orderId);
    }

    @Override
    public OrderStatusVo queryOrderStatusCounts(String userId) {
        return orderStatusMapper.queryOrderStatusCounts(userId);
    }

    @Override
    public PageResult<OrderStatus> queryOrderTrend(String userId, int page, int pageSize) {
        int currentPage = (page - 1) * pageSize;
        List<OrderStatus> orderStatusList = orderStatusMapper.queryOrderTrend(userId, currentPage, pageSize);
        // 总页数
        int records = orderStatusMapper.queryOrderTrendCount(userId);
        int total = records % pageSize;
        total = total == 0 ? records / pageSize : (records / pageSize) + 1;
        return new PageResult<>(page, total, records, orderStatusList);
    }

    @Override
    public PageResult<CenterOrderVo> queryOrderByUserIdAndOrderStatus(String userId, Integer status, int page, int pageSize) {
        int currentPage = (page - 1) * pageSize;
        List<CenterOrderVo> orderList = orderMapper.queryOrderByUserIdAndOrderStatus(userId, status, currentPage, pageSize);
        // 总记录
        int records = orderMapper.queryOrderByUserIdAndOrderStatusCount(userId, status);
        // 总页数
        int total = records % pageSize;
        total = total == 0 ? records / pageSize : (records / pageSize) + 1;
        return new PageResult<>(page, total, records, orderList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void confirmReceive(String userId, String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.TRAN_SUCCESS.type);
        orderStatus.setSuccessTime(new Date());
        orderStatusMapper.updateById(orderStatus);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<OrderItems> queryCommentByUserIdAndOrderId(String userId, String orderId) {
        LambdaQueryWrapper<OrderItems> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderItems::getOrderId, orderId);
        return orderItemMapper.selectList(queryWrapper);
    }

    @Override
    public PageResult<ItemCommentVo> queryCommentByUserId(String userId, int page, int pageSize) {
        int currentPage = (page - 1) * pageSize;
        List<ItemCommentVo> itemCommentList = itemCommentMapper.queryItemCommentByUserId(userId, currentPage, pageSize);
        int records = itemCommentMapper.queryItemCommentByUserIdCount(userId);
        int total = records % pageSize;
        total = total == 0 ? records / pageSize : (records / pageSize) + 1;
        return new PageResult<>(page, total, records, itemCommentList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void publishComments(String userId, String orderId, List<ItemsComments> itemsComments) {
        // order更新评论字段
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNoEnum.YES.type);
        orderMapper.updateById(orders);
        // order-status更新评论时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateById(orderStatus);
        // item-comment 商品评论
        for (ItemsComments itemsComment : itemsComments) {
            itemsComment.setUserId(userId);
            Date date = new Date();
            itemsComment.setCreatedTime(date);
            itemsComment.setUpdatedTime(date);
            itemCommentMapper.insert(itemsComment);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteOrderById(String orderId, String userId) {
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsDelete(YesOrNoEnum.YES.type);
        Date date = new Date();
        order.setUpdatedTime(date);
        orderMapper.updateById(order);

        // 修改订单表中的状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.TRAN_FAIL.type);
        orderStatus.setCloseTime(date);
        orderStatusMapper.updateById(orderStatus);
    }
}
