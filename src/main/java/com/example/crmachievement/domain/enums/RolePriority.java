package com.example.crmachievement.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RolePriority {
    ADMIN(0),
    SALES_MANAGER(1),
    SALES(2),
    USER(3);

    private final int priority;
}
