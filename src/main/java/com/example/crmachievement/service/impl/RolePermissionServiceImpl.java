package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.RolePermission;
import com.example.crmachievement.service.RolePermissionService;
import com.example.crmachievement.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对表【crm_role_permission(角色-权限关联表)】的数据库操作Service实现
 *
 * @author 001
 * @since 2025-03-26 04:49:13
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService {

    // 根据角色id列表查询权限id列表
    @Override
    public List<String> getPermissionIdsByRoleIds(List<String> roleIds) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePermission::getRoleId, roleIds);

        List<RolePermission> rolePermissions = list(wrapper);

        // 使用 toSet() 去重，然后转换为 ArrayList
        return rolePermissions.stream()
                .map(RolePermission::getId)
                .collect(Collectors.collectingAndThen(
                        Collectors.toSet(), // 使用 Set 去重
                        ArrayList::new)); // 转换为 ArrayList
    }
}




