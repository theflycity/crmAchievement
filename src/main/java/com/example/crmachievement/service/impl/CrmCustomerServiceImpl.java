package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.common.generator.IdGenerator;
import com.example.crmachievement.domain.CrmCustomer;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.service.CrmCustomerService;
import com.example.crmachievement.mapper.CrmCustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.crmachievement.domain.enums.BusinessCode.*;

/**
* @author 001
* @description 针对表【crm_customer】的数据库操作Service实现
* @createDate 2025-03-19 21:08:19
*/
@Service
@RequiredArgsConstructor
public class CrmCustomerServiceImpl extends ServiceImpl<CrmCustomerMapper, CrmCustomer>
    implements CrmCustomerService{

    private final IdGenerator idGenerator;
    CrmCustomer customer = new CrmCustomer();

    @Override
    public ServiceResult<?> createCustomer(String inputName, String inputCity, String inputPhone, String inputCreatedBy) {
        // 数据清洗
        String name = inputName.trim();
        String city = inputCity.trim();
        String phone = inputPhone.trim();
        String createdBy = inputCreatedBy.trim();

        // 客户查重，这里查询手机、用户名、城市，全都一样就认为是相同的客户
        LambdaQueryWrapper<CrmCustomer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CrmCustomer::getName, name)
                .eq(CrmCustomer::getCity, city)
                .eq(CrmCustomer::getPhone, phone);
        long count = count(queryWrapper);
        if (count > 0) {
            return ServiceResult.fail(CUSTOMER_EXISTS);
        }

        // 生成时间
        Date now = new Date();

        // 生成ID
        String customerId = idGenerator.nextUUID(now);

        // 数据转换成Customer
        customer.setId(customerId);
        customer.setName(name);
        customer.setCity(city);
        customer.setPhone(phone);
        customer.setCreatedBy(createdBy);
        customer.setCreatedTime(now);
        customer.setUpdateTime(now);

        // 保存到数据库并返回结果
        return saveOrUpdateAndReturnResult(customer, customerId);
    }

    @Override
    public ServiceResult<?> updateCustomer(String id, String inputName, String inputCity, String inputPhone, String createdBy, String inputUpdatedBy, Date createdTime) {
        // 数据清洗
        String name = inputName.trim();
        String city = inputCity.trim();
        String phone = inputPhone.trim();
        String updatedBy = inputUpdatedBy.trim();

        // 查询现有客户
        CrmCustomer existingCustomer = getById(id);
        if (existingCustomer == null) {
            return ServiceResult.fail(CUSTOMER_NOT_FOUND);
        }

        // 生成时间
        Date now = new Date();

        // 数据转换成Customer
        customer.setId(id);
        customer.setName(name);
        customer.setCity(city);
        customer.setPhone(phone);
        customer.setCreatedBy(createdBy);
        customer.setCreatedTime(createdTime);
        customer.setUpdateTime(now);
        customer.setUpdatedBy(updatedBy);

        // 保存到数据库并返回结果
        return saveOrUpdateAndReturnResult(customer, id);
    }

    private ServiceResult<?> saveOrUpdateAndReturnResult(CrmCustomer customer, String customerId) {
        // 保存到数据库并重试3次
        boolean saveResult;
        int retryCount = 0;
        do {
            saveResult = saveOrUpdate(customer);
            retryCount++;
        } while (!saveResult && retryCount < 3);

        // 返回结果
        if (saveResult) {
            // 根据id查询用户
            LambdaQueryWrapper<CrmCustomer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CrmCustomer::getId, customerId);
            CrmCustomer returnedCustomer = getOne(queryWrapper);

            // 返回成功结果，包含查询到的客户信息
            return ServiceResult.success(SUCCESS, returnedCustomer);
        }
        // 返回失败
        return ServiceResult.fail(INTERNAL_ERROR);
    }
}