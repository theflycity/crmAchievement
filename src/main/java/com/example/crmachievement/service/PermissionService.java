package com.example.crmachievement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmachievement.domain.Permission;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface PermissionService extends IService<Permission> {
    // 获取用户权限列表
    List<Permission> getPermissionListByPermissionIds(List<String> permissionIds);

    Collection<? extends GrantedAuthority> getPermissionCodeListByPermissionIds(List<String> permissionIds);
}
