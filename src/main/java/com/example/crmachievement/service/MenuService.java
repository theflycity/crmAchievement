package com.example.crmachievement.service;

import com.example.crmachievement.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
* @author 001
* @description 针对表【crm_menu(菜单表)】的数据库操作Service
* @createDate 2025-03-29 14:03:30
*/
public interface MenuService extends IService<Menu> {

    List<Menu> getMenusListByPermissionList(Collection<? extends GrantedAuthority> permissionCodeCollection);
}
