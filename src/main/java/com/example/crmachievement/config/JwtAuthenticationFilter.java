package com.example.crmachievement.config;

import com.example.crmachievement.domain.Role;
import com.example.crmachievement.domain.RolePermission;
import com.example.crmachievement.domain.UserRole;
import com.example.crmachievement.domain.dto.UserDTO;
import com.example.crmachievement.domain.result.ApiResponse;
import com.example.crmachievement.security.UserSecurityInfo;
import com.example.crmachievement.service.RolePermissionService;
import com.example.crmachievement.service.RoleService;
import com.example.crmachievement.service.UserRoleService;
import com.example.crmachievement.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.crmachievement.domain.enums.BusinessCode.TOKEN_INVALID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RolePermissionService rolePermissionService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 获取请求头中的Authorization字段(前端处理)
        String authorizationHeader = request.getHeader("Authorization");

        // 检查Authorization字段是否存在且以Bearer开头(前端处理)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取JWT令牌
            String token = authorizationHeader.substring(7);
            try {
                // 解析JWT令牌以获取声明信息
                Claims claims = jwtConfig.parseToken(token);
                // 从声明中获取用户ID
                String userId = claims.getSubject();
                // 从数据库获取用户权限信息
                HashMap<String, Object> columnMap = new HashMap<>();
                columnMap.put("user_id", userId);
                List<String> roleIdList = userRoleService.listByMap(columnMap).stream().map(UserRole::getRoleId).collect(Collectors.toList());
                List<String> permissionCodeList = rolePermissionService.
                        listByIds(roleIdList).stream().
                        map(RolePermission::getPermissionPermCode).
                        collect(Collectors.toList());
                // 创建 GrantedAuthority列表，用于存储用户的权限信息
                List<GrantedAuthority> grantedAuthorities = permissionCodeList.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                // 创建 UserSecurityInfo用于存储用户安全信息
                String userName = userService.getById(userId).getName();
                String password = userService.getById(userId).getPassword();
                UserSecurityInfo userSecurityInfo = new UserSecurityInfo(userId, userName, password, grantedAuthorities);
                // 创建 UserDTO 对象，用于存储用户信息
                String deptId = userService.getById(userId).getDeptId();
                List<String> roleLogicalKeyList = roleService.listByIds(roleIdList).stream().map(Role::getLogicalKey).collect(Collectors.toList());
                UserDTO userDTO = new UserDTO(userId, userName, deptId, roleIdList, roleLogicalKeyList);
                // 创建 UsernamePasswordAuthenticationToken对象，用于存储认证信息(权限信息,安全信息,用户信息)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userSecurityInfo, userDTO, grantedAuthorities);
                // 设置认证信息的详细信息
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将认证信息设置到SecurityContextHolder中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // TODO 友好展示
                // 如果解析JWT令牌失败，设置响应状态码为401 Unauthorized
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // 返回TOKEN_INVALID的错误信息
                response.getWriter().write(ApiResponse.fail(TOKEN_INVALID).toString());
                return;
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}