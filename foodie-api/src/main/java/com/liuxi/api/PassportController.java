package com.liuxi.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuxi.pojo.User;
import com.liuxi.pojo.bo.UserBo;
import com.liuxi.service.UserService;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/17 4:03
 */
@RestController
@RequestMapping("user")
@Api(description = "用户管理", tags = "用户操作")
public class PassportController {

    @Autowired
    private UserService userService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @GetMapping("queryUsernameIsExist")
    @ApiOperation(value = "查询用户名是否存在", notes = "根据用户名查询是否存在")
    public ResultJsonResponse queryUsernameIsExist(@RequestParam String username) {
        // isNotBlank 可以判断空字符串 " "
        if (StringUtils.isBlank(username)) {
            return ResultJsonResponse.errorException("username 不能为空");
        }
        boolean result = userService.queryUsernameIsExist(username);
        return result ? ResultJsonResponse.errorMsg("username 已存在") : ResultJsonResponse.ok();
    }

    @PostMapping("register")
    @ApiOperation(value = "注册用户", notes = "注册用户信息")
    public ResultJsonResponse createUser(@RequestBody UserBo userBo, HttpServletResponse response) {
        if (userBo == null || StringUtils.isBlank(userBo.getPassword()) || StringUtils.isBlank(userBo.getConfirmPassword())
                || StringUtils.isBlank(userBo.getUsername())) {
            return ResultJsonResponse.errorMsg("用户信息不能为空");
        }
        if (!userBo.getPassword().equals(userBo.getConfirmPassword())) {
            return ResultJsonResponse.errorMsg("密码不一致");
        }

        if (userService.queryUsernameIsExist(userBo.getUsername())) {
            return ResultJsonResponse.errorMsg("username 已存在");
        }

        if (userBo.getPassword().length() < 4) {
            return ResultJsonResponse.errorMsg("密码长度不能小于六");
        }
        User user = userService.createUser(userBo);
        setCookieVal(response, user);
        return ResultJsonResponse.ok(user);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录接口", notes = "传入用户名和密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, dataType = "string"),
            @ApiImplicitParam(name = "password", required = true, dataType = "string")
    })
    public ResultJsonResponse login(@RequestBody UserBo userBo, HttpServletResponse response) {
        if (userBo == null || StringUtils.isBlank(userBo.getPassword()) || StringUtils.isBlank(userBo.getUsername())) {
            return ResultJsonResponse.errorMsg("用户信息不能为空");
        }
        User user = userService.login(userBo);
        if (user == null) {
            return ResultJsonResponse.errorMsg("用户名或者密码不正确");
        }
        setCookieVal(response, user);
        return ResultJsonResponse.ok(user);
    }

    @PostMapping("logout")
    @ApiOperation(value = "用户注销", notes = "用户退出登录，删除 cookie 信息")
    public ResultJsonResponse logout(@RequestParam("userId") String userId, HttpServletResponse response) {
        Cookie cookie = new Cookie("user", null);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultJsonResponse.ok();
    }

    /**
     * 设置 cookie 值
     * @param response
     * @param user
     */
    private void setCookieVal(HttpServletResponse response, User user) {
        Cookie cookie = null;
        try {
            String jsonUser = MAPPER.writeValueAsString(user);
            cookie = new Cookie("user", URLEncoder.encode(jsonUser, StandardCharsets.UTF_8.toString()));
            cookie.setPath("/");
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }
}
