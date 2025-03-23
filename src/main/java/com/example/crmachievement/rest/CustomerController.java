package com.example.crmachievement.rest;

import com.example.crmachievement.domain.dto.AuthInfo;
import com.example.crmachievement.domain.enums.BusinessCode;
import com.example.crmachievement.domain.dto.CustomerDTO;
import com.example.crmachievement.domain.result.ApiResponse;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.service.CrmCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@Api(tags = "客户管理接口")
public class CustomerController {
    private final CrmCustomerService crmCustomerService;

    @PostMapping
    @ApiOperation("创建客户")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        //基础参数校验
        if (customerDTO.getName() == null || customerDTO.getPhone() == null || customerDTO.getCreatedBy() == null) {
             return ResponseEntity.ok(BusinessCode.PARAM_IS_NULL);
        }

        //传递请求
        ServiceResult<?> serviceResult = crmCustomerService.createCustomer(
                customerDTO.getName(),customerDTO.getCity(),customerDTO.getPhone(),customerDTO.getCreatedBy());

        //返回响应
        ApiResponse<?> success = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(success);
    }
}
