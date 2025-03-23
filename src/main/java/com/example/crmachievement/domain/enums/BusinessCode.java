package com.example.crmachievement.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessCode {
    // 成功状态
    SUCCESS("操作成功", 200, 2000, "SUCCESS", "error.success"),

    // 客户端错误（业务状态码以2xxx/4xxx区分）
    PARAM_IS_NULL("请求参数为空", 400, 4001, "PARAM_IS_NULL", "error.param.null"),
    BAD_REQUEST("请求参数错误", 400, 4002, "BAD_REQUEST", "error.request.invalid"),
    UNAUTHORIZED("未授权访问", 401, 4003, "UNAUTHORIZED", "error.auth.unauthorized"),
    USER_NOT_FOUND("用户不存在", 404, 4004, "USER_NOT_FOUND", "error.user.notFound"),
    USER_EXISTS("用户已存在", 409, 4005, "USER_EXISTS", "error.user.exists"),
    CUSTOMER_EXISTS("客户已存在", 409, 4006, "CUSTOMER_EXISTS", "error.customer.exists"),
    CUSTOMER_NOT_FOUND("客户不存在", 404, 4008, "CUSTOMER_NOT_FOUND", "error.customer.notFound"),
    PASSWORD_ERROR("密码错误", 401, 4007, "PASSWORD_ERROR", "error.auth.password"),

    // 服务端错误（业务状态码以5xxx区分）,
    INTERNAL_ERROR("系统繁忙，请稍后重试", 500, 5001, "INTERNAL_ERROR", "error.system.internal");

    private final String userMessage;      // 用户友好提示（默认中文，可直接展示）
    private final int httpStatusCode;      // HTTP 状态码
    private final int busStatusCode;       // 业务状态码（自定义数字体系，非HTTP状态码）
    private final String busCode;          // 业务描述码（唯一标识，全大写下划线格式）
    private final String busMsgKey;        // 业务信息键值（用于国际化，如 error.user.notFound）

    // 可添加基于 code 查找枚举的方法（按需）
    public static BusinessCode of(String code) {
        for (BusinessCode businessCode : values()) {
            if (businessCode.getBusCode().equals(code)) {
                return businessCode;
            }
        }
        return INTERNAL_ERROR; // 默认返回系统错误
    }
}