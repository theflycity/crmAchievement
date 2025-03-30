package com.example.crmachievement.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单表
 * @TableName crm_menu
 */
@TableName(value ="crm_menu")
@Data
public class Menu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId
    private String id;

    /**
     * 菜单名称（前端展示:如"客户管理"）
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 子菜单数目
     */
    private Integer subCount;

    /**
     * 菜单图标（如"user"）
     */
    private String menuIcon;

    /**
     * 菜单排序值，用于控制显示顺序
     */
    private Integer menuSort;

    /**
     * 组件名称（供前端框架注册）
     */
    private String componentName;

    /**
     * 前端组件路径（如 views/customer/index.vue）
     */
    private String componentPath;

    /**
     * 前端路由路径（如"/customer"）
     */
    private String urlPath;

    /**
     * 菜单类型（如：按钮）
     */
    private Integer menuType;

    /**
     * 关联的权限标识
     */
    private String permCode;

    /**
     * 是否为外链菜单（0否，1是）
     */
    private Integer iFrame;

    /**
     * 是否缓存页面（0否，1是）
     */
    private Integer cache;

    /**
     * 是否隐藏菜单（0否，1是）
     */
    private Integer hidden;

    /**
     * 创建者ID
     */
    private String createdBy;

    /**
     * 更新者ID
     */
    private String updatedBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Menu other = (Menu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getSubCount() == null ? other.getSubCount() == null : this.getSubCount().equals(other.getSubCount()))
            && (this.getMenuIcon() == null ? other.getMenuIcon() == null : this.getMenuIcon().equals(other.getMenuIcon()))
            && (this.getMenuSort() == null ? other.getMenuSort() == null : this.getMenuSort().equals(other.getMenuSort()))
            && (this.getComponentName() == null ? other.getComponentName() == null : this.getComponentName().equals(other.getComponentName()))
            && (this.getComponentPath() == null ? other.getComponentPath() == null : this.getComponentPath().equals(other.getComponentPath()))
            && (this.getUrlPath() == null ? other.getUrlPath() == null : this.getUrlPath().equals(other.getUrlPath()))
            && (this.getMenuType() == null ? other.getMenuType() == null : this.getMenuType().equals(other.getMenuType()))
            && (this.getPermCode() == null ? other.getPermCode() == null : this.getPermCode().equals(other.getPermCode()))
            && (this.getIFrame() == null ? other.getIFrame() == null : this.getIFrame().equals(other.getIFrame()))
            && (this.getCache() == null ? other.getCache() == null : this.getCache().equals(other.getCache()))
            && (this.getHidden() == null ? other.getHidden() == null : this.getHidden().equals(other.getHidden()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getSubCount() == null) ? 0 : getSubCount().hashCode());
        result = prime * result + ((getMenuIcon() == null) ? 0 : getMenuIcon().hashCode());
        result = prime * result + ((getMenuSort() == null) ? 0 : getMenuSort().hashCode());
        result = prime * result + ((getComponentName() == null) ? 0 : getComponentName().hashCode());
        result = prime * result + ((getComponentPath() == null) ? 0 : getComponentPath().hashCode());
        result = prime * result + ((getUrlPath() == null) ? 0 : getUrlPath().hashCode());
        result = prime * result + ((getMenuType() == null) ? 0 : getMenuType().hashCode());
        result = prime * result + ((getPermCode() == null) ? 0 : getPermCode().hashCode());
        result = prime * result + ((getIFrame() == null) ? 0 : getIFrame().hashCode());
        result = prime * result + ((getCache() == null) ? 0 : getCache().hashCode());
        result = prime * result + ((getHidden() == null) ? 0 : getHidden().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", menuName=").append(menuName);
        sb.append(", parentId=").append(parentId);
        sb.append(", subCount=").append(subCount);
        sb.append(", menuIcon=").append(menuIcon);
        sb.append(", menuSort=").append(menuSort);
        sb.append(", componentName=").append(componentName);
        sb.append(", componentPath=").append(componentPath);
        sb.append(", urlPath=").append(urlPath);
        sb.append(", menuType=").append(menuType);
        sb.append(", permCode=").append(permCode);
        sb.append(", iFrame=").append(iFrame);
        sb.append(", cache=").append(cache);
        sb.append(", hidden=").append(hidden);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}