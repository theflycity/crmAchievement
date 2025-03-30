package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.User;
import com.example.crmachievement.mapper.UserMapper;
import com.example.crmachievement.security.UserDetails;
import com.example.crmachievement.service.PermissionService;
import com.example.crmachievement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PermissionService permissionServiceBK;

    @Override
    public UserDetails loadUserById(String userId) {
        /*// 获取用户信息
        User user = getById(userId);
        if (user == null) {
            // todo 异常处理
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }

        // 获取角色列表
        List<String> roleIds = permissionServiceBK.getUserRoles(userId);

        // 获取权限列表
        List<String> authorities = permissionServiceBK.getPermissionListByPermissionIds(userId).stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        // 获取菜单列表
        List<String> menus = permissionServiceBK.getUserMenus(userId);

        return new UserDetails(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getDeptId(),
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                roleIds,
                menus );*/


        return null;
    }
}




