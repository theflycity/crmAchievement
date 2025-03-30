package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.config.JwtConfig;
import com.example.crmachievement.domain.Menu;
import com.example.crmachievement.domain.Permission;
import com.example.crmachievement.domain.Role;
import com.example.crmachievement.domain.User;
import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.mapper.UserMapper;
import com.example.crmachievement.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.crmachievement.domain.enums.BusinessCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;
    private final MenuService menuService;

    private final JwtConfig jwtConfig;

    @Override
    public ServiceResult<AuthInfo> login(String username, String inputPassword) {
        try {
            // 根据用户名获取用户信息
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getName, username));

            // 用户空值处理,状态验证
            if (user == null || user.getStatus() == 0) {
                return ServiceResult.fail(USER_NOT_FOUND);
            }

            // 密码验证
            // todo 密码加密
            if (!Objects.equals(inputPassword, user.getPassword())) {
                return ServiceResult.fail(PASSWORD_ERROR);
            }

            // 查询角色ID列表
            List<String> roleIds = userRoleService.getRoleIdsByUserId(user.getId());

            // 生成 JWT的HashMap
            Map<String, Object> claims = new HashMap<>();

            // 角色ID列表空值处理
            if (roleIds != null && roleIds.isEmpty()) {

                // 查询角色信息
                List<Role> roleList = roleService.listByIds(roleIds);

                // 查询权限ID列表
                List<String> permissionIds = rolePermissionService.getPermissionIdsByRoleIds(roleIds);

                // 查询权限ID列表空值处理
                if (permissionIds != null && permissionIds.isEmpty()) {

                    // 查询权限信息列表
                    List<Permission> permissionList = permissionService.getPermissionListByPermissionIds(permissionIds);

                    // 查询权限标识符列表
                    Collection<? extends GrantedAuthority> permissionCodeCollection = permissionService.getPermissionCodeListByPermissionIds(permissionIds);

                    // 查询菜单信息列表
                    List<Menu> menusList = menuService.getMenusListByPermissionList(permissionCodeCollection);


                    // 添加用户菜单信息到claims
                    claims.put("menusList", menusList);
                    // 添加用户权限信息到claims
                    claims.put("permissionList", permissionList);
                    // 添加用户权限标识符列表到claims
                    claims.put("permissionCodeList", permissionCodeCollection);

                }
                // 添加用户角色信息到claims
                claims.put("roleList", roleList);
            }
            // 添加用户信息到claims
            claims.put("user", user);
            // 生成JWT
            String token = jwtConfig.createToken(user.getId(), claims);
            // 返回认证信息
            return ServiceResult.success(SUCCESS, new AuthInfo(token, user.getId(), user.getName()));


        } catch (Exception e) {
            // 异常处理（如数据库断开）
            return ServiceResult.fail(INTERNAL_ERROR);
        }
    }
}
