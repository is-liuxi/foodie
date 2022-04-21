package com.liuxi.api;

import com.liuxi.pojo.vo.OrderVo;
import com.liuxi.service.OrderService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 18:59
 */
@RestController
@Api(description = "支付页面", tags = "支付页面处理")
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @PostMapping("getWXPayQRCode")
    @ApiOperation(value = "生成微信支付二维码", notes = "生成微信支付二维码")
    public ResultJsonResponse setWeChatPayQrCode(@RequestParam("merchantUserId") String merchantUserId,
                                                 @RequestParam("merchantOrderId") String merchantOrderId) {
        if (StringUtils.isEmpty(merchantUserId)) {
            return ResultJsonResponse.errorMsg("用户未登录");
        }
        OrderVo order = orderService.queryOrderByIdAndUserId(merchantUserId, merchantOrderId);
        return ResultJsonResponse.ok(order);
    }

    @PostMapping("goAiPay")
    @ApiOperation(value = "生成微信支付二维码", notes = "生成微信支付二维码")
    public ResultJsonResponse goAiPay(@RequestParam("merchantUserId") String merchantUserId,
                                                 @RequestParam("merchantOrderId") String merchantOrderId) {
        if (StringUtils.isEmpty(merchantUserId)) {
            return ResultJsonResponse.errorMsg("用户未登录");
        }
        OrderVo order = orderService.queryOrderByIdAndUserId(merchantUserId, merchantOrderId);
        return ResultJsonResponse.ok(order);
    }
}
