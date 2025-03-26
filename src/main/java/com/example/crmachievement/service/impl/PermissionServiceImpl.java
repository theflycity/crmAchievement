package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.CrmRolePermission;
import com.example.crmachievement.domain.CrmUser;
import com.example.crmachievement.domain.CrmUserRole;
import com.example.crmachievement.mapper.CrmRolePermissionMapper;
import com.example.crmachievement.mapper.CrmUserMapper;
import com.example.crmachievement.mapper.CrmUserRoleMapper;
import com.example.crmachievement.service.CrmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<CrmUserMapper, CrmUser> implements CrmUserService {

    private final CrmUserRoleMapper crmUserRoleMapper;

    private final CrmRolePermissionMapper crmRolePermissionMapper;

    // 用于查询用户的权限信息
    public List<GrantedAuthority> getUserAuthorities(String userId) {
        // 使用条件构造器查询用户的权限信息
        LambdaQueryWrapper<CrmUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(CrmUser::getId, userId);
        CrmUser user = getOne(userQueryWrapper);

        if (user == null) {
            return Arrays.asList(); // 如果用户不存在，返回空列表
        }

        // 获取用户的角色ID列表
        LambdaQueryWrapper<CrmUserRole> userRoleQueryWrapper = new LambdaQueryWrapper<>();
        userRoleQueryWrapper.eq(CrmUserRole::getUserId, userId);
        List<CrmUserRole> userRoles = crmUserRoleMapper.selectList(userRoleQueryWrapper);
        List<String> roleIds = userRoles.stream().map(CrmUserRole::getRoleId).collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return Arrays.asList(); // 如果角色ID列表为空，返回空列表
        }

        // 根据角色ID列表获取权限标识符列表
        LambdaQueryWrapper<CrmRolePermission> rolePermissionQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionQueryWrapper.in(CrmRolePermission::getRoleId, roleIds);
        List<CrmRolePermission> rolePermissions = crmRolePermissionMapper.selectList(rolePermissionQueryWrapper);
        Set<String> permissionCodes = rolePermissions.stream().map(CrmRolePermission::getPermissionPermCode).collect(Collectors.toSet());

        // 将权限标识符列表转换为GrantedAuthority列表
        return permissionCodes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}