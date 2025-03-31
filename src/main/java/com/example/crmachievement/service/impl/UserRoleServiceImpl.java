package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.UserRole;
import com.example.crmachievement.service.UserRoleService;
import com.example.crmachievement.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 001
* @description 针对表【crm_user_role(用户-角色关联表)】的数据库操作Service实现
* @createDate 2025-03-26 04:48:48
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}




