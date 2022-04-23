package com.liuxi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
     * 静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // SWAGGER-UI 映射，设置其它的会被覆盖
                .addResourceLocations("classpath:/META-INF/resources/")
                // 文件目录映射
                .addResourceLocations("file:\\E:\\images\\");
    }

    /**
     * 跨域设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有路径、所有来源、所有请求方法
        registry.addMapping("/**")
                // 前端地址 http://localhost:8080
                .allowedOrigins("*")
                // 请求的方式
                .allowedMethods("*")
                // 请求头信息
                .allowedHeaders("*")
                // 是否可以携带数据
                .allowCredentials(true)
                .exposedHeaders("simple");
    }
}
