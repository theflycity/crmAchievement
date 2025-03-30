package com.example.crmachievement.service.strategy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crmachievement.domain.Customer;
import com.example.crmachievement.domain.result.ServiceResult;

@FunctionalInterface
public interface QueryStrategy {
    ServiceResult<?> query(QueryWrapper<Customer> queryWrapper, String userDTO);
}
