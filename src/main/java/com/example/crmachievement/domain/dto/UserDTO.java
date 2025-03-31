package com.example.crmachievement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String departmentId;
    private List<String> roleIdList;
    private List<String> roleLogicalKeyList;
}
