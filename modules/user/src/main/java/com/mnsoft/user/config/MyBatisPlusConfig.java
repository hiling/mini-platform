package com.mnsoft.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.mnsoft.user.mapper")
public class MyBatisPlusConfig {
}
