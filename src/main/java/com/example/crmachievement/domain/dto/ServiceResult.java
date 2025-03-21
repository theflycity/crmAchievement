package com.example.crmachievement.domain.dto;

import com.example.crmachievement.common.enums.ErrorCode;
import lombok.Data;

// Service 层业务结果
@Data
public class ServiceResult<T> {
    private boolean success;
    private String errorCode;  // 从 ErrorCode 枚举获取 code
    private String errorMsg;   // 从 ErrorCode 枚举获取 message
    private T payload;         // 业务数据（如插入的 Customer 对象）
    // 成功静态方法（带业务数据）
    public static <T> ServiceResult<T> success(ErrorCode errorCode,T payload) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(true);
        result.setPayload(payload);
        return result;
    }

    // 失败（必传 ErrorCode 枚举）
    public static <T> ServiceResult<T> fail(ErrorCode errorCode) {
        return fail(errorCode, errorCode.getMessage());
    }

    // 失败（允许覆盖默认 message，适用于动态错误信息）
    public static <T> ServiceResult<T> fail(ErrorCode errorCode, String customMessage) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode.getCode());
        result.setErrorMsg(customMessage); // 可选：直接使用 errorCode.getMessage()
        return result;
    }

}
