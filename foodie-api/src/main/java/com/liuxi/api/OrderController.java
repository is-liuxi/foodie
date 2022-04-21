package com.liuxi.api;

import com.liuxi.pojo.vo.ShopCartCreateOrderVo;
import com.liuxi.service.OrderService;
import com.liuxi.util.ConstantUtils;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
