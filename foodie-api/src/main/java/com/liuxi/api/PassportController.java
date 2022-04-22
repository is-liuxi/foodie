package com.liuxi.api;

import com.aliyun.oss.OSS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuxi.config.AliYunConfig;
import com.liuxi.pojo.User;
import com.liuxi.pojo.bo.UserRegistryBo;
import com.liuxi.pojo.bo.UserUpdateBo;
import com.liuxi.pojo.page.PageResult;
import com.liuxi.pojo.vo.ItemCommentVo;
import com.liuxi.service.ItemService;
import com.liuxi.service.OrderService;
import com.liuxi.service.UserService;
import com.liuxi.util.FileUtils;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ItemService itemService;
    @Autowired
    private OSS oss;
    @Autowired
    private AliYunConfig aliYunConfig;

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
    public ResultJsonResponse createUser(@RequestBody UserRegistryBo userBo, HttpServletResponse response) {
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
    public ResultJsonResponse login(@RequestBody UserRegistryBo userBo, HttpServletResponse response) {
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

    @GetMapping("userInfo/{userId}")
    @ApiOperation(value = "查询用户信息", notes = "查询用户个人信息")
    public ResultJsonResponse queryUserInfo(@PathVariable("userId") String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResultJsonResponse.errorMsg("用户未登录");
        }
        User user = userService.queryUserInfo(userId);
        return ResultJsonResponse.ok(user);
    }

    @PutMapping("updateUserInfo/{userId}")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public ResultJsonResponse updateUserInfo(@PathVariable("userId") String userId,
                                             @RequestBody @Valid UserUpdateBo user,
                                             BindingResult bindingResult) {
        // 前端传入字段不符合校验判断
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errorsList = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return ResultJsonResponse.errorMap(errorsList);
        }
        String resultUserId = userService.updateUserInfo(userId, user);
        return ResultJsonResponse.ok(resultUserId);
    }

    @PostMapping("uploadFace")
    @ApiOperation(value = "头像上传", notes = "头像上传")
    public ResultJsonResponse uploadFace(@RequestParam("file") MultipartFile multipartFile,
                                         @RequestParam("userId") String userId) {
        try {
            InputStream is = multipartFile.getInputStream();
            String fileExt = StringUtils.substringAfter(multipartFile.getOriginalFilename(), ".");
            String filePath = FileUtils.setFilePath(fileExt);
            oss.putObject(aliYunConfig.getBucket(), filePath, is);
            userService.updateUserFaceImg(userId, aliYunConfig.getUrlFix() + filePath);
            return ResultJsonResponse.ok(aliYunConfig.getUrlFix() + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultJsonResponse.errorMsg("图片上传失败");
        }
    }

    @PostMapping("logout")
    @ApiOperation(value = "用户注销", notes = "用户退出登录，删除 cookie 信息")
    public ResultJsonResponse logout(@RequestParam("userId") String userId, HttpServletResponse response) {
        Cookie cookie = new Cookie("user", null);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultJsonResponse.ok();
    }

    @GetMapping("queryCommentByUserId")
    public ResultJsonResponse queryCommentByUserId(@RequestParam("userId") String userId,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("pageSize") int pageSize) {
        PageResult<ItemCommentVo> pageResult = itemService.queryCommentByUserId(userId, page, pageSize);
        return ResultJsonResponse.ok(pageResult);
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
            cookie.setMaxAge(60 * 30);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }
}
