package com.datareport.common.enumeration;





import java.util.HashMap;

import com.datareport.common.String.StringUtil;



/**
 * 菜单类别枚举
 * @author 
 *
 */
public enum ModularMtypeEnum {

	/**
	 * 根目录
	 */
	ROOTDIRECTORY(0,"根目录"),
	
	/**
	 * 菜单
	 */
    MENU(1,"菜单"),
	
	/**
	 * 页面
	 */
    PAGE(2,"页面"),
	
	/**
	 * 按钮
	 */
    BUTTON(3,"按钮");
	
	
	private Integer code;

	private String value;
	
	
	/**
	 * 根据code取值
	 * @param code
	 * @return
	 */
	public static String getValue(Integer code) {
		if (StringUtil.isEmpty(code)) {
			return null;
		}

		for (ModularMtypeEnum e : ModularMtypeEnum.values()) {
			if (e.getCode() == code) {
				return e.value;
			}
		}

		return null;
	}
	
	
	private ModularMtypeEnum(Integer code, String value){
		this.code=code;
		this.value=value;
	}
	
	public static HashMap<Integer, String> getMap() {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		for (ModularMtypeEnum e : ModularMtypeEnum.values()) {
			result.put(e.getCode(), e.getValue());
		}

		return result;
	}
	
	public static HashMap<String, String> getMapString() {
		HashMap<String, String> result = new HashMap<String, String>();
		for (ModularMtypeEnum e : ModularMtypeEnum.values()) {
			result.put(String.valueOf(e.getCode()), e.getValue());
		}

		return result;
	}
	
	

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
	
	
	
}
