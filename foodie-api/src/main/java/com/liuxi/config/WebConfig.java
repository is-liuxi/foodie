package com.liuxi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *  WEBMVC 设置
 * </P>
 * @author liu xi
 * @date 2022/4/17 23:52
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有路径、所有来源、所有请求方法
        registry.addMapping("/**")
                // 前端地址
                .allowedOrigins("http://localhost:8080")
                // 请求的方式
                .allowedMethods("*")
                // 请求头信息
                .allowedHeaders("*")
                // 是否可以携带数据
                .allowCredentials(true);
    }
}
