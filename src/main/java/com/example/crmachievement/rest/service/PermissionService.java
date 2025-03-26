package com.example.crmachievement.rest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmachievement.domain.CrmUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface PermissionService extends IService<CrmUser> {
    //获取用户权限
    Collection<? extends GrantedAuthority> getUserAuthorities(String userId);
}
