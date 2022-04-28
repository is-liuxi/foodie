package com.liuxi.api;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.liuxi.pojo.vo.ShopCartVo;
import com.liuxi.service.ShopCartService;
import com.liuxi.util.common.JsonUtils;
import com.liuxi.util.common.RedisUtils;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.liuxi.util.common.ConstantUtils.SHOP_CART_REDIS_KEY;

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
public class ShopCatController extends BaseController {

    @Autowired
    private ShopCartService shopCatService;

    @PostMapping("add")
    @ApiOperation(value = "添加购物车", notes = "添加购物车商品到 Cookie 中")
    public ResultJsonResponse addCat(@RequestParam("userId") String userId, @RequestBody ShopCartVo shopCatVo,
                                     HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(userId)) {
            return ResultJsonResponse.errorMsg("userId 不能为空");
        }

        // 用户已登录，将购物车中的信息同步到缓存中
        String cacheValue = RedisUtils.get(SHOP_CART_REDIS_KEY + userId);
        List<ShopCartVo> cacheShopCartList;
        if (StringUtils.isNotBlank(cacheValue)) {
            boolean flag = false;
            // 从缓存中拿到数据
            cacheShopCartList = JsonUtils.jsonToList(cacheValue, ShopCartVo.class);

            // 如果添加的商品已经存在，增加购物车中的数量
            for (ShopCartVo item : cacheShopCartList) {
                if (item.getSpecId().equals(shopCatVo.getSpecId())) {
                    item.setBuyCounts(item.getBuyCounts() + shopCatVo.getBuyCounts());
                    flag = true;
                }
            }
            if (!flag) {
                cacheShopCartList.add(shopCatVo);
            }
        } else {
            // 缓存中没有数据
            cacheShopCartList = new ArrayList<>();
            cacheShopCartList.add(shopCatVo);
        }
        // 更新缓存，覆盖旧值
        String newCacheValue = JsonUtils.writeValueAsString(cacheShopCartList);
        RedisUtils.set(SHOP_CART_REDIS_KEY + userId, newCacheValue);
        response.addCookie(updateCookie(newCacheValue));
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

        // 更新购物车中的 Cookie 缓存数据
        String cacheValue = RedisUtils.get(SHOP_CART_REDIS_KEY + userId);
        List<ShopCartVo> shopCartVoList = JsonUtils.jsonToList(cacheValue, ShopCartVo.class);
        if (CollectionUtils.isNotEmpty(shopCartVoList)) {
            shopCartVoList.removeIf(item -> itemSpecIds.equals(item.getSpecId()));
        }
        RedisUtils.set(SHOP_CART_REDIS_KEY + userId, JsonUtils.writeValueAsString(shopCartVoList));
        return ResultJsonResponse.ok();
    }
}
