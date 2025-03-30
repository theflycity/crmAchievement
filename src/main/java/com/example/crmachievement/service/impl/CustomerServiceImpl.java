package com.example.crmachievement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmachievement.common.generator.IdGenerator;
import com.example.crmachievement.domain.Customer;
import com.example.crmachievement.domain.result.ServiceResult;
import com.example.crmachievement.mapper.CustomerMapper;
import com.example.crmachievement.service.CustomerService;
import com.example.crmachievement.service.strategy.StrategyContext;
import com.example.crmachievement.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.crmachievement.domain.enums.BusinessCode.*;

/**
 * @author 001
 * @description 针对表【crm_customer(客户表)】的数据库操作Service实现
 * @createDate 2025-03-25 23:55:21
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
        implements CustomerService {
    private final IdGenerator idGenerator;
    Customer customer = new Customer();
    private final RedisUtil redisUtil;
    private final StrategyContext strategyContext;

    @Override
    public ServiceResult<?> createCustomer(String inputName, String inputCity, String inputPhone, String inputCreatedBy) {
        // 数据清洗
        String name = inputName.trim();
        String city = inputCity.trim();
        String phone = inputPhone.trim();
        String createdBy = inputCreatedBy.trim();

        // 客户查重，这里查询手机、用户名、城市，全都一样就认为是相同的客户
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getName, name)
                .eq(Customer::getCity, city)
                .eq(Customer::getPhone, phone);
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
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);

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
        Customer existingCustomer = getById(id);
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
        customer.setCreatedAt(createdTime);
        customer.setUpdatedAt(now);
        customer.setUpdatedBy(updatedBy);

        // 保存到数据库并返回结果
        return saveOrUpdateAndReturnResult(customer, id);
    }

    @Override
    public ServiceResult<?> getCustomerList(String userDTO, String role) {
        // TODO 从缓存中查询数据，如果存在直接返回，不存在再查询数据库并缓存

        // TODO 策略模式查询客户列表
        /*// 新建条件查询器
        QueryWrapper<CrmCustomer> queryWrapper = new QueryWrapper<>();
        // 通过策略上下文调用策略
        strategyContext.getPolicy(role).apply(queryWrapper,userDTO);

        // 执行查询并获取结果
        ServiceResult<?> serviceResult = QueryStrategy.query(queryWrapper, userDTO);*/

        // TODO 动态生成缓存键
        // String cacheKey = String.format("customer_list_%s_%s", role, userId);

        // 缓存逻辑
        /*if (serviceResult.isSuccess()) {
            List<CrmCustomer> customerList = (List<CrmCustomer>) serviceResult.getPayload();
            redisUtil.set(cacheKey, customerList, 300);
        }*/

        return null;//serviceResult;
    }

    @Override
    public ServiceResult<?> getCustomer(String id) {
        // 查询客户
        Customer customer = getById(id);

        // 返回结果
        if (customer != null) {
            return ServiceResult.success(SUCCESS, customer);
        } else {
            return ServiceResult.fail(CUSTOMER_NOT_FOUND);
        }
    }

    @Override
    public ServiceResult<?> deleteCustomer(String id) {
        // 查询客户是否存在
        Customer existingCustomer = getById(id);
        if (existingCustomer == null) {
            return ServiceResult.fail(CUSTOMER_NOT_FOUND);
        }

        // 删除客户
        boolean deleteResult = removeById(id);

        // 返回结果
        if (deleteResult) {
            return ServiceResult.success(SUCCESS, "客户删除成功");
        } else {
            return ServiceResult.fail(INTERNAL_ERROR);
        }
    }

    private ServiceResult<?> saveOrUpdateAndReturnResult(Customer customer, String customerId) {
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
            LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Customer::getId, customerId);
            Customer returnedCustomer = getOne(queryWrapper);

            // 返回成功结果，包含查询到的客户信息
            return ServiceResult.success(SUCCESS, returnedCustomer);
        }
        // 返回失败
        return ServiceResult.fail(INTERNAL_ERROR);
    }
}