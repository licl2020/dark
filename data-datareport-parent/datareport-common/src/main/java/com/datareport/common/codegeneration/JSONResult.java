package com.datareport.common.codegeneration;


import java.io.Serializable;

/**
 * ajax 传参返回值
 */
public class JSONResult<T> implements Serializable {

    // 响应状态 默认false
    private Boolean success = false;
    // 响应消息
    private String message;
    // 存放的数据
    private T data;

    public JSONResult() {
        super();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}