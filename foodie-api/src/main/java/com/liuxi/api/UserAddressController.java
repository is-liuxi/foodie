package com.liuxi.api;

import com.liuxi.pojo.UserAddress;
import com.liuxi.service.UserAddressService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/20 4:31
 */
@RestController
@RequestMapping("address")
@Api(description = "用户地址管理", tags = "用户地址管理")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("list")
    @ApiOperation(value = "查询用户收货地址", notes = "查询用户收货地址")
    public ResultJsonResponse list(@RequestParam("userId") String userId) {
        if (StringUtils.isBlank(userId) || "undefined".equals(userId)) {
            return ResultJsonResponse.errorMsg("用户未登录");
        }
        return ResultJsonResponse.ok(userAddressService.queryUserAddressByUserId(userId));
    }

    @PostMapping("add")
    @ApiOperation(value = "新增收货地址", notes = "新增收货地址")
    public ResultJsonResponse addUserAddress(@RequestBody UserAddress userAddress) {
        if (userAddress == null || userAddress.getUserId() == null) {
            return ResultJsonResponse.errorMsg("传入数据不能为空");
        }
        if (!userAddressService.addUserAddress(userAddress)) {
            return ResultJsonResponse.errorMsg("新增地址后台出错");
        }
        return ResultJsonResponse.ok();
    }

    @PutMapping("update")
    @ApiOperation(value = "修改收货地址", notes = "修改收货地址")
    public ResultJsonResponse updateUserAddress(@RequestBody UserAddress userAddress) {
        if (userAddress == null || userAddress.getUserId() == null) {
            return ResultJsonResponse.errorMsg("传入数据不能为空");
        }
        if (!userAddressService.updateUserAddressByUserId(userAddress)) {
            return ResultJsonResponse.errorMsg("修改地址后台出错");
        }
        return ResultJsonResponse.ok();
    }

    @PutMapping("setDefault")
    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址")
    public ResultJsonResponse setDefaultAddress(@RequestParam("userId") String userId,
                                                @RequestParam("addressId") String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ResultJsonResponse.errorMsg("传入数据不能为空");
        }
        userAddressService.setDefaultAddress(userId, addressId);
        return ResultJsonResponse.ok();
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public ResultJsonResponse deleteUserAddress(@RequestParam("userId") String userId,
                                                @RequestParam("addressId") String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ResultJsonResponse.errorMsg("传入数据不能为空");
        }
        userAddressService.deleteUserAddressById(userId, addressId);
        return ResultJsonResponse.ok();
    }
}
