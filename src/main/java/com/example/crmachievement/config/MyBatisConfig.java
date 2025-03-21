package com.example.crmachievement.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.crmachievement.mapper")
public class MyBatisConfig {
}
