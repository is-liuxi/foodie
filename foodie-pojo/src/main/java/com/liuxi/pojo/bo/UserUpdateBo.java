package com.liuxi.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/22 4:51
 */
@Data
public class UserUpdateBo {
    private String userId;

    @Length(message = "昵称长度不能超过12位")
    @NotNull(message = "昵称不能为空")
    @ApiModelProperty(value = "用户名", name = "nickname", example = "jackson", required = true)
    private String nickname;

    @Length(message = "真实姓名长度不能超过12位")
    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @Length(message = "手机号长度为11位", min = 11, max = 11)
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "请输入有效的手机号码！")
    private String mobile;

    @Email(message = "请输入有效的邮箱地址！")
    @NotEmpty(message = "邮箱地址不能为空")
    private String email;

    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    private Integer sex;

    @NotNull(message = "日期格式不正确")
    @Past(message = "必须是一个过去的日期")
    private Date birthday;
}
