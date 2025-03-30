package com.example.crmachievement.service;

import com.example.crmachievement.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmachievement.security.UserDetails;

/**
* @author 001
* @description 针对表【crm_user(用户表)】的数据库操作Service
* @createDate 2025-03-30 15:09:08
*/
public interface UserService extends IService<User> {
    UserDetails loadUserById(String userId);
}
