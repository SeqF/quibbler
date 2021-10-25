package com.ps.quibbler.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ps
 */
@Configuration
@MapperScan("com.ps.quibbler.mapper")
public class MyBatisPlusConfig {
}
