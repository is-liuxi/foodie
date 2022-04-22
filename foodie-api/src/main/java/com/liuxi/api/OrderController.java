package com.liuxi.api;

import com.liuxi.pojo.OrderStatus;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.CenterOrderVo;
import com.liuxi.pojo.vo.OrderStatusVo;
import com.liuxi.pojo.vo.ShopCartCreateOrderVo;
import com.liuxi.service.OrderService;
import com.liuxi.util.ConstantUtils;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 2:31
 */
@Api(description = "订单管理", tags = "订单管理")
@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    @ApiOperation(value = "生成订单", notes = "生成订单")
    public ResultJsonResponse createOrder(@RequestBody ShopCartCreateOrderVo shopCartCreateOrderVo, HttpServletResponse response) {
        if (shopCartCreateOrderVo.getPayMethod() == null) {
            return ResultJsonResponse.errorMsg("请选择支付方式");
        }
        String orderId = orderService.createOrder(shopCartCreateOrderVo);
        // 删除购物车中的 Cookie
        Cookie cookie = new Cookie(ConstantUtils.FOOD_SHOP_CART_COOKIE_KEY, "");
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultJsonResponse.ok(orderId);
    }

    @GetMapping("getPaidOrderInfo")
    public ResultJsonResponse getPaidOrderInfo(@RequestParam("orderId") String orderId) {
        OrderStatus orderStatus = orderService.getPaidOrderInfo(orderId);
        return ResultJsonResponse.ok(orderStatus);
    }

    @GetMapping("updateOrderStatus/{orderId}")
    public ResultJsonResponse updateOrderStatus(@PathVariable("orderId") String orderId) {
        orderService.updateOrderStatus(orderId);
        return ResultJsonResponse.ok();
    }

    @GetMapping("statusCounts/{userId}")
    @ApiOperation(value = "查询订单各种状态", notes = "查询订单各种状态")
    public ResultJsonResponse queryOrderStatusCounts(@PathVariable("userId") String userId) {
        OrderStatusVo orderStatus = orderService.queryOrderStatusCounts(userId);
        return ResultJsonResponse.ok(orderStatus);
    }

    @GetMapping("trend")
    @ApiOperation(value = "查看成功的订单", notes = "查看已付款、待发货、已完成订单")
    public ResultJsonResponse trend(@RequestParam("userId") String userId,
                                    @RequestParam("page") int page,
                                    @RequestParam("pageSize") int pageSize) {
        PageResult<OrderStatus> pageResult = orderService.queryOrderTrend(userId, page, pageSize);
        return ResultJsonResponse.ok(pageResult);
    }

    @GetMapping("queryOrderByUserIdAndOrderStatus")
    @ApiOperation(value = "查询个人所有订单", notes = "查询个人所有订单")
    public ResultJsonResponse queryOrderByUserIdAndOrderStatus(@RequestParam("userId") String userId,
                                                               @RequestParam(value = "orderStatus", required = false) Integer status,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("pageSize") int pageSize) {
        if (StringUtils.isEmpty(userId)) {
            return ResultJsonResponse.errorMsg("用户未登录");
        }
        PageResult<CenterOrderVo> resultList = orderService.queryOrderByUserIdAndOrderStatus(userId, status, page, pageSize);
        return ResultJsonResponse.ok(resultList);
    }
}
