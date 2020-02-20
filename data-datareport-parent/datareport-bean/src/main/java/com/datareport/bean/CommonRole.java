package com.datareport.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.datareport.validation.RoleAddParmValidator;
import com.datareport.validation.RoleUpdateParmValidator;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public class CommonRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签Id
     */
    @TableField(value = "LABEL_ID")
    private String labelId;
    /**
     * 角色名称
     */
    @TableField(value = "NAME")
    @NotBlank(message="角色名称不能为null",groups = {RoleAddParmValidator.class,RoleUpdateParmValidator.class}) 
    private String name;
    @TableField(value = "ROLE")
    private String role;
    @TableField(value = "DESCRIPTION")
    private String description;
    /**
     * 判断该角色是否在使用（1：使用，2：禁用）
     */
    @TableField(value = "IS_SHOW")
    private Integer isShow;
    /**
     * 判断是什么角色（1：超级管理员，2：用户角色）
     */
    @TableField(value = "IS_HANDLER")
    private Integer isHandler;
    @TableField(exist = false)
    private List<CommonPermssion> permssion;


    public List<CommonPermssion> getPermssion() {
		return permssion;
	}

	public void setPermssion(List<CommonPermssion> permssion) {
		this.permssion = permssion;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsHandler() {
        return isHandler;
    }

    public void setIsHandler(Integer isHandler) {
        this.isHandler = isHandler;
    }

    @Override
    public String toString() {
        return "CommonRole{" +
        ", id=" + id +
        ", labelId=" + labelId +
        ", name=" + name +
        ", role=" + role +
        ", description=" + description +
        ", isShow=" + isShow +
        ", isHandler=" + isHandler +
        "}";
    }
}
