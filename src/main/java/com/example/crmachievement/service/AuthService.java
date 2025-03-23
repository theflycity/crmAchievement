package com.example.crmachievement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmachievement.domain.CrmUser;
import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.result.ServiceResult;

public interface AuthService extends IService<CrmUser> {
    ServiceResult<AuthInfo> login(String username, String inputPassword);
}
