package com.example.crmachievement.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final String departmentId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final List<String> roleIds;
    private final List<String> menus;

    public UserDetails(String id, String username, String password, String departmentId,
                       Collection<? extends GrantedAuthority> authorities, List<String> roleIds, List<String> menus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.departmentId = departmentId;
        this.authorities = authorities;
        this.roleIds = roleIds;
        this.menus = menus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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

    public String getId() {
        return id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public List<String> getMenus() {
        return menus;
    }
}
