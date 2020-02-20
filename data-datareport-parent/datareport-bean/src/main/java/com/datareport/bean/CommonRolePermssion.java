package com.datareport.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 * 角色资源权限表中间表
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public class CommonRolePermssion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 角色Id
     */
    @TableField(value = "ROLE_ID")
    private Integer roleId;
    /**
     * 资源（权限）Id
     */
    @TableField(value = "RESOURCE_ID")
    private Integer resourceId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "CommonRolePermssion{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", resourceId=" + resourceId +
        "}";
    }
}
