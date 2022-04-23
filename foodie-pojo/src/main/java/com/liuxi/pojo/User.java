package com.liuxi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </P>
 * @author liu xi
 * @date 2022/4/17 2:58
 */
@TableName(value = "users")
@Data
public class User implements Serializable {
    /**
     * 主键id 用户id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 密码 密码
     * @JsonIgnore 注解不会返回到前端
     */
    @JsonIgnore
    private String password;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    @TableField(value = "realname")
    private String realName;

    /**
     * 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private Date birthday;

    /**
     * 创建时间 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新时间 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;
}