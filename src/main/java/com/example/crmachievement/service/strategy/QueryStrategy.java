package com.example.crmachievement.service.strategy;

import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.security.UserSecurityInfo;

@FunctionalInterface
public interface QueryStrategy {
    ServiceResult<?> query(UserSecurityInfo userSecurityInfo);
}
