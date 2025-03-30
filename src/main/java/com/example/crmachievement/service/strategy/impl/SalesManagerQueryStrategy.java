package com.example.crmachievement.service.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.crmachievement.domain.Customer;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.service.strategy.QueryStrategy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@PreAuthorize("hasRole('sales')")
public class SalesManagerQueryStrategy implements QueryStrategy {
    @Override
    public ServiceResult<?> query(QueryWrapper<Customer> queryWrapper, String userDTO) {
        /*// 从安全上下文获取部门ID（这里假设部门ID存储在用户ID中或需要其他方式获取）
        String departmentId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 构建带部门ID的查询条件
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getDepartmentId, departmentId);

        // 执行查询
        CrmCustomerMapper mapper = CustomerServiceImpl.this.mapper; // 需要注入或重构
        List<Customer> list = mapper.selectList(wrapper);*/

        return null;// ServiceResult.success(list);
    }
}