package com.example.crmachievement.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel("客户模型")
public class CustomerDTO {
    @ApiModelProperty(value = "客户姓名",required = true)
    private String name;
    @ApiModelProperty(value = "客户所在地",example = "上海")
    private String city;
    @ApiModelProperty(value = "客户联系电话",required = true)
    private String phone;
    @ApiModelProperty(value = "创建人",required = true)
    private String createdBy;
    
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private Date createdTime;
    
    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    private Date updateTime;
}