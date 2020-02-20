//package com.datareport.controller;
//
//
//
//
//
//
//
//
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.datareport.bean.Admin;
//import com.datareport.bean.SystemLog;
//import com.datareport.common.cookies.Cookies;
//import com.datareport.common.ip.IpUtils;
//import com.datareport.common.json.JsonUtils;
//import com.datareport.common.md5.Md5Utils;
//import com.datareport.common.msg.ResultTools;
//import com.datareport.common.properties.PropertiesFileUtil;
//import com.datareport.config.jwt.JwtUtil;
//import com.datareport.config.jwt.RedisConstant;
//import com.datareport.config.redis.RedisClient;
//import com.datareport.err.stateCode;
//import com.datareport.service.AdminService;
//import com.datareport.service.ISystemLogService;
//import com.datareport.util.UserUtil;
//
//import lombok.extern.log4j.Log4j;
//
//
///**
// * @ClassName: LogController
// * @Description: TODO
// * @author: Licl
// * @date: 2017年7月4日 下午5:49:24
// */
//@Controller
//@Log4j
//public class LogJwtController
//{
//	 
//	 @Autowired
//	 private AdminService adminService;
//
//
//    @Resource
//    private ISystemLogService logService;
//    
//	public static Properties cu = PropertiesFileUtil.getProperties("application.properties");	
//
//
//	@Autowired
//	private RedisClient redis;
//
//	@Autowired
//	private UserUtil userUtil;
//	
//   
//	@RequestMapping("/Login")
//	public String Login(HttpServletRequest request, HttpServletResponse respone, Model model)
//			throws Exception
//	{
//		return geturl("login");
//	}
//	
//	@RequestMapping(value="/LoginVerify", produces = "application/json; charset=utf-8")
//	@ResponseBody
//	    public ResultTools doLogin(Admin bean, HttpServletRequest request, HttpServletResponse respone, Model model){
//	        log.info("进入登录处理");
//	        String exceptionClassName =bean.getuName();
//	        log.info("exceptionClassName:"+exceptionClassName);
//	        QueryWrapper<Admin> ew = new QueryWrapper<>();
//	        ew.eq("u_name", bean.getuName());
//	        List<Admin> userList = adminService.list(ew);
//	        String error = null;
//	        int code=-100;
//	        if (userList.size() == 0) {
//	        	error = "未知账户";
//	        }else {
//	        	String password=Md5Utils.getMD5Pwd(bean.getuName(), bean.getPassword());
//	        	if (!password.equals(userList.get(0).getPassword())) {
//	        		error = "密码不正确";
//	        	}else if(userList.get(0).getState()==2) {
//	        		error = "账户已禁用";
//	        	}else {
//	        		String token = java.util.UUID.randomUUID().toString();
//	            	// 清除可能存在的shiro权限信息缓存
//	            	if (redis.hHasKey(RedisConstant.PREFIX_SHIRO_CACHE,token)) {
//	            		redis.del(RedisConstant.PREFIX_SHIRO_CACHE,token);
//	            	}
//	            	// 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
//	            	userList.get(0).setCurrentTimeMillisRedis(String.valueOf(System.currentTimeMillis()));
//	            	redis.hset(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, token, JsonUtils.objectToJson(userList.get(0)), Integer.parseInt(cu.getProperty("refreshTokenExpireTime")));
//	            	// 从Header中Authorization返回AccessToken，时间戳为当前时间戳
//	            	String jwtToken = JwtUtil.sign(String.valueOf(token), userList.get(0).getCurrentTimeMillisRedis());
//	            	Cookies.setCookies(respone,60*60*4,RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,jwtToken);
////	            	respone.setHeader("Authorization", jwtToken);
////	            	respone.setHeader("Access-Control-Expose-Headers", "Authorization");
////	            	 UsernamePasswordToken tokens = new UsernamePasswordToken();
////	                 Subject subject = SecurityUtils.getSubject();
////	                 subject.login(tokens);;
////	            	SecurityUtils.getSubject().login(new JwtToken(jwtToken));
//	        		code=200;
//	        		addLog(userList.get(0),"登录");
//	        	}
//	        } 
//	            return ResultTools.getInstance(code, "", error);
//	    }
//	
//	
//	
//	/**
//	 * 退出
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/exit")
//	public String exit(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		    Subject subject = SecurityUtils.getSubject();
//			Cookies.deiCookies(request, response,RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN);
//			addLog(userUtil.getUser(),"退出");
//			 userUtil.exit();
//			subject.logout();
//		 return "redirect:Login"; 
//	}
//	
//	
//	/**
//	 * @Title: 提示
//	 * @Description: TODO
//	 * @param bean
//	 * @param request
//	 * @param respone
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 * @return: String
//	 */
//	@RequestMapping("/prompt")
//	public String prompt(HttpServletRequest request, HttpServletResponse respone, Model model)
//			throws Exception
//	{
//		String code =request.getParameter("code");
//		model.addAttribute("content",stateCode.getValue(code));
//		return "errors/prompt";
//	}
//	
//	
//	
//	
//	public void addLog(Admin admin,String title) {
//		
//	    SystemLog operationLog = new SystemLog();
//	    operationLog.setIpAddress(IpUtils.intranetIp());
//        operationLog.setRunTime(0l);
//        operationLog.setReturnValue("/Login");
//        operationLog.setArgs("无");
//        operationLog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        operationLog.setMethod("com.datareport.controller.LogController.exit");
//        operationLog.setUserId(admin.getId());
//        operationLog.setUserName(admin.getcName());
//        operationLog.setLogLevel(1);
//        operationLog.setLogDescribe('"'+admin.getcName()+'"'+"=》"+title);
//        operationLog.setOperationType("select");
//        operationLog.setOperationUnit("user");
//        logService.save(operationLog);
//        
//	}
//	
//	
//	 //被踢出后跳转的页面
//    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
//    public String kickOut() {
//        return "errors/405";
//    }
//
//
//	/**
//	 * @Title: geturl
//	 * @Description: TODO
//	 * @param url
//	 * @return
//	 * @return: String
//	 */
//	public String geturl(String url)
//	{
//		return "login/" + url;
//	}
//
//	
//	
//
//	
//}
