package com.liuxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuxi.pojo.User;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/17 3:53
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    int queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param user
     * @return
     */
    void createUser(User user);

    /**
     * 用户登录
     * @param user
     */
    User login(User user);
}
