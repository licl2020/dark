package com.datareport.config.jwt;

public class RedisConstant {

	    public static final String LOGIN_SALT = "storyweb-bp";
	    //request请求头属性
	    public static final String REQUEST_AUTH_HEADER="Authorization";
	 
	    //JWT-account
	    public static final String ACCOUNT = "account";
	 
	    //Shiro redis 前缀
	    public static final String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";
	 
	    //redis-key-前缀-shiro:refresh_token
//	    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";
	    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "user_token";
	    
	    //权限
	    public final static String REDIS_AUTH_MAP = "redis_auth_map";
	 
	    //JWT-currentTimeMillis
	    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";
	    
	    public final static String USER_ID = "user_id";
	    
	    public final static String REDIS_PREFIX_LOGIN="code-generator_token_%s";
	    
	    /**
	     * 请求头 - token  【注：ShiroConfig中放行】
	     */
	    public static final String REQUEST_HEADER = "X-Token";
	    
	    public static final String PASSWORD = "123456";
}
