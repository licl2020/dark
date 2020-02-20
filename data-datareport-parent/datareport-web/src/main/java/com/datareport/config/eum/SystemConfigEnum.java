package com.datareport.config.eum;


import java.util.HashMap;

import com.datareport.common.String.StringUtil;


/**
 * 后缀配置枚举
 * @author LICL
 *
 */
public enum SystemConfigEnum {
	
	/**
	 * 验证
	 */
	VELOCITY(1,"_1_map"),
	
	/**
	 * id
	 */
	IDMAP(2,"_2_map"),
	

	
	/**
	 * auth list
	 */
	AUTH_LIST(3,"_3_list"),
	

	
	/**
	 * #角色权限map
	 */
	ROLE_AUTH_MAP(4,"login_role_auth_map"),
	

	
	/**
	 * 所有系统权限
	 */
	SYSTEM_AUTH_MAP(5,"system_auth_map"),
	

	
	/**
	 * 功能模块id集合
	 */
	MODULAR_ID_MAP(6,"login_modular_id_map"),
	

	
	/**
	 * 权限map集合
	 */
	ROLE_AUTH_ID(7,"login_auth_map"),
	

	
	/**
	 * 区域idmap缓存
	 */
	AREA_ID_MAP(8,"area_id_map"),
	
	
	/**
	 * 区域表
	 */
    MONGDB_TABLE(9,"login_area_table"),
	
	
	/**
	 * 登入点map
	 */
    LANDINGPOINT_MAP(10,"landingPoint_map"),
	
	
	/**
	 * 角色idmap
	 */
    ROLE_ID_MAP(11,"login_role_id_map"),
	
	
	/**
	 * 超级管理员id
	 */
    ROELE_ID(12,"13582"),
	
	
	/**
	 * #缓存用户数据前缀
	 */
    USER_ID(13,"login_user_"),
	
	
	/**
	 * 缓存用户登录令牌
	 */
    USER_TO_KEN(14,"login_user_to_ken"),
	
	
	/**
	 * 区域信息缓存
	 */
    T_ORGAN_MAP(15,"t_organ_map"),
    
    /**
          * 系统标识
     */
    SYSTEM_FLAG(16,"system");
	
	
	
	
	private Integer code;

	private String value;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	/**
	 * 根据code取值
	 * @param code
	 * @return
	 */
	public static String getValue(Integer code) {
		if (StringUtil.isEmpty(code)) {
			return null;
		}

		for (SystemConfigEnum e : SystemConfigEnum.values()) {
			if (e.getCode() == code) {
				return e.value;
			}
		}

		return null;
	}
	
	
	private SystemConfigEnum(Integer code, String value){
		this.code=code;
		this.value=value;
	}
	
	public static HashMap<Integer, String> getMap() {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		for (SystemConfigEnum e : SystemConfigEnum.values()) {
			result.put(e.getCode(), e.getValue());
		}

		return result;
	}
}
