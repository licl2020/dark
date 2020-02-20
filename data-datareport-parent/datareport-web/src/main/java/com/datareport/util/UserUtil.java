package com.datareport.util;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datareport.bean.Admin;
import com.datareport.common.json.JsonUtils;
import com.datareport.config.jwt.JwtUtil;
import com.datareport.config.jwt.RedisConstant;
import com.datareport.config.redis.RedisClient;

/**
 * 获取当前登录用户工具类
 *
 */
@Component
public class UserUtil {



    @Autowired
   private  RedisClient redisClient;
    
    /**
     * 获取当前登录用户
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public Admin getUser() {
    	 String token = (String) SecurityUtils.getSubject().getPrincipal();
		 String reds_key=JwtUtil.getClaim(token, RedisConstant.USER_ID);
		 Object json=redisClient.hget(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, reds_key);
     	 Admin admin=JsonUtils.jsonToBean(json.toString(), Admin.class);
        return admin;
    }
    
    public void exit() {
    	 String token = (String) SecurityUtils.getSubject().getPrincipal();
		 String reds_key=JwtUtil.getClaim(token, RedisConstant.USER_ID);
		 redisClient.hdel(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, reds_key);
    }

    /**
     * 获取当前登录用户Id
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public Integer getUserId() {
        return getUser().getId();
    }

    /**
     * 获取当前登录用户Token
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户Account
     *
     * @param
     * @return com.wang.model.UserDto
     * @author wliduo[i@dolyw.com]
     * @date 2019/3/15 11:48
     */
    public String getAccount() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Account
        return JwtUtil.getClaim(token, RedisConstant.ACCOUNT);
    }
}
