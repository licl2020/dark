package com.datareport.common.enumeration;










import java.util.HashMap;

//报错枚举
public enum zdrkEnum {

	A("10","接口调用成功"),
	B("20","备案数据部分发生异常!"),
	C("-10","参数请求错误"),
	D("-20","数据库连接失败!"),
	E("-30","接口应用内部错误!"),
	F("-31","不是有效的XML文件!"),
	G("-60","XML文件不符合规范格式!"),
	H("-61","备案数据版本号错误!"),
	I("-62","备案数据单位代码错误!"),
	J("-64","备案数据单位名称错误!"),
	K("-70","备案提交的记录数超过限制!"),
	L("-71","信息不符合数据项规范（扩展错误描述）!"),
	M("-72","信息分类代码错误!"),
	N("-73","数据项非法!"),
	O("-74","字典不符合规范!"),
	P("-75","条件参数逻辑表达式错误!"),
	Q("-80","业务逻辑校验错误!"),
	R("-81","布控时间不可以超过1年!"),
	S("-82","非治安重点人不可以进行布控!"),
	T("-83","轨迹线索的订阅人与管控民警不一致!"),
	U("-84","变更的治安重点人不存在!"),
	V("-85","注销的治安重点人不存在!"),
	W("-86","治安重点人重复备案!"),
	X("-87","备案的治安重点人的户籍信息不存在!"),
	Y("-90","系统异常!");
	
	private String code;

	private String remarks;
	
	
	
	public static String getValue(String code) {
		for (zdrkEnum e : zdrkEnum.values()) {
			if (e.getCode().equals(code)) {
				return e.getRemarks();
			}
		}

		return "当前"+code+"错误编码未收录";
	}
	
	
	private zdrkEnum(String code, String remarks){
		this.code=code;
		this.remarks=remarks;
	}
	
	
	public static HashMap<String, String> getMap() {
		HashMap<String, String> result = new HashMap<String, String>();
		for (zdrkEnum e : zdrkEnum.values()) {
			result.put(e.getCode(), e.getRemarks());
		}

		return result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
