package com.liuxi.api;

import com.liuxi.util.common.ResultJsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/21 22:26
 */
@RestController
public class TestPayController {

    @GetMapping("testPay")
    public ResultJsonResponse testPay() {
        return ResultJsonResponse.ok();
    }
}
