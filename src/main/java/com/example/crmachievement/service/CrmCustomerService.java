package com.example.crmachievement.service;

import com.example.crmachievement.domain.CrmCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmachievement.domain.result.ServiceResult;

/**
* @author 001
* @description 针对表【crm_customer】的数据库操作Service
* @createDate 2025-03-19 21:08:19
*/
public interface CrmCustomerService extends IService<CrmCustomer> {

    ServiceResult createCustomer(String inputName, String inputCity, String inputPhone, String inputCreatedBy);
}
