package com.example.crmachievement.domain.result;

import com.example.crmachievement.domain.enums.BusinessCode;
import lombok.Data;
//业务结果
@Data
public class ServiceResult<T> {
    private boolean success;
    private BusinessCode businessCode; // 存储业务码枚举
    private T payload;         // 业务数据（如插入的 Customer 对象）
    
    // 静态方法：成功（带业务数据）
    public static <T> ServiceResult<T> success(BusinessCode businessCode, T payload) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(true);
        result.setBusinessCode(businessCode); // 赋值枚举
        result.setPayload(payload);
        return result;
    }

    // 失败（必传 BusinessCode 枚举）
    public static <T> ServiceResult<T> fail(BusinessCode businessCode) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setBusinessCode(businessCode);
        return result;
    }
}
