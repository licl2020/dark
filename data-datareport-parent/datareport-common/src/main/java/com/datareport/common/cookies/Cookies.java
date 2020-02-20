package com.datareport.common.cookies;





import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import java.net.URLDecoder;

/**
 * 操作Cookies
 * 
 * @author Administrator
 *
 */
public class Cookies
{
    

    /**
     * 设置Cookies
     * 
     * @param response
     * @param uuid
     * @param index 0 不记录cookie -1 会话级cookie，关闭浏览器失效   60*60(秒) 如果为null默认-1
      * @throws Exception
     */
    public static void setCookies(HttpServletResponse response, Integer index, String name, long value)
        throws Exception
    {
        Cookie cookie = new Cookie(name,String.valueOf(value)); // (key,value)
        cookie.setPath("/");// 这个要设置
        // cookie.setDomain(".aotori.com");//这样设置，能实现两个网站共用
        // cookie.setMaxAge(0);//不记录cookie
        // cookie.setMaxAge(-1);//会话级cookie，关闭浏览器失效
        // cookie.setMaxAge(30*60);//过期时间为1小时
        if(StringUtils.isEmpty(index)){
        	 cookie.setMaxAge(-1);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
        }else{
        cookie.setMaxAge(index);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
        }
        response.addCookie(cookie); // 添加第一个Cookie
    }
    
    /**
     * 设置Cookies
     * 
     * @param response
     * @param uuid
     * @param index 0 不记录cookie -1 会话级cookie，关闭浏览器失效   60*60(秒) 如果为null默认-1
      * @throws Exception
     */
    public static void setCookies(HttpServletResponse response, Integer index, String name, String value)
    {
    	try {
            Cookie cookie = new Cookie(name,String.valueOf(value)); // (key,value)
            cookie.setPath("/");// 这个要设置
            // cookie.setDomain(".aotori.com");//这样设置，能实现两个网站共用
            // cookie.setMaxAge(0);//不记录cookie
            // cookie.setMaxAge(-1);//会话级cookie，关闭浏览器失效
            // cookie.setMaxAge(30*60);//过期时间为1小时
            if(StringUtils.isEmpty(index)){
            	 cookie.setMaxAge(-1);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
            }else{
            cookie.setMaxAge(index);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
            }
            response.addCookie(cookie); // 添加第一个Cookie
		} catch (Exception e) {
		}
    	

    }
    
    
    /**
     * 设置Cookies
     * 
     * @param response
     * @param uuid
     * @param index 0 不记录cookie -1 会话级cookie，关闭浏览器失效   60*60(秒) 如果为null默认-1
      * @throws Exception
     */
    public static void setCookies(HttpServletResponse response, Integer index, String name, String value,String url)
        throws Exception
    {
        Cookie cookie = new Cookie(name, value); // (key,value)
        cookie.setPath("/");// 这个要设置
        // cookie.setDomain(".aotori.com");//这样设置，能实现两个网站共用
        // cookie.setMaxAge(0);//不记录cookie
        // cookie.setMaxAge(-1);//会话级cookie，关闭浏览器失效
        // cookie.setMaxAge(30*60);//过期时间为1小时
        if(StringUtils.isEmpty(index)){
        	 cookie.setMaxAge(-1);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
        }else{
        cookie.setMaxAge(index);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
        }
        cookie.setDomain(url);
        response.addCookie(cookie); // 添加第一个Cookie
    }
    
    /**
     * 获取用户Id
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static String getCookies(HttpServletRequest request, String name)
    {
    	try {
            Cookie cookies[] = request.getCookies();
            if (cookies != null)
            {
                for (int i = 0; i < cookies.length; i++)
                {
                    if (cookies[i].getName().equals(name))
                    {
                        return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                    }
                }
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

    }
    
    /**
     * 删除Cookies
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static void deiCookies(HttpServletRequest request, HttpServletResponse response, String name)
    {
        Cookie cookies[] = request.getCookies();
        if (cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equals(name))
                {
                    Cookie cookie = new Cookie(name, "");// 这边得用"",不能用null
                    cookie.setPath("/");// 设置成跟写入cookies一样的
                    // cookie.setDomain(".wangwz.com");//设置成跟写入cookies一样的
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
    
}
