package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.Permission;
import com.example.crmachievement.mapper.*;
import com.example.crmachievement.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> getPermissionListByPermissionIds(List<String> permissionIds) {
        // 根据权限ID列表获取权限列表
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Permission::getId, permissionIds);

        List<Permission> permissions = list(wrapper);

        // 使用 HashSet 去重，然后转换为 ArrayList
        return new ArrayList<>(new HashSet<>(permissions));
    }

    @Override
    public Collection<? extends GrantedAuthority> getPermissionCodeListByPermissionIds(List<String> permissionIds) {
        // 根据权限ID列表获取权限列表
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Permission::getId, permissionIds);

        List<Permission> permissions = list(wrapper);

        // 使用 toSet() 去重，然后转换为 GrantedAuthority 列表
        return permissions.stream()
                .map(Permission::getPermCode) // 假设 Permission 类中有 getCode() 方法
                .collect(Collectors.toSet())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}