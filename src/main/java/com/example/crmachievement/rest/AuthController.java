package com.example.crmachievement.rest;

import com.example.crmachievement.domain.enums.BusinessCode;
import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.request.LoginRequest;
import com.example.crmachievement.domain.result.ApiResponse;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.rest.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@ApiOperation(tags = "登录接口",value = "用户登录", notes = "通过用户名密码获取访问令牌")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation("登录授权")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest) {
        // 参数基础校验
        if (loginRequest.getName() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.ok(BusinessCode.PARAM_IS_NULL);
        }
        // 解析请求参数并转发
        ServiceResult<AuthInfo> serviceResult = authService.login(loginRequest.getName(), loginRequest.getPassword());

        // 封装响应信息
        ApiResponse<AuthInfo> success = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(success);
    }
}
