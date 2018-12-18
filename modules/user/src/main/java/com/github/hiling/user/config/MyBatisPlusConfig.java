package com.github.hiling.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.github.hiling.user.mapper")
public class MyBatisPlusConfig {
}
