package com.datareport.bean;









/**
 * 权限
 * @author Administrator
 *
 */
public class ModularDto {

	
	/**
	 * 默认空构造
	 */
	public ModularDto(){}
	

	/**
	 * 是否用于权限
	 */
    private  Integer id;
	
	/**
	 * 是否用于权限
	 */
    private  Integer pId;
    
    
	/**
	 * 父级id
	 */
    private  Integer parentId;
    
    /**
     * 权限名称
     */
    private  String name;
    
    /**
     * 权限id
     */
    private  Integer auth_id;
    
    /**
     * 校验名字
     */
    private String velocity_name;
    
    
    /**
     * 权限是否可分配  1 可分配  2 不可分配
     */
    private Integer isdistribution;
    
    /**
     * 类别  0 根目录  1 菜单  2 页面  3 按钮
     */
    private Integer mType;


	/**
	 * 角色id
	 */
	private Integer roleid;
	
    /**
     * 权限类别  1 采集点  2 业主  3  从业  4 公安
     */
    private Integer type;

    /**
     * 校验名字
     */
    public String getVelocity_name() {
		return velocity_name;
	}
    /**
     * 校验名字
     */
	public void setVelocity_name(String velocity_name) {
		this.velocity_name = velocity_name;
	}

	public Integer getpId() {
		return pId;
	}
	/**
     * 权限是否可分配  1 可分配  2 不可分配
     */
	public Integer getIsdistribution() {
		return isdistribution;
	}
	
	private  boolean checked;
	

			

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAuth_id() {
		return auth_id;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	/**
     * 类别  0 根目录  1 菜单  2 页面  3 按钮
     */
	public Integer getmType() {
		return mType;
	}
    /**
     * 类别  0 根目录  1 菜单  2 页面  3 按钮
     */
	public void setmType(Integer mType) {
		this.mType = mType;
	}

	/**
     * 权限是否可分配  1 可分配  2 不可分配
     */
	public void setIsdistribution(Integer isdistribution) {
		this.isdistribution = isdistribution;
	}
    
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setAuth_id(Integer auth_id) {
		if(auth_id != null){
			this.checked=true;
			
		}else {
			this.checked=false;
		}
		this.auth_id = auth_id;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}


	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
    
    
}
