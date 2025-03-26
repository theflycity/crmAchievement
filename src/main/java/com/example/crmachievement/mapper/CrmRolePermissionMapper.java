package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.CrmRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
* @author 001
* @description 针对表【crm_role_permission(角色-权限关联表)】的数据库操作Mapper
* @createDate 2025-03-26 04:49:13
* @Entity com.example.crmachievement.domain.CrmRolePermission
*/
public interface CrmRolePermissionMapper extends BaseMapper<CrmRolePermission> {
    @Select("<script>" +
            "SELECT permission_perm_code FROM crm_role_permission WHERE role_id IN " +
            "<foreach item='roleId' index='index' collection='roleIds' open='(' separator=',' close=')'>" +
            "#{roleId}" +
            "</foreach>" +
            "</script>")
    Set<String> selectPermissionCodesByRoleIds(@Param("roleIds") List<String> roleIds);
}




