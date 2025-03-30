package com.example.crmachievement.config;

import com.example.crmachievement.domain.result.ApiResponse;
import com.example.crmachievement.security.UserDetails;
import com.example.crmachievement.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.example.crmachievement.domain.enums.BusinessCode.TOKEN_INVALID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
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
                // 从claims中获取权限信息
                List<String> authorities = (List<String>) claims.get("permissionCodeList");
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                // 获取用户详细信息
                UserDetails userDetails = userService.loadUserById(userId);
                // 创建一个UsernamePasswordAuthenticationToken对象，用于存储认证信息
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
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