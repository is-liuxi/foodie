package com.liuxi.service.impl;

import com.liuxi.mapper.UserMapper;
import com.liuxi.pojo.User;
import com.liuxi.pojo.bo.UserBo;
import com.liuxi.service.UserService;
import com.liuxi.util.common.DateUtils;
import com.liuxi.util.common.MD5Utils;
import com.liuxi.util.enums.SexEnum;
import com.liuxi.util.idwork.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/17 4:01
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_FACE_IMG = "https://itcast-haoke-study.oss-cn-guangzhou.aliyuncs.com/images/2022/03/11/16469499083866800.jpg";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = RuntimeException.class)
    @Override
    public boolean queryUsernameIsExist(String username) {

        return userMapper.queryUsernameIsExist(username) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public User createUser(UserBo userBo) {
        User user = new User();
        // 设置全局主键
        user.setId(Sid.nextShort());
        user.setUsername(userBo.getUsername());
        try {
            // 密码进行 MD5 加密
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setNickname(userBo.getUsername());
        user.setFace(DEFAULT_USER_FACE_IMG);
        user.setBirthday(DateUtils.stringToDate("1971-01-01", "yyyy-MM-dd"));
        user.setSex(SexEnum.SECRET.type);
        // 创建、更新时间
        Date date = new Date();
        user.setCreatedTime(date);
        user.setUpdatedTime(date);
        userMapper.createUser(user);
        return user;
    }

    @Override
    public User login(UserBo userBo) {
        User user = new User();
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userMapper.login(user);
    }
}
