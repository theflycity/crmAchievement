package com.example.crmachievement.rest;

import com.example.crmachievement.domain.enums.BusinessCode;
import com.example.crmachievement.domain.dto.CustomerDTO;
import com.example.crmachievement.domain.result.ApiResponse;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.security.UserSecurityInfo;
import com.example.crmachievement.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@Api(tags = "客户管理接口")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ApiOperation("创建客户")
    @PreAuthorize("hasAuthority('system:customer:add')")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        // 基础参数校验
        if (customerDTO.getName() == null || customerDTO.getPhone() == null || customerDTO.getCreatedBy() == null) {
            return ResponseEntity.ok(BusinessCode.PARAM_IS_NULL);
        }

        // 传递请求
        ServiceResult<?> serviceResult = customerService.createCustomer(
                customerDTO.getName(), customerDTO.getCity(), customerDTO.getPhone(), customerDTO.getCreatedBy());

        // 返回响应
        ApiResponse<?> response = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ApiOperation("全量更新客户")
    @PreAuthorize("hasAuthority('crm:customer:edit')")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDTO customerDTO) {
        // 基础参数校验
        if (customerDTO.getName() == null || customerDTO.getCity() == null || customerDTO.getPhone() == null || customerDTO.getCreatedBy() == null || customerDTO.getUpdatedBy() == null || customerDTO.getCreatedTime() == null) {
            return ResponseEntity.ok(BusinessCode.PARAM_IS_NULL);
        }

        // 传递请求
        ServiceResult<?> serviceResult = customerService.updateCustomer(
                id, customerDTO.getName(), customerDTO.getCity(), customerDTO.getPhone(), customerDTO.getCreatedBy(), customerDTO.getUpdatedBy(), customerDTO.getCreatedTime());

        // 返回响应
        ApiResponse<?> response = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ApiOperation("查询客户列表")
    @PreAuthorize("hasAuthority('crm:customer:view')")
    public ResponseEntity<?> getCustomerList(@RequestHeader("Authorization") String authHeader) {
        // 从安全上下文中获取 UerDTO 和 角色列表
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 从 Authentication 对象中获取 UserSecurityInfo
        UserSecurityInfo userSecurityInfo = (UserSecurityInfo) authentication.getPrincipal();

        // 传递请求
        ServiceResult<?> serviceResult = customerService.getCustomerList(userSecurityInfo);

        // 返回响应
        ApiResponse<?> response = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("查询单个客户")
    @PreAuthorize("hasAuthority('crm:customer:view')")
    public ResponseEntity<?> getCustomer(@PathVariable("id") String id) {
        // 传递请求
        ServiceResult<?> serviceResult = customerService.getCustomer(id);

        // 返回响应
        ApiResponse<?> response = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除客户")
    @PreAuthorize("hasAuthority('crm:customer:delete')")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
        // 传递请求
        ServiceResult<?> serviceResult = customerService.deleteCustomer(id);

        // 返回响应
        ApiResponse<?> response = ApiResponse.success(serviceResult.getBusinessCode(), serviceResult.getPayload());
        return ResponseEntity.ok(response);
    }
}