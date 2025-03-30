package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.Menu;
import com.example.crmachievement.service.MenuService;
import com.example.crmachievement.mapper.MenuMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
* @author 001
* @description 针对表【crm_menu(菜单表)】的数据库操作Service实现
* @createDate 2025-03-29 14:03:30
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Override
    public List<Menu> getMenusListByPermissionList(Collection<? extends GrantedAuthority> permissionCodeCollection) {
        return null;
    }
}




