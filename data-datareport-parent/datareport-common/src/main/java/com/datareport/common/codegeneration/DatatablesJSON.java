package com.datareport.common.codegeneration;


import java.io.Serializable;
import java.util.List;

public class DatatablesJSON<T> implements Serializable {

    //当前页面
    private int draw;
    //总页数
    private long recordsTotal;
    private long recordsFiltered;

    // 响应状态 默认false
    private Boolean success = false;
    // 响应消息
    private String error;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    // 存放的数据
    private List<T> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
