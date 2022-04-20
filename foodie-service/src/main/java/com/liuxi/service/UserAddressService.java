package com.liuxi.service;

import com.liuxi.pojo.UserAddress;

import java.util.List;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/20 4:32
 */
public interface UserAddressService {

    /**
     * 查询用户地址
     * @param userId
     * @return
     */
    List<UserAddress> queryUserAddressByUserId(String userId);

    /**
     * 新增用户地址
     * @param userAddress
     * @return
     */
    boolean addUserAddress(UserAddress userAddress);

    /**
     * 修改用户地址
     * @param userAddress
     * @return
     */
    boolean updateUserAddressByUserId(UserAddress userAddress);

    /**
     * 修改默认地址
     * @param userId
     * @param addressId
     * @return
     */
    boolean setDefaultAddress(String userId, String addressId);

    /**
     * 删除收货地址
     * @param userId
     * @param addressId
     * @return
     */
    boolean deleteUserAddressById(String userId, String addressId);
}
