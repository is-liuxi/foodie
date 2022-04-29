package com.liuxi.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/28 18:27
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EsApplicationMain {
    public static void main(String[] args) {

        SpringApplication.run(EsApplicationMain.class, args);
    }
}
