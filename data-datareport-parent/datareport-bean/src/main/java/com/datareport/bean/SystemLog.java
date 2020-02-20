package com.datareport.bean;

import org.apache.ibatis.type.JdbcType;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.datareport.common.bolbString.MyBlobTypeHandler;

/**
 * @author Unclue_liu
 * @organization tyzn
 * @date 2019/8/30 0030 16:48
 * @desc TODO
 */
public class SystemLog {
	
	@TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     */
	 @TableField(value = "create_time")
    private String createTime;
    /**
     * 日志等级
     */
	 @TableField(value = "log_level")
    private Integer logLevel;
    /**
     * 被操作的对象
     */
	 @TableField(value = "operation_unit")
    private String operationUnit;
    /**
     * 方法名
     */
	 @TableField(value = "method")
    private String method;
    /**
     * 参数
     */
	 @TableField(value = "args")
    private byte[] args;
	 @TableField(exist = false)
	 private String args_String;
    /**
     * 操作人id
     */
	 @TableField(value = "user_id")
    private Integer userId;
    /**
     * 操作人
     */
	 @TableField(value = "user_name")
    private String userName;
    /**
     * 日志描述
     */
	 @TableField(value = "log_describe")
    private String logDescribe;
    /**
     * 操作类型
     */
    @TableField(value = "operation_type")
    private String operationType;
    /**
     * 方法运行时间
     */
    @TableField(value = "run_time")
    private Long runTime;
    /**
     * 方法返回值
     */
    @TableField(value = "return_value")
    private byte[] returnValue;
    @TableField(exist = false)
    private String returnValue_String;
    /**
     * IP地址
     */
    @TableField(value = "ip_address")
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public byte[] getArgs() {
		return args;
	}

	public void setArgs(byte[] args) {
		this.args = args;
	}

	public byte[] getReturnValue() {
		return returnValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArgs_String() {
		if(null!=args){
			args_String=new String(args);
        }
		return args_String;
	}

	public void setArgs_String(String args_String) {
		this.args_String = args_String;
	}

	public String getReturnValue_String() {
		if(null!=returnValue){
			returnValue_String=new String(returnValue);
        }
		return returnValue_String;
	}

	public void setReturnValue_String(String returnValue_String) {
		this.returnValue_String = returnValue_String;
	}

	public void setReturnValue(byte[] returnValue) {
		this.returnValue = returnValue;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

    public String getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(String operationUnit) {
        this.operationUnit = operationUnit;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getLogDescribe() {
		return logDescribe;
	}

	public void setLogDescribe(String logDescribe) {
		this.logDescribe = logDescribe;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }


    @Override
    public String toString() {
        return "SystemLog{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", logLevel=" + logLevel +
                ", operationUnit='" + operationUnit + '\'' +
                ", method='" + method + '\'' +
                ", args='" + args + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", logDescribe='" + logDescribe + '\'' +
                ", operationType='" + operationType + '\'' +
                ", runTime=" + runTime +
                ", returnValue='" + returnValue + '\'' +
                '}';
    }
}
