package com.example.crmachievement.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 成功状态（HTTP 200）
    SUCCESS(200, "SUCCESS", "操作成功"),

    // 客户端错误（HTTP 4xx）
    BAD_REQUEST(400, "BAD_REQUEST", "请求参数错误"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "未授权访问"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "用户不存在"),
    USER_EXISTS(409, "USER_EXISTS", "用户已存在"),
    PASSWORD_ERROR(422, "PASSWORD_ERROR", "密码错误"),

    // 服务端错误（HTTP 5xx）
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "系统繁忙，请稍后重试");

    private final int httpStatus;  // HTTP 状态码（遵循标准）
    private final String code;     // 业务错误码（唯一标识，如 "USER_NOT_FOUND"）
    private final String message;  // 用户友好提示（支持国际化前缀，如 "error.user.notFound"）

    // 可添加基于 code 查找枚举的方法（按需）
    public static ErrorCode of(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return INTERNAL_ERROR; // 默认返回系统错误
    }
}
