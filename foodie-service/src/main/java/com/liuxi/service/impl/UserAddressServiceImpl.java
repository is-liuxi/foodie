package com.liuxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuxi.mapper.UserAddressMapper;
import com.liuxi.pojo.UserAddress;
import com.liuxi.service.UserAddressService;
import com.liuxi.util.enums.YesOrNoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/20 4:34
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> queryUserAddressByUserId(String userId) {
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddress::getUserId, userId);
        return userAddressMapper.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean addUserAddress(UserAddress userAddress) {
        Date date = new Date();
        userAddress.setUpdatedTime(date);
        userAddress.setCreatedTime(date);
        userAddress.setIsDefault(YesOrNoEnum.NO.type);
        return userAddressMapper.insert(userAddress) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateUserAddressByUserId(UserAddress userAddress) {
        Date date = new Date();
        userAddress.setUpdatedTime(date);
        userAddress.setCreatedTime(date);
        return userAddressMapper.updateById(userAddress) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean setDefaultAddress(String userId, String addressId) {
        // 将原有的默认地址修改为 0
        userAddressMapper.updateDefaultAddressInvalid(userId);
        userAddressMapper.updateDefaultAddressStatus(userId, addressId);
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean deleteUserAddressById(String userId, String addressId) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("user_id", userId);
        map.put("id", addressId);
        return userAddressMapper.deleteByMap(map) > 0;
    }
}
