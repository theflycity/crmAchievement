package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.common.exception.BadRequestException;
import com.example.crmachievement.config.JwtConfig;
import com.example.crmachievement.domain.*;
import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.mapper.UserMapper;
import com.example.crmachievement.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.crmachievement.domain.enums.BusinessCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private final UserService userService;
    private final DeptService deptService;
    private final UserRoleService userRoleService;
    private final RolePermissionService rolePermissionService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final MenuService menuService;
    private final JwtConfig jwtConfig;

    @Override
    public ServiceResult<AuthInfo> login(String username, String inputPassword) {

            // 根据用户名获取用户信息
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getName, username));

            // 用户空值处理,状态验证
            if (user == null || user.getStatus() == 0) {
                throw new BadRequestException(USER_NOT_FOUND);
            }

            // 密码验证
            // todo 密码加密
            if (!Objects.equals(inputPassword, user.getPassword())) {
                throw new BadRequestException(PASSWORD_ERROR);
            }

            // 生成 AuthInfo并添加信息到 AuthInfo
            AuthInfo authInfo = new AuthInfo().setUserId(user.getId()).setUserName(user.getName()).setDeptId(user.getDeptId());

            // 查询部门名称
            String deptName = deptService.getById(user.getDeptId()).getName();
            // 添加信息到 AuthInfo
            authInfo.setDeptName(deptName);

            // 查询角色ID列表
            HashMap<String, Object> columnMap1 = new HashMap<String, Object>();
            columnMap1.put("user_id", user.getId());
            List<String> roleIdList = userRoleService.listByMap(columnMap1).stream().map(UserRole::getRoleId).collect(Collectors.toList());

            // 角色ID列表空值处理
            if (!roleIdList.isEmpty()) {
                // 查询角色姓名列表
                List<String> roleNames = roleService.listByIds(roleIdList).stream().map(Role::getName).collect(Collectors.toList());
                // 添加角色姓名信息到 authInfo
                authInfo.setRoleNameList(roleNames);

                // 查询权限ID列表
                LambdaQueryWrapper<RolePermission> rolePermissionListWrapper = new LambdaQueryWrapper<>();
                rolePermissionListWrapper.in(RolePermission::getRoleId, roleIdList);
                List<RolePermission> rolePermissionList = rolePermissionService.list(rolePermissionListWrapper);
                List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

                // 查询权限ID列表空值处理
                if (!permissionIds.isEmpty()) {
                    // 查询权限标识符列表
                    Collection<? extends GrantedAuthority> permissionPermCodeList = rolePermissionList.stream().map(RolePermission::getPermissionPermCode).distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    authInfo.setPermissionPermCode(permissionPermCodeList);
                    // 查询权限名称列表
                    List<String> permissionNameList = permissionService.listByIds(permissionIds).stream().map(Permission::getPermName).collect(Collectors.toList());
                    authInfo.setPermissionNameList(permissionNameList);

                    // 提取权限代码列表为 String 类型
                    List<String> permissionCodeList = rolePermissionList.stream().map(RolePermission::getPermissionPermCode).collect(Collectors.toList());
                    // 查询菜单信息列表
                    LambdaQueryWrapper<Menu> menusListWrapper = new LambdaQueryWrapper<>();
                    menusListWrapper.in(Menu::getPermCode, permissionCodeList);
                    List<Menu> menusList = menuService.list(menusListWrapper);
                    authInfo.setMenus(menusList);
                }
            }
            // 生成JWT
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("用户名", username);
            String token = jwtConfig.createToken(user.getId(), claims);
            // 添加JWT到 AuthInfo
            authInfo.setToken(token);
            // 返回认证信息
            return ServiceResult.success(SUCCESS, authInfo);

    }
}
