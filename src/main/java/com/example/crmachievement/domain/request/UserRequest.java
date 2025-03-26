package com.example.crmachievement.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户模型")
public class UserRequest {
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "Token")
    private String token;
}
