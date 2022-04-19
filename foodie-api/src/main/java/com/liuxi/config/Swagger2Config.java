package com.liuxi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/17 23:14
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 构造 apiInfo 信息
                .apiInfo(builderApiInfo())
                // controller 所在包
                .select().apis(RequestHandlerSelectors.basePackage("com.liuxi.api"))
                // 所有 controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo builderApiInfo() {
        return new ApiInfoBuilder()
                // 文档页标题
                .title("天天吃货 电商平台接口 API")
                // 作者信息
                .contact(new Contact("is-liuxi", "localhost:2233", "is-liuxi@xx.com"))
                // 详情
                .description("天天吃货 Swagger 学习 API")
                // 网站地址
                .termsOfServiceUrl("www.is-liuxi.com")
                // 版本
                .version("1.0.0").build();
    }
}
