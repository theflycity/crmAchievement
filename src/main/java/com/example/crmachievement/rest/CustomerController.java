package com.example.crmachievement.rest;

import com.example.crmachievement.domain.dto.ApiResponse;
import com.example.crmachievement.domain.dto.CustomerDTO;
import com.example.crmachievement.service.CrmCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public void createCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        //基础参数校验
        if (customerDTO.getName() == null || customerDTO.getPhone() == null || customerDTO.getCreatedBy() == null) {
             //return ApiResponse.error();
        }
        //传递请求

        //返回响应
    }

}
