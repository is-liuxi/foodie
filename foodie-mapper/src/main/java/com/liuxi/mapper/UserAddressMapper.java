package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.UserAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/20 4:35
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    /**
     * 修改原来的默认地址无效
     * @param userId
     * @return
     */
    @Update("UPDATE user_address SET is_default = 0, updated_time = now() WHERE user_id = #{userId} AND is_default = 1")
    int updateDefaultAddressInvalid(String userId);

    /**
     * 修改成默认地址
     * @param userId
     * @param addressId
     * @return
     */
    @Update("UPDATE user_address SET is_default = 1, updated_time = now() WHERE user_id = #{userId} AND id = #{addressId}")
    int updateDefaultAddressStatus(@Param("userId") String userId, @Param("addressId") String addressId);
}
