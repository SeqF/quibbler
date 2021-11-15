package com.ps.quibbler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author ps
 */
@SpringBootApplication
public class QuibblerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuibblerApplication.class, args);
    }
}
