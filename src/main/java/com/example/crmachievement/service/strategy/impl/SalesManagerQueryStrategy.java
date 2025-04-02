package com.example.crmachievement.service.strategy.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.crmachievement.domain.Customer;
import com.example.crmachievement.domain.User;
import com.example.crmachievement.domain.enums.BusinessCode;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.security.UserSecurityInfo;
import com.example.crmachievement.service.CustomerService;
import com.example.crmachievement.service.UserService;
import com.example.crmachievement.service.strategy.QueryStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SalesManagerQueryStrategy implements QueryStrategy {
    private final UserService userService;
    private final CustomerService customerService;
    @Override
    public ServiceResult<?> query(UserSecurityInfo userSecurityInfo) {
        // 根据部门id查询用户id列表
        List<String> userIdList = userService.list(Wrappers.lambdaQuery(User.class)
                        .eq(User::getDeptId, userSecurityInfo.getDepartmentId()))
                        .stream().map(User::getId).collect(Collectors.toList());
       // 根据用户id列表查询客户列表
        List<Customer> customerList = customerService.list(Wrappers.lambdaQuery(Customer.class)
                .in(Customer::getUpdatedBy, userIdList));

        return ServiceResult.success(BusinessCode.SUCCESS,customerList);
    }
}