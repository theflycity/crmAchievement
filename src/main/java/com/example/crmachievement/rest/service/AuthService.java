package com.example.crmachievement.rest.service;

import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.result.ServiceResult;

public interface AuthService {
    ServiceResult<AuthInfo> login(String name, String password);
}
