package com.datareport.interceptor;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.datareport.bean.Admin;
import com.datareport.common.String.StringUtil;
import com.datareport.common.cookies.Cookies;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.properties.PropertiesFileUtil;
import com.datareport.config.jwt.JwtUtil;
import com.datareport.config.jwt.RedisConstant;
import com.datareport.config.redis.RedisClient;
import com.datareport.err.JumpErrHtml;
import com.datareport.err.stateCode;
import com.datareport.util.UserUtil;

import lombok.extern.log4j.Log4j;
@Component
@Log4j
public class LoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private RedisClient redis;
	
	@Autowired
	private UserUtil userUtil;
	
	public static Properties cu = PropertiesFileUtil.getProperties("application.properties");	

	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {  
        try {  
            log.info("登陆拦截"+request.getRequestURI());  
            //校验用户是否已经登录,如果登录过,将之前用户踢掉,同时更新缓存中用户信息  
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            if(StringUtil.isEmpty(token)) {
            	JumpErrHtml.errHanle(request, response,"Login",stateCode.TOKEN_BE_OVERDUE);
            	return false;  
            }
            //获取key
            String reds_key=JwtUtil.getClaim(token, RedisConstant.USER_ID);
            if(!redis.hHasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, reds_key)) {
            	JumpErrHtml.errHanle(request, response,"Login", stateCode.TOKEN_BE_OVERDUE);
            	return false; 
            }
            
       	     Admin admin=userUtil.getUser();
       	     String uesrToken=JwtUtil.getClaim(token, RedisConstant.ACCOUNT);
       	     if(!admin.getToken().equals(uesrToken)) {
       	    	JumpErrHtml.errHanle(request, response, "Login",stateCode.LANDING_ELSEWHERE);
       	    	return false; 
       	     }
       	     //刷新时间
       	     redis.hset(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,reds_key,JsonUtils.objectToJson(admin),Integer.parseInt(cu.getProperty("refreshTokenExpireTime")));
       	     Cookies.setCookies(response,Integer.parseInt(cu.getProperty("refreshTokenExpireTime")),RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,token);
        } catch (Exception e) {  
            log.info("preHandle="+e.getMessage());  
        }  
        return true;  
    }  
	
  
	   /**
     * 该方法将在Controller执行之后，返回视图之前执行，modelAndView表示请求Controller处理之后返回的Model和View对象，所以可以在
     * 这个方法中修改modelAndView的属性，从而达到改变返回的模型和视图的效果。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {

    }
}
