package com.example.crmachievement.service.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crmachievement.domain.Customer;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.service.strategy.QueryStrategy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@PreAuthorize("hasRole('sales')")
public class SalesQueryStrategy implements QueryStrategy {

    @Override
    public ServiceResult<?> query(QueryWrapper<Customer> queryWrapper, String userDTO) {
        /*// 构建带销售ID的查询条件
        wrapper.eq(Customer::getUpdatedBy, salesmanId);

        // 执行查询
        CrmCustomerMapper mapper = CustomerServiceImpl.this.mapper; // 需要注入或重构
        List<Customer> list = mapper.selectList(wrapper);*/

        return null;// ServiceResult.success(list);

    }
}