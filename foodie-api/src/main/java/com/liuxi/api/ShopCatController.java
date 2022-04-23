package com.liuxi.api;

import com.liuxi.pojo.vo.ShopCartVo;
import com.liuxi.service.ShopCartService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/20 3:51
 */
@RestController
@RequestMapping("shopCart")
@Api(description = "购物车", tags = "购物车管理")
public class ShopCatController {

    @Autowired
    private ShopCartService shopCatService;

    @PostMapping("add")
    @ApiOperation(value = "添加购物车", notes = "添加购物车商品到 Cookie 中")
    public ResultJsonResponse addCat(@RequestParam("userId") String userId, @RequestBody ShopCartVo shopCatVo,
                                     HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return ResultJsonResponse.errorMsg("userId 不能为空");
        }

        return ResultJsonResponse.ok();
    }

    @GetMapping("refresh")
    @ApiOperation(value = "根据商品规格id刷新购物车商品数据", notes = "购物车商品查询")
    public ResultJsonResponse refresh(@RequestParam("itemSpecIds") String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return ResultJsonResponse.errorMsg("spu id不能为空");
        }
        List<ShopCartVo> itemList = shopCatService.selectCartsBySpecId(itemSpecIds);
        return ResultJsonResponse.ok(itemList);
    }

    @DeleteMapping("del")
    @ApiOperation(value = "删除购物车", notes = "删除购物车中的商品")
    public ResultJsonResponse delShopCats(@RequestParam("userId") String userId,
                                          @RequestParam("itemSpecId") String itemSpecIds, HttpServletRequest request,
                                          @RequestHeader(value = "headerUserToken", required = false) String user) {
        String headerUserToken = request.getHeader("headerUserToken");
        return ResultJsonResponse.ok();
    }
}
