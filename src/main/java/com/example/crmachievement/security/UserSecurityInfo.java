package com.example.crmachievement.security;

import com.example.crmachievement.domain.Menu;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserSecurityInfo implements UserDetails {
    // 用户id
    private final String userId;
    // 用户名
    private final String username;
    // 密码
    private final String password;
    // 权限标识符列表
    //private List<String> permissionPermCode;
    private final Collection<? extends GrantedAuthority> authorities;
    // 部门id
    private final String departmentId;
    // 角色id列表
    private final List<String> roleIdList;
    // 角色逻辑标识符列表
    private final List<String> roleLogicalKeyList;
    // 权限id列表
    private final List<String> permissionIdList;
    // 权限名称列表
    private final List<String> permissionNameList;
    // 菜单id列表
    private final List<String> menuIdList;
    // 菜单列表
    private final List<Menu> menus;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
