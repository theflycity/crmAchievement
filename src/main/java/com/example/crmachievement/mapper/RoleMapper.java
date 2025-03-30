package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 001
* @description 针对表【crm_role(角色表)】的数据库操作Mapper
* @createDate 2025-03-29 14:03:15
* @Entity com.example.crmachievement.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {
    // 获取角色逻辑标识
    @Select("SELECT logical_key FROM crm_role WHERE id = #{roleId}")
    String getLogicalKeyByRoleId(@Param("roleId") String roleId);
}




