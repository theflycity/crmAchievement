package com.example.crmachievement.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户登录模型")
public class LoginRequest {
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
}
