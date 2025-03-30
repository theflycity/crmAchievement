package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 001
* @description 针对表【crm_user_role(用户-角色关联表)】的数据库操作Mapper
* @createDate 2025-03-26 04:48:48
* @Entity com.example.crmachievement.domain.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("SELECT role_id FROM crm_user_role WHERE user_id = #{userId}")
    List<String> selectRoleIdsByUserId(@Param("userId") String userId);

}




