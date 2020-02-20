package com.datareport.quartz.bean;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.datareport.quartz.validation.JobAddParmValidator;
import com.datareport.quartz.validation.JobUpdateParmValidator;

/**
 * <p>
 * 
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public class JobDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 任务名称
     */
    @NotBlank(message="任务名称不能为空",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class}) 
    @Pattern(regexp = "^[0-9a-zA-Z_]{1,}$",message = "任务名称只能是数字、字母、下划线组成",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class})
    private String jobName;  
    
    /**
     * 分组名称
     */
    @NotBlank(message="分组名称不能为空",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class}) 
    @Pattern(regexp = "^[0-9a-zA-Z_]{1,}$",message = "分组名称只能是数字、字母、下划线组成",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class})
    private String groupName;
    
    
    /**
     * 执行类名
     */
    @NotBlank(message="执行类名不能为空",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class}) 
    @Pattern(regexp = "^[0-9a-zA-Z.]{1,}$",message = "分组名称只能是数字、字母、小数点组成",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class})
    private String className;
    
    /**
     * 1 启动 2 停用
     */
    private Integer state;
    
    private String  stateCode;
    
    public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		switch (stateCode) {
			case "NONE":  //不存在
				break;
			case "NORMAL": //正常
				this.state=1;
				break;
			case "PAUSED"://暂停
				this.state=2;
				break;
			case "COMPLETE": //完成
				break;
			case "ERROR": //错误
				break;
			case "BLOCKED"://阻塞
				break;
			default:
				break;
		}
		this.stateCode = stateCode;
	}

	
	/**
     * CRON表达式
     */
	  @NotBlank(message="CRON表达式不能为空",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class}) 
	  @Pattern(regexp = "^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?)*))$",message = "分组名称只能是数字、字母、小数点组成",groups = {JobAddParmValidator.class,JobUpdateParmValidator.class})
    private String  cronExp;
    
    
    

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    
    
  
}
