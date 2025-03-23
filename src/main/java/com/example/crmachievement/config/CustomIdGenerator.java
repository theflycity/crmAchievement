package com.example.crmachievement.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import java.util.UUID;

public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        // 如果需要返回数字类型ID，可以实现此方法
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        // 自定义UUID生成逻辑
        return UUID.randomUUID().toString();
    }
}