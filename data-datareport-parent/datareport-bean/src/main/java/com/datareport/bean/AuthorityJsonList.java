package com.datareport.bean;













import java.util.List;








/**
 * ClassName:TreeBO Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 *
 * @author Administrator
 * @version
 * @since Ver 1.1
 * @Date 2017年7月19日 下午2:22:17
 *
 */
public class AuthorityJsonList
{
	private String	status;

	private String	message;

	private List<CommonPermssion>	data;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public List<CommonPermssion> getData() {
		return data;
	}

	public void setData(List<CommonPermssion> data) {
		this.data = data;
	}




}
