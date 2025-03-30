package com.example.crmachievement.service;

import com.example.crmachievement.domain.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 001
* @description 针对表【crm_role_permission(角色-权限关联表)】的数据库操作Service
* @createDate 2025-03-26 04:49:13
*/
public interface RolePermissionService extends IService<RolePermission> {

    List<String> getPermissionIdsByRoleIds(List<String> roleIds);
}
