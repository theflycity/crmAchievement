package com.example.crmachievement.common.exception.handler;

import com.example.crmachievement.common.exception.BadRequestException;
import com.example.crmachievement.domain.enums.BusinessCode;
import com.example.crmachievement.domain.result.ApiResponse;
import com.example.crmachievement.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.example.crmachievement.domain.enums.BusinessCode.BAD_REQUEST;
import static com.example.crmachievement.domain.enums.BusinessCode.INTERNAL_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(BadRequestException ex) {
        // 记录异常信息
        log.error(ThrowableUtil.getStackTrace(ex));
        // 构造响应对象，并返回
        ApiResponse<?> response = ApiResponse.fail(ex.getBusinessCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getBusinessCode().getHttpStatusCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ThrowableUtil.getStackTrace(ex));
        ApiResponse<?> response = ApiResponse.fail(BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception ex) {
        log .error(ThrowableUtil.getStackTrace(ex));
        ApiResponse<?> response = ApiResponse.fail(INTERNAL_ERROR, "Internal server error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 其他特定异常处理
}
