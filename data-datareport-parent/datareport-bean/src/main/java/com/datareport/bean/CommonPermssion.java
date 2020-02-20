package com.datareport.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.datareport.validation.CommonPermssionAddParmValidator;
import com.datareport.validation.CommonPermssionUpdateParmValidator;

/**
 * <p>
 * 该表用来存储资源权限信息
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public class CommonPermssion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 权限名称
     */
    @TableField(value = "NAME")
    @NotBlank(message="权限名称不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private String name;
    /**
         * 类别   0 系统目录  1 菜单  2 页面  3 按钮  
     */
    @TableField(value = "TYPE")
    @NotNull(message="权限类别不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private Integer type;
    
    /**
     * 权限是否可分配  1 可分配  2 不可分配
     */
    @TableField(value = "ISDISTRIBUTION")
    @NotNull(message="权限是否可分配不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private Integer isdistribution;
    
    /**
     * 上级ID
     */
    @TableField(value = "PARENT_ID")
    @NotNull(message="上级不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private Integer parentId;
    /**
     * 上级PIDs
     */
    @TableField(value = "PARENT_IDS")
    private String parentIds;
    /**
     * 访问路径
     */
    @TableField(value = "VISIT_URL")
    @NotBlank(message="访问路径不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private String visitUrl;
    /**
     * 图标(可以不要)
     */
    @TableField(value = "ICONCLS")
    private String iconcls;
    /**
     * 权限(如user:list)
     */
    @TableField(value = "PERMISSION")
    @NotBlank(message="权限标识不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private String permission;
    /**
     * 排序
     */
    @TableField(value = "ORDER_NUM")
    @NotNull(message="排序不能为null",groups = {CommonPermssionAddParmValidator.class,CommonPermssionUpdateParmValidator.class}) 
    private Integer orderNum;
    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;
    
    /**
     * 1 有下级 2 无下级
     */
    @TableField(value = "M_PARENTC")
    private Integer mParentc;
    
    
    
    /**
     * 是否有下级
     */
    @TableField(exist = false)
	private Boolean isParent;
    
    

    /**
     * 原有父级id
     */
    @TableField(exist = false)
    private Integer yldyo;
    
    @TableField(exist = false) 
    private List<CommonPermssion> itmeList;


    
	public List<CommonPermssion> getItmeList() {
		return itmeList;
	}

	public void setItmeList(List<CommonPermssion> itmeList) {
		this.itmeList = itmeList;
	}

	public Integer getYldyo() {
		return yldyo;
	}

	public void setYldyo(Integer yldyo) {
		this.yldyo = yldyo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

    public Integer getIsdistribution() {
		return isdistribution;
	}

	public void setIsdistribution(Integer isdistribution) {
		this.isdistribution = isdistribution;
	}

	public Integer getmParentc() {
		return mParentc;
	}

	public void setmParentc(Integer mParentc) {
		switch (mParentc) {
		case 1:
			this.isParent=true;
			break;
		case 2:
			this.isParent=false;
			break;
		default:
			break;
		}
		this.mParentc = mParentc;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	@Override
    public String toString() {
        return "CommonPermssion{" +
        ", id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", parentId=" + parentId +
        ", parentIds=" + parentIds +
        ", visitUrl=" + visitUrl +
        ", iconcls=" + iconcls +
        ", permission=" + permission +
        ", orderNum=" + orderNum +
        ", remark=" + remark +
        "}";
    }
}
