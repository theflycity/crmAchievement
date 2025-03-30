package com.example.crmachievement.service.strategy;

import com.example.crmachievement.service.strategy.impl.SalesQueryStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StrategyContext {

    private final Map<String, SalesQueryStrategy> strategyMap = new HashMap<>();

    @Autowired
    public StrategyContext(SalesQueryStrategy salesQueryStrategy, SalesQueryStrategy salesManagerQueryStrategy) {
        strategyMap.put("sales", salesQueryStrategy);
        strategyMap.put("sales_manager", salesManagerQueryStrategy);
        // 添加其他角色和策略
    }

    public SalesQueryStrategy getPolicy(String role) {
        SalesQueryStrategy strategy = strategyMap.get(role);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for role: " + role);
        }
        return strategy;
    }
}
