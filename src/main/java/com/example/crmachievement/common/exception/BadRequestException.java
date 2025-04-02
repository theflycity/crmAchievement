package com.example.crmachievement.common.exception;

import com.example.crmachievement.domain.enums.BusinessCode;
import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {
    // 业务码
    private final BusinessCode businessCode;
    // 错误信息
    private final String errorMessage;

    public BadRequestException(BusinessCode businessCode) {
        // 确保自定义异常与标准异常的行为一致
        super(businessCode.getUserMessage());
        this.businessCode = businessCode;
        this.errorMessage = businessCode.getUserMessage();
    }
    // 错误信息可以自定义
    public BadRequestException(BusinessCode businessCode, String errorMessage) {
        super(errorMessage);
        this.businessCode = businessCode;
        this.errorMessage = errorMessage;
    }
}
