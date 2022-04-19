package com.liuxi.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/17 5:15
 */
@Data
@ApiModel(value = "用户对象BO", description = "从客户端传入的对象")
public class UserVo implements Serializable {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
}
