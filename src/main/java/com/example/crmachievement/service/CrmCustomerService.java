package com.example.crmachievement.service;

import com.example.crmachievement.domain.CrmCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmachievement.domain.result.ServiceResult;

import java.util.Date;

/**
* @author 001
* @description 针对表【crm_customer(客户表)】的数据库操作Service
* @createDate 2025-03-25 23:55:21
*/
public interface CrmCustomerService extends IService<CrmCustomer> {
    ServiceResult<?> createCustomer(String inputName, String inputCity, String inputPhone, String inputCreatedBy);

    ServiceResult<?> updateCustomer(String id, String inputName, String inputCity, String inputPhone, String inputCreatedBy, String createdBy, Date createdTime);

    ServiceResult<?> getCustomerList();

    ServiceResult<?> getCustomer(String id);

    ServiceResult<?> deleteCustomer(String id);
}
