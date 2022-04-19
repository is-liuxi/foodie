package com.liuxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * 引导类
 * </P>
 * @author liu xi
 * @date 2022/4/17 1:58
 */
@SpringBootApplication
public class ApiApplicationMain {
    public static void main(String[] args) {
        // 返回一个正在运行得 ApplicationContext 对象
        SpringApplication.run(ApiApplicationMain.class, args);
    }
}
