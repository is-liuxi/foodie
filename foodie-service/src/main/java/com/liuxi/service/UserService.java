package com.liuxi.service;

import com.liuxi.pojo.User;
import com.liuxi.pojo.bo.UserRegistryBo;
import com.liuxi.pojo.bo.UserUpdateBo;

/**
 * <p>
 *
 * </P>
 * @author LiuXi
 * @date 2022/4/17 4:01
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBo
     * @return
     * @throws Exception
     */
    User createUser(UserRegistryBo userBo);

    /**
     * 用户登录
     * @param userBo
     * @return
     */
    User login(UserRegistryBo userBo);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    User queryUserInfo(String userId);

    /**
     * 更新用户信息
     * @param userId
     * @param userBo
     * @return
     */
    String updateUserInfo(String userId, UserUpdateBo userBo);

    /**
     * 修改用户头像
     * @param userId
     * @param faceUrl
     */
    void updateUserFaceImg(String userId, String faceUrl);
}
