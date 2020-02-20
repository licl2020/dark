package com.datareport.err;

public enum stateCode {
	
	/**
	 * token过期
	 */
	TOKEN_BE_OVERDUE("token_be_overdue","用户登录信息已失效,请重新登陆"),
	/**
	 * 用户在其他地方登陆
	 */
	LANDING_ELSEWHERE("landing_elsewhere","用户在其他地方登陆"),
	/**
	 * 参数请求错误
	 */
	PARAMETER_ERROR("parameter_error","参数请求错误"),
	/**
	 * 请联系管理员获得访问权限
	 */
	NO_ACCESS("no_access","请联系管理员获得访问权限"),
	/**
	 * 匿名Subject，请先登录
	 */
	PLEASE_LOGIN_FIRST("please_login_first","请先登录"),
	/**
	 * 请重新登陆登录
	 */
	PLEASE_LOGIN_AFAIN("please_login_again","请重新登陆登录"),
	/**
	 * 404异常
	 */
	FOUR_HUNDRED_AND_FOUR("404","404异常"),
	/**
	 * Exception异常
	 */
	Exception("exception","Exception异常"),
	/**
	 *CustomException异常
	 */
	CustomException("customException","CustomException异常");
	
	private stateCode(String name,String msg) {
		this.name=name;
		this.msg=msg;
	}
	
	public static String getValue(String code) {
		for (stateCode e : stateCode.values()) {
			if (e.getName().equals(code)) {
				return e.getMsg();
			}
		}
		return "请重新登陆";
	}

	private String name;
	
	private String msg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
