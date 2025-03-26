package com.example.crmachievement.rest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.domain.CrmUser;
import com.example.crmachievement.mapper.CrmUserMapper;
import com.example.crmachievement.rest.service.CrmUserService;
import org.springframework.stereotype.Service;

@Service
public class CrmUserServiceImpl extends ServiceImpl<CrmUserMapper, CrmUser> implements CrmUserService {
}
