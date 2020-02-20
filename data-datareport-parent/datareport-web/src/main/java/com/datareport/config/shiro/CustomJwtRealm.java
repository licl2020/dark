//package com.datareport.config.shiro;
//
//
//
//import java.util.List;
//
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//
//import com.datareport.bean.Admin;
//import com.datareport.bean.CommonPermssion;
//import com.datareport.bean.CommonRole;
//import com.datareport.common.json.JsonUtils;
//import com.datareport.common.jwt.JwtToken;
//import com.datareport.common.md5.Md5Utils;
//import com.datareport.config.chcache.EhcacheUtil;
//import com.datareport.config.jwt.JwtUtil;
//import com.datareport.config.jwt.RedisConstant;
//import com.datareport.config.redis.RedisClient;
//import com.datareport.service.AdminService;
//
//import lombok.extern.log4j.Log4j;
//
///**
// * 描述：
// *
// * @author caojing
// * @create 2019-01-27-13:57
// */
//@Log4j
//public class CustomJwtRealm extends AuthorizingRealm {
//	
//	@Autowired
//	@Lazy
//    private AdminService adminService;
//	
//	@Autowired
//	@Lazy
//    private EhcacheUtil ehcacheUtil;
//	
//
//	@Autowired
//	private RedisClient redis;
//	
//	/**
//	 * 大坑，必须重写此方法，不然Shiro会报错
//	 */
//	@Override
//	public boolean supports(AuthenticationToken token) {
//		return token instanceof JwtToken;
//	}
//	
//	  /**
//     * 必须重写此方法，不然Shiro会报错
//     */
//	
//	 @Override
//	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		     log.info("shiro开始授权");
//	         SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//	         if(authorizationInfo.getStringPermissions()==null) {
//	         String token=(String) principals.getPrimaryPrincipal();
//	         // 解密获得account，查询redis数据
//	    	 String reds_key=JwtUtil.getClaim(token, RedisConstant.ACCOUNT);
//	    	 Object json=redis.hget(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, reds_key);
//	    	 Admin admin=JsonUtils.jsonToBean(json.toString(), Admin.class);
//	    	 
//	    	 List<Admin> userList = adminService.Login(admin.getuName());
//	        if(userList.size()==1) {
//	        	for(CommonRole role:userList.get(0).getRoles()) {
//	        		 authorizationInfo.addRole(role.getLabelId());
//	 	            for (CommonPermssion permission :role.getPermssion()) {
//	 	                authorizationInfo.addStringPermission(permission.getPermission());
//	 	            }
//	        	}
//	           
//	        }
//	         }
//	        log.info("授权结束");
//	        return authorizationInfo;
//	    }
//
//	    @Override
//	    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
//	    	 log.info("开始登陆认证");
//	    	 String token=(String)auth.getCredentials();
//	 		// 解密获得account，查询redis数据
//	    	 String reds_key=JwtUtil.getClaim(token, RedisConstant.ACCOUNT);
//	    	 if (null !=reds_key &&redis.hHasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,reds_key)) {
//         	  
//	    		 Object json=redis.hget(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN, reds_key);
//	    		 Admin admin=JsonUtils.jsonToBean(json.toString(), Admin.class);
//	        	// 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
//	    		if (JwtUtil.verify(token)) {
//	    			// 获取AccessToken时间戳，与RefreshToken的时间戳对比
//	    			if (JwtUtil.getClaim(token, RedisConstant.CURRENT_TIME_MILLIS).equals(admin.getCurrentTimeMillisRedis())) {
//	    				
//	    				 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//	    						     token, //用户名
//	    						     Md5Utils.getMD5Pwd(token,token), //密码，写死
//	    		                    ByteSource.Util.bytes(token+"salt"),//salt=username+salt
//	    		                    getName()  //realm name
//	    		            );
//	    				return authenticationInfo;
//	    			}
//	    		}
//	    		throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
//	    		 
//	    	 }else {
//	    			throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
//	    	 }
//	    	 
//	    	
//	    }
//	    
//}
//
