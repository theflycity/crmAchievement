package com.example.crmachievement.common.generator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component // 确保Spring能自动注册Bean
public class IdGenerator implements IdentifierGenerator {

    @Override
    public boolean assignId(Object idValue) {
        return IdentifierGenerator.super.assignId(idValue);
    }

    @Override
    public Number nextId(Object entity) {
        // 实现具体的ID生成逻辑，例如使用Snowflake算法生成数字ID
        return System.currentTimeMillis();
    }

    @Override
    public String nextUUID(Object entity) {
        Date now = (Date) entity;
        String uuid = UUID.randomUUID().toString();
        // 截取时间戳的后 8 位
        String timestamp = String.valueOf(now.getTime()).substring(5);
        return timestamp + "-" + uuid.substring(0, 27); // 保持总长度为 36 位
    }
}