package com.datareport.controller;









import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datareport.bean.Admin;
import com.datareport.bean.SystemLog;
import com.datareport.common.String.StringUtil;
import com.datareport.common.cookies.Cookies;
import com.datareport.common.ip.IpUtils;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.md5.Md5Utils;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.properties.PropertiesFileUtil;
import com.datareport.config.jwt.JwtUtil;
import com.datareport.config.jwt.RedisConstant;
import com.datareport.config.redis.RedisClient;
import com.datareport.config.shiro.JwtToken;
import com.datareport.err.stateCode;
import com.datareport.service.AdminService;
import com.datareport.service.ISystemLogService;
import com.datareport.util.UserUtil;


/**
 * @ClassName: LogController
 * @Description: TODO
 * @author: Licl
 * @date: 2017年7月4日 下午5:49:24
 */
@Controller
public class LogController
{


    @Resource
    private ISystemLogService logService;

	@Autowired
	private RedisClient redis;
    
	public static Properties cu = PropertiesFileUtil.getProperties("application.properties");	

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
    private AdminService adminService;
	
   
	@RequestMapping("/Login")
	public String Login(HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception
	{
		return geturl("login");
	}
	
	@RequestMapping(value="/LoginVerify", produces = "application/json; charset=utf-8")
	@ResponseBody
	    public ResultTools doLogin(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model){
	       String error = null;
	        int code=-100;
	        try {
		        if(StringUtil.isEmpty(bean.getuName())) {
		        	error="用户名不能为null";
		        }else if(StringUtil.isEmpty(bean.getPassword())) {
		        	error="用户名不正确";
		        }else {
		            QueryWrapper<Admin> ew = new QueryWrapper<>();
			        ew.eq("u_name",bean.getuName());
			        List<Admin> userList = adminService.list(ew);
			        if(userList.size()==0) {
			        	error="用户不存在";
			        }else {
			        	  String password=Md5Utils.getMD5Pwd(bean.getuName(),bean.getPassword());
			        	  if(!password.equals(userList.get(0).getPassword())) {
			        		  error="密码不正确";
			        	  }else if(userList.get(0).getState()==2) {
			        		  error="用户已经被禁用";
					        }else {
					        	String token = java.util.UUID.randomUUID().toString();
					        	userList.get(0).setToken(token);
				            	// 清除可能存在的shiro权限信息缓存
				            	if (redis.hHasKey(RedisConstant.PREFIX_SHIRO_CACHE,String.valueOf(userList.get(0).getId()))) {
				            		redis.del(RedisConstant.PREFIX_SHIRO_CACHE,String.valueOf(userList.get(0).getId()));
				            	}
				            	 redis.hset(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, String.valueOf(userList.get(0).getId()), JsonUtils.objectToJson(userList.get(0)), Integer.parseInt(cu.getProperty("refreshTokenExpireTime")));
				            	 String jwtToken = JwtUtil.sign2(token,String.valueOf(userList.get(0).getId()));
				            	 Cookies.setCookies(respone,Integer.parseInt(cu.getProperty("refreshTokenExpireTime")),RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,jwtToken);
//					        	 UsernamePasswordToken token = new UsernamePasswordToken(bean.getuName(),bean.getPassword());
				                 Subject subject = SecurityUtils.getSubject();
				                 subject.login(new JwtToken(jwtToken));;
				                 if(subject.isAuthenticated()) {
				  	        		code=200;
				  	        		addLog(userUtil.getUser(),"登录");
				                  }
					        }
			        }
		        }
                 
			} catch (AuthenticationException e) {
				code=-100;
				error=e.getMessage();
			}
	            return ResultTools.getInstance(code, error, "");
	    }
	
	
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exit")
	public String exit(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
			Cookies.deiCookies(request, response,RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN);
			addLog(userUtil.getUser(),"退出");
			 userUtil.exit();
			  Subject subject = SecurityUtils.getSubject();
			subject.logout();
		 return "redirect:Login"; 
	}
	
	
	/**
	 * @Title: 提示
	 * @Description: TODO
	 * @param bean
	 * @param request
	 * @param respone
	 * @param model
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@RequestMapping("/prompt")
	public String prompt(HttpServletRequest request, HttpServletResponse respone, Model model)
			throws Exception
	{
		String code =request.getParameter("code");
		model.addAttribute("content",stateCode.getValue(code));
		return "errors/prompt";
	}
	
	
	@RequestMapping(value = "/autherror")
    public String autherror(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		String code =request.getParameter("code");
		model.addAttribute("content",stateCode.getValue(code));
        return "errors/404";
    }
	
	
	
	public void addLog(Admin admin,String title) {
		
	    SystemLog operationLog = new SystemLog();
	    operationLog.setIpAddress(IpUtils.intranetIp());
        operationLog.setRunTime(0l);
        operationLog.setReturnValue("/Login".getBytes());
        operationLog.setArgs("无".getBytes());
        operationLog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        operationLog.setMethod("com.datareport.controller.LogController.exit");
        operationLog.setUserId(admin.getId());
        operationLog.setUserName(admin.getuName());
        operationLog.setLogLevel(1);
        operationLog.setLogDescribe('"'+admin.getcName()+'"'+"=》"+title);
        operationLog.setOperationType("select");
        operationLog.setOperationUnit("user");
        logService.save(operationLog);
        
	}
	
	
	 //被踢出后跳转的页面
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickOut() {
        return "errors/405";
    }


	/**
	 * @Title: geturl
	 * @Description: TODO
	 * @param url
	 * @return
	 * @return: String
	 */
	public String geturl(String url)
	{
		return "login/" + url;
	}

	
	

	
}
