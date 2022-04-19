package com.liuxi.service;

import com.liuxi.pojo.User;
import com.liuxi.pojo.vo.UserVo;

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
    User createUser(UserVo userBo);

    /**
     * 用户登录
     * @param userBo
     * @return
     */
    User login(UserVo userBo);
}
