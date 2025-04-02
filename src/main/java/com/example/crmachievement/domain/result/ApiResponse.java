package com.example.crmachievement.domain.result;

import com.example.crmachievement.domain.enums.BusinessCode;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

// 统一的 API 响应格式
@Data
@AllArgsConstructor
@ApiModel("响应模型")
public class ApiResponse<T> {
    private String userMessage;      // 用户友好提示（默认中文，可直接展示）
    private int httpStatusCode;      // HTTP 状态码（如 200）
    private int busStatusCode;       // 业务状态码（自定义数字体系，非HTTP状态码）
    private String busCode;          // 业务描述码（唯一标识，全大写下划线格式 如 "USERNAME_DUPLICATE"）
    private String busMsgKey;        // 业务信息键值（用于国际化，如 error.user.notFound）
    private T data;                        // 业务数据（成功时返回）

    // 静态工厂方法简化构造
    public static <T> ApiResponse<T> success(BusinessCode businessCode, T data) {
        return new ApiResponse<>(
            businessCode.getUserMessage(),
            businessCode.getHttpStatusCode(),
            businessCode.getBusStatusCode(),
            businessCode.getBusCode(),
            businessCode.getBusMsgKey(),
            data
        );
    }

    public static ApiResponse<?> fail(BusinessCode businessCode) {
        return new ApiResponse<>(
            businessCode.getUserMessage(),
            businessCode.getHttpStatusCode(),
            businessCode.getBusStatusCode(),
            businessCode.getBusCode(),
            businessCode.getBusMsgKey(),
            null
        );
    }

    public static ApiResponse<?> fail(BusinessCode businessCode,String userMessage) {
        return new ApiResponse<>(
                userMessage,
                businessCode.getHttpStatusCode(),
                businessCode.getBusStatusCode(),
                businessCode.getBusCode(),
                businessCode.getBusMsgKey(),
                null
        );
    }
}