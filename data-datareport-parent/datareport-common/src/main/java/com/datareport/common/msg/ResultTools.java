package com.datareport.common.msg;

import com.datareport.common.json.JsonUtils;

/**
 * 接口返回信息统一处理类
 * @author LICL
 *
 */
public class ResultTools {
	
	// 返回码
	private int code;
	
	// 返回消息
	private String message;
	

	//返回code描述
	private String describe;
	
	//返回数据
    private Object data; 
    
    public ResultTools(){}
    
    
    /**
     * 
     * @param code 返回码
     * @param message 返回消息
     * @param describe 返回code描述
     * @return
     */
    public ResultTools(int code,String message,String describe){
    	this.code=code;
    	this.message=message;
    	this.describe=describe;
    }
    
    /**
     * 
     * @param code 返回码
     * @param message 返回消息
     * @param describe 返回code描述
     * @param data 返回数据
     * @return
     */
    public ResultTools(int code,String message,String describe,Object data){
    	this.code=code;
    	this.message=message;
    	this.describe=describe;
    	this.data=data;
    }
	
    /**
     * 
     * @param code 返回码
     * @param message 返回消息
     * @param describe 返回code描述
     * @param data 返回数据
     * @return
     */
    public static ResultTools getInstance(Integer code, String message,String describe,Object data) {
        return new ResultTools(code, message, describe,data);
    }
    
    /**
     * 
     * @param code 返回码
     * @param message 返回消息
     * @param describe 返回code描述
     * @return
     */
    public static ResultTools getInstance(Integer code, String message,String describe) {
        return new ResultTools(code, message, describe);
    }
    
    
    
    /**
     * 
     * @param code 返回码
     * @param message 返回消息
     * @param describe 返回code描述
     * @return
     */
    public static String getInstanceJson(Integer code, String message,String describe) {
        return JsonUtils.objToJson(new ResultTools(code, message, describe));
    }


    /**
     * 
     * @param code 枚举类型
     * @return
     */
    public static ResultTools getInstance(Light r) {
        return new ResultTools(r.getCode(), r.getDescribe(), r.getValue());
    }
    
    /**
     * 
     * @param code 枚举类型
     * @return
     */
    public static String getInstanceJson(Light r) {
        return JsonUtils.objToJson(new ResultTools(r.getCode(), r.getValue(), r.getDescribe()));
    }
    
    /**
     * 
     * @param code 枚举类型
     * @param data 数据
     * @return
     */
    public static ResultTools getInstance(Light r,Object data) {
        return new ResultTools(r.getCode(), r.getValue(), r.getDescribe(),data);
    }
	  // 1. 定义枚举类型
	  public enum Light {
			
		   /**
			 * 200   
			 * 操作成功
			 */
			SUCCESS(200,"success","操作成功"),
			
			/**
			 * 201   
			 * 操作失败
			 */
			ERROR_201(201,"error","操作失败"),
			
			/**
			 * 202   
			 * 密码不能为null
			 */
			ERROR_202(202,"error","密码不能为null"),
			
			/**
			 * 203  
			 * 接口异常
			 */
			ERROR_203(203,"error","接口异常"),
			
			/**
			 * 204  
			 * 请求接口方法不存在
			 */
			ERROR_204(204,"error","请求接口方法不存在");
	  
	    /**
		 * 返回code
		 */
		private Integer code;
		
		/**
		 * 返回code值
		 */
		private String value;

		/**
		 * 返回code描述
		 */
		private String describe;
	  
	    // 构造函数，枚举类型只能为私有
	    private Light(Integer code,String value,String describe) {
	    	this.code=code;
			this.value=value;
			this.describe=describe;
	    }
	    
	    
		/**
		 * 获取返回code值
		 */
		public String getValue() {
			return value;
		}



		/**
		 * 获取返回code值
		 */
		public void setValue(String value) {
			this.value = value;
		}




		/**
		 * 获取返回code
		 */
		public Integer getCode() {
			return code;
		}

		/**
		 * 设置返回code
		 */
		public void setCode(Integer code) {
			this.code = code;
		}

		/**
		 * 获取返回code描述
		 */
		public String getDescribe() {
			return describe;
		}

		/**
		 * 设置返回code描述
		 */
		public void setDescribe(String describe) {
			this.describe = describe;
		}
	  
	  }



	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 获取返回数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 获取返回数据
	 */
	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * 返回code描述
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * 返回code描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	  
    
	  
}
