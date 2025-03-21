package com.example.crmachievement.mapper;

import com.example.crmachievement.domain.CrmUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 001
* @description 针对表【crm_user】的数据库操作Mapper
* @createDate 2025-03-19 21:09:04
* @Entity com.example.crmachievement.domain.CrmUser
*/
@Mapper
public interface CrmUserMapper extends BaseMapper<CrmUser> {

}




