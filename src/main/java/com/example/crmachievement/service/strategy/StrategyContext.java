package com.example.crmachievement.service.strategy;

import com.example.crmachievement.service.strategy.impl.SalesQueryStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StrategyContext {

    private final Map<String, QueryStrategy> strategyMap = new HashMap<>();

    @Autowired
    public StrategyContext(@Lazy QueryStrategy salesQueryStrategy,@Lazy QueryStrategy salesManagerQueryStrategy) {
        strategyMap.put("sales", salesQueryStrategy);
        strategyMap.put("sales_manager", salesManagerQueryStrategy);
        // 添加其他角色和策略
    }

    public QueryStrategy getPolicy(String role) {
        QueryStrategy strategy = strategyMap.get(role);
        if (strategy == null) {
            // todo 友好展示
            throw new IllegalArgumentException("No strategy found for role: " + role);
        }
        return strategy;
    }
}
