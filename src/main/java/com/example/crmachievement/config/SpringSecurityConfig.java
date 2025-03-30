package com.example.crmachievement.config;

import com.example.crmachievement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtConfig jwtConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 禁用跨站请求伪造（CSRF）保护
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用无状态会话管理，不创建或使用HttpSession
            .and()
            .authorizeRequests() // 配置URL访问权限控制
            .antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs", "/api/auth/login").permitAll() // 放行的路径
            .antMatchers("/api/customer/**").authenticated() // 需要认证的接口
            .anyRequest().permitAll(); // 其他请求放行
        // todo 友好展示
        // 添加JWT过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(userService, jwtConfig), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}