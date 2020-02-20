package com.datareport.common.cookies;






import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**  
* 使用该类必须在 web.xml 中添加监听（org.springframework.web.context.request.RequestContextListener） 
* 该作用域仅适用于WebApplicationContext环境 
*/  
public class Global {

	 /** 
     * 获取当前请求session 
     * @return 
     */  
    public static HttpServletRequest getHttpServletRequest(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes())  
                .getRequest();  
        return request;  
    }     
    /** 
     * 获取当前请求session 
     * @return 
     */  
    public static HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();  
    }  
}
