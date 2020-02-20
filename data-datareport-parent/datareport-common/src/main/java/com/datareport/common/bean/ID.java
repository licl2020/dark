package com.datareport.common.bean;




import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.datareport.common.excel.Excel;
import com.datareport.common.page.Page;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class ID    implements Serializable, Cloneable
{

	
	

	/** 数据id */
	@Excel(skip = true)
	@XStreamOmitField
	private long id;

	/** 分页 */
	@XStreamOmitField
	protected Page				page;
	
	
	/**
	 * 授权令牌
	 */
	@XStreamOmitField
	private String token;
	
	
	
	/**
	 * 操作标识  1 add 2 update 3 del
	 */
	@Excel(skip = true)
	@XStreamOmitField
	private Integer action;
	
	/**
	 * 开始时间
	 */
	@XStreamOmitField
	private String startTime;
	
	/**
	 * 结束时间
	 */
	@XStreamOmitField
	private String endTime;


	
	
	
	
	/**
	 * 开始时间
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	
	/**
	 * 结束时间
	 */
	public String getEndTime() {
		return endTime;
	}

	
	/**
	 * 结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/** 分页 */
	public Page getPage()
	{
		return page;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	/** 分页 */
	@XmlElement 
	public void setPage(Page page)
	{
		this.page = page;
	}
	/**
	 * 授权令牌
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 授权令牌
	 */
	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}




}
