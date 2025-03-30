package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 001
* @description 针对表【crm_menu(菜单表)】的数据库操作Mapper
* @createDate 2025-03-29 14:03:30
* @Entity com.example.crmachievement.domain.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {
    // 根据用户ID获取菜单名称
    @Select("SELECT menu_name FROM crm_menu WHERE perm_code IN (SELECT permission_perm_code FROM crm_role_permission WHERE role_id IN (SELECT role_id FROM crm_user_role WHERE user_id = #{userId}))")
    List<String> getMenuNamesByUserId(@Param("userId") String userId);
}




