package com.example.crmachievement.domain.dto;

import com.example.crmachievement.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {
    // 用户ID
    private String userId;
    // 用户名
    private String userName;
    // 部门ID
    private String deptId;
    // 部门名称
    private String deptName;
    // 角色名称列表
    private List<String> roleNameList;
    // 权限标识符列表
    private Collection<? extends GrantedAuthority> permissionPermCode;
    // 权限名称列表
    private List<String> permissionNameList;
    // 菜单列表
    private Collection<Menu> menus;
    // token
    private String token;

    public AuthInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public AuthInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public AuthInfo setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    public AuthInfo setDeptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public AuthInfo setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
        return this;
    }

    public AuthInfo setPermissionPermCode(Collection<? extends GrantedAuthority> permissionPermCode) {
        this.permissionPermCode = permissionPermCode;
        return this;
    }

    public AuthInfo setPermissionNameList(List<String> permissionNameList) {
        this.permissionNameList = permissionNameList;
        return this;
    }

    public AuthInfo setMenus(Collection<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public AuthInfo setToken(String token) {
        this.token = token;
        return this;
    }
}
