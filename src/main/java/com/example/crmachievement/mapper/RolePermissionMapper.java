package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 001
* @description 针对表【crm_role_permission(角色-权限关联表)】的数据库操作Mapper
* @createDate 2025-03-26 04:49:13
* @Entity com.example.crmachievement.domain.RolePermission
*/
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    // 获取权限标识符集合
    @Select("SELECT permission_perm_code FROM crm_role_permission WHERE role_id = #{roleId}")
    List<String> selectPermissionCodesByRoleId(@Param("roleId") String roleId);
}




