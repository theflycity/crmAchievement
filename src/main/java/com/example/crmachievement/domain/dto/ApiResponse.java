package com.example.crmachievement.domain.dto;

import com.example.crmachievement.common.enums.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

// 统一的 API 响应格式
@Data
@AllArgsConstructor
@ApiModel("响应模型")
public class ApiResponse<T> {
    @ApiModelProperty(value = "状态码",required = true)
    private int status;      // HTTP 状态码（如 200）
    private String code;     // 业务错误码（如 "USERNAME_DUPLICATE"）
    private String message;  // 用户友好提示（如 "用户名已存在"）
    private T data;          // 业务数据（成功时返回）

    // 静态工厂方法简化构造
    public static <T> ApiResponse<T> success(ErrorCode errorCode, T data) {
        return new ApiResponse<>(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage(),data );
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage(),null);
    }
}
