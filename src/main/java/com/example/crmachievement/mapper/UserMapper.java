package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 001
* @description 针对表【crm_user(用户表)】的数据库操作Mapper
* @createDate 2025-03-30 15:09:08
* @Entity com.example.crmachievement.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT role_id FROM crm_user_role WHERE user_id = #{userId}")
    List<String> getRoleIdsByUserId(@Param("userId") String userId);
}




