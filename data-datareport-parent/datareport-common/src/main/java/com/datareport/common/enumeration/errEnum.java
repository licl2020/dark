package com.datareport.common.enumeration;










import java.util.HashMap;

//报错枚举
public enum errEnum {

	ONE(1,"请联系管理员获得访问权限"),
	HTML(2,"非法请求!"),
	BUTTON(3,"参数请求错误");
	
	private Integer code;

	private String remarks;
	
	
	
	public static String getValue(Integer code) {
		for (errEnum e : errEnum.values()) {
			if (e.getCode() == code) {
				return e.getRemarks();
			}
		}

		return "请重新登录，即将跳转到登录页面";
	}
	
	
	private errEnum(Integer code, String remarks){
		this.code=code;
		this.remarks=remarks;
	}
	
	
	public static HashMap<Integer, String> getMap() {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		for (errEnum e : errEnum.values()) {
			result.put(e.getCode(), e.getRemarks());
		}

		return result;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
