package com.example.crmachievement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {
    private String token;
    private String userId;
    private String userName;
}
