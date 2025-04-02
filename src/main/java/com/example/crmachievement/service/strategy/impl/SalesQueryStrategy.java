package com.example.crmachievement.service.strategy.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.crmachievement.domain.Customer;
import com.example.crmachievement.domain.enums.BusinessCode;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.security.UserSecurityInfo;
import com.example.crmachievement.service.CustomerService;
import com.example.crmachievement.service.strategy.QueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesQueryStrategy implements QueryStrategy {

    private final CustomerService customerService;

    @Override
    public ServiceResult<?> query(UserSecurityInfo userSecurityInfo) {

        // 执行带销售 ID的查询
        List<Customer> customerList = customerService.list(Wrappers.lambdaQuery(Customer.class)
                .eq(Customer::getUpdatedBy, userSecurityInfo.getUserId()));
        return ServiceResult.success(BusinessCode.SUCCESS,customerList);

    }
}