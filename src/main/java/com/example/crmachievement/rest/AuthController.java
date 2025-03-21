package com.example.crmachievement.rest;

import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.dto.LoginRequest;
import com.example.crmachievement.domain.dto.ServiceResult;
import com.example.crmachievement.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@ApiOperation(value = "用户登录", notes = "通过用户名密码获取访问令牌")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation("登录授权")
    public ResponseEntity<ServiceResult<AuthInfo>> doLogin(@Valid @RequestBody LoginRequest loginRequest) throws BadRequestException {
        // 参数基础验证
        if (loginRequest.getName() == null || loginRequest.getPassword() == null) {
            throw new BadRequestException("用户名或密码不能为空");
        }

        // 解析请求参数并转发
        ServiceResult<AuthInfo> serviceResult = authService.login(loginRequest.getName(), loginRequest.getPassword());

        // 封装响应信息
        return ResponseEntity.ok(serviceResult);
    }
}
