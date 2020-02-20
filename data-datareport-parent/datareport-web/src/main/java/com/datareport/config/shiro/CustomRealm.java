package com.datareport.config.shiro;



import java.util.List;
import java.util.Properties;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.datareport.bean.Admin;
import com.datareport.bean.CommonPermssion;
import com.datareport.bean.CommonRole;
import com.datareport.common.md5.Md5Utils;
import com.datareport.common.properties.PropertiesFileUtil;
import com.datareport.config.chcache.EhcacheUtil;
import com.datareport.service.AdminService;
import com.datareport.util.UserUtil;

import lombok.extern.log4j.Log4j;

/**
 * 描述：
 *
 * @author caojing
 * @create 2019-01-27-13:57
 */
@Log4j
public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	@Lazy
    private AdminService adminService;
	
	@Autowired
	@Lazy
    private EhcacheUtil ehcacheUtil;
	
//
//	@Autowired
//	private RedisClient redis;
	
	@Autowired
	private UserUtil userUtil;
	
	public static Properties cu = PropertiesFileUtil.getProperties("application.properties");
	
	
	  /**
   * 必须重写此方法，不然Shiro会报错
   */
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	 @Override
	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		     log.info("shiro开始授权");
	         SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	    	 Admin admin=userUtil.getUser();
	    	 List<Admin> userList = adminService.Login(admin.getuName());
	        if(userList.size()==1) {
	        	for(CommonRole role:userList.get(0).getRoles()) {
	        		 authorizationInfo.addRole(String.valueOf(role.getId()));
	 	            for (CommonPermssion permission :role.getPermssion()) {
	 	                authorizationInfo.addStringPermission(permission.getPermission());
	 	            }
	        	}
	           
	         }
	        log.info("授权结束");
	        return authorizationInfo;
	    }

	    @Override
	    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
	    	 log.info("开始登陆认证");
	    	        //根据用户名从数据库获取密码
	                 String token = (String) authenticationToken.getPrincipal();
   				     SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
   						     token, //用户名
   						     Md5Utils.getMD5Pwd(token,token), //密码，写死
   		                    ByteSource.Util.bytes(token+"salt"),//salt=username+salt
   		                    getName()  //realm name
   		            );
   				 
   				return authenticationInfo;
	    	
	    }
	    
}

