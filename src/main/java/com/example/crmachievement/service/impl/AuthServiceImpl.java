package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.config.JwtConfig;
import com.example.crmachievement.domain.CrmUser;
import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.dto.ServiceResult;
import com.example.crmachievement.mapper.CrmUserMapper;
import com.example.crmachievement.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.crmachievement.common.enums.ErrorCode.*;

@Service
public class AuthServiceImpl extends ServiceImpl<CrmUserMapper, CrmUser> implements AuthService {
    @Override
    public ServiceResult<AuthInfo> login(String username, String inputPassword) {
        try {
        // 1. 构建 Lambda 查询条件
        LambdaQueryWrapper<CrmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CrmUser::getName, username) // 匹配姓名
                .select(CrmUser::getId,
                        CrmUser::getName,
                        CrmUser::getPassword); // 仅查询密码字段

        // 2. 执行查询（避免直接调用 Mapper）
        CrmUser crmUser = this.getOne(queryWrapper);

        // 3. 用户名验证
        if (crmUser == null) {
            return ServiceResult.fail(USER_NOT_FOUND);
        }

        // 4. 密码验证
        //todo 密码加密
        if (!inputPassword.equals(crmUser.getPassword())) {
            return ServiceResult.fail(PASSWORD_ERROR);
        }

        // 5. 生成JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", crmUser.getId());
        claims.put("name", crmUser.getName());
        String token = JwtConfig.createToken(crmUser.getId().toString(), claims);
            AuthInfo authInfo = new AuthInfo(token, crmUser.getId(), crmUser.getName());
            // 6. 返回认证信息
        return ServiceResult.success(SUCCESS,new AuthInfo(token, crmUser.getId(), crmUser.getName()));
        } catch (Exception e) {
            // 7. 异常处理（如数据库断开）
            return ServiceResult.fail(INTERNAL_ERROR);
        }
    }
}
