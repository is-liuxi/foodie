package com.liuxi.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/18 17:14
 */
@Configuration
@ConfigurationProperties(prefix = "aliyun")
@PropertySource("classpath:aliyun.properties")
@Data
public class AliYunConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String urlFix;
    private String bucket;
    private String endpoint;

    @Bean
    public OSS oss() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
