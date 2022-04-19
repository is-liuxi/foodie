package com.liuxi.api;

import com.aliyun.oss.OSS;
import com.liuxi.config.AliYunConfig;
import com.liuxi.util.FileUtils;
import com.liuxi.util.common.ResultJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 17:18
 */
@RestController
@RequestMapping("upload")
@Api(description = "文件上传", tags = "图片上传")
public class UploadImageController {

    @Autowired
    private OSS oss;

    @Autowired
    private AliYunConfig aliYunConfig;

    @PostMapping("uploadImage")
    @ApiOperation(value = "上传图片", notes = "上传图片到 阿里云 OSS 中")
    public ResultJsonResponse uploadImage(@RequestParam("file") MultipartFile file) {
        // 获取文件后缀
        String filePath = FileUtils.setFilePath(StringUtils.substringAfter(file.getOriginalFilename(), "."));
        try {
            oss.putObject(aliYunConfig.getBucket(), filePath, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultJsonResponse.ok(aliYunConfig.getUrlFix() + filePath);
    }
}
