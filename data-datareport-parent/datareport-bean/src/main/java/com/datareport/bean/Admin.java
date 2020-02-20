package com.datareport.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.datareport.validation.AdminAddParmValidator;
import com.datareport.validation.AdminUpdateParmValidator;

/**
 * <p>
 * 
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;
    /**
     * 1 男 2 女
     */
    @TableField(value = "sex")
    private Integer sex;
    /**
     * 身份证
     */
    @TableField(value = "card")
    private String card;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Long updateTime;
    /**
     * 邮箱
     */
//    @NotBlank(message="邮箱不能为空",groups = {AdminAddParmValidator.class,AdminUpdateParmValidator.class}) 
//    @Pattern(regexp = "^(.+)@(.+)$",message = "邮箱的格式不合法",groups = {AdminAddParmValidator.class})
    @TableField(value = "mail")
    private String mail;
    /**
     * 手机号
     */
//    @NotBlank(message="手机号",groups = {AdminAddParmValidator.class,AdminUpdateParmValidator.class}) 
//    @Pattern(regexp = "^(.+)@(.+)$",message = "手机号的格式不合法",groups = {AdminAddParmValidator.class})
    @TableField(value = "tel")
    private String tel;
    /**
     * 机构id
     */
    @TableField(value = "o_id")
    private String oId;
    /**
     * 角色id
     */
    @TableField(value = "r_id")
    @NotNull(message="角色不能为null",groups = {AdminAddParmValidator.class,AdminUpdateParmValidator.class}) 
    private Integer rId;
    /**
     * 是否为超级管理员 1 是 2 否
     */
    @TableField(value = "a_max")
    private Integer aMax;
    /**
     * 用户状态 1 启用 2 禁用
     */
    @TableField(value = "state")
    private Integer state;
    /**
     * 昵称
     */
    @TableField(value = "c_name")
    private String cName;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 用户名
     */
    @TableField(value = "u_name")
    @NotBlank(message="用户名",groups = {AdminAddParmValidator.class,AdminUpdateParmValidator.class}) 
    private String uName;
    
    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private List<CommonRole> roles;
    

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<CommonRole> getRoles() {
		return roles;
	}

	public void setRoles(List<CommonRole> roles) {
		this.roles = roles;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Integer getaMax() {
        return aMax;
    }

    public void setaMax(Integer aMax) {
        this.aMax = aMax;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    @Override
    public String toString() {
        return "Admin{" +
        ", id=" + id +
        ", name=" + name +
        ", sex=" + sex +
        ", card=" + card +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", mail=" + mail +
        ", tel=" + tel +
        ", oId=" + oId +
        ", rId=" + rId +
        ", aMax=" + aMax +
        ", state=" + state +
        ", cName=" + cName +
        ", password=" + password +
        ", uName=" + uName +
        "}";
    }
}
