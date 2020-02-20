package com.datareport.config.filter;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.datareport.bean.Admin;
import com.datareport.common.aes.AesEncryptUtil;
import com.datareport.common.cookies.Cookies;
import com.datareport.common.json.JsonUtils;
import com.datareport.common.jwt.JwtToken;
import com.datareport.config.jwt.JwtUtil;
import com.datareport.config.jwt.RedisConstant;
import com.datareport.config.jwt.UrlEnum;
import com.datareport.config.redis.RedisClient;
import com.datareport.err.CustomException;
import com.datareport.err.stateCode;

/**
 * JWT过滤
 * 
 * @author lwx
 * @date 2019/03/09
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

	@Value("${refreshTokenExpireTime}")
	private String refreshTokenExpireTime;

	@Autowired
	private RedisClient redis;

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

	/**
	 * 这里我们详细说明下为什么最终返回的都是true，即允许访问 例如我们提供一个地址 GET /article 登入用户和游客看到的内容是不同的
	 * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西 所以我们在这里返回true，Controller中可以通过
	 * subject.isAuthenticated() 来判断用户是否登入
	 * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
	 * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		HttpServletRequest httpServletRequests = (HttpServletRequest) request;
		// 判断用户是否想要登入
		if (this.isLoginAttempts(request)) {
			try {
				if(!JwtUtil.verify(httpServletRequests)) {
					return true;
				}
				// 进行Shiro的登录UserRealm
				this.executeLogins(request, response);
			} catch (Exception e) {
				// 认证出现异常，传递错误信息msg
				String msg = e.getMessage();
				// 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
				Throwable throwable = e.getCause();
				if (throwable != null && throwable instanceof SignatureVerificationException) {
					// 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
					msg = "token或者密钥不正确(" + throwable.getMessage() + ")";
				} else if (throwable != null && throwable instanceof TokenExpiredException) {
					// 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
					if (this.refreshTokens(request, response)) {
						return true;
					} else {
						msg = "token已过期(" + throwable.getMessage() + ")";
					}
				} else {
					// 应用异常不为空
					if (throwable != null) {
						// 获取应用异常msg
						msg = throwable.getMessage();
					}
				}
				errHanle((HttpServletRequest) request,(HttpServletResponse) response,"prompt",stateCode.TOKEN_BE_OVERDUE);
				return false;
			}
		}else {
			if(!SecurityUtils.getSubject().isAuthenticated()) {
            // 没有携带Token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            // 获取当前请求类型
            String httpMethod = httpServletRequest.getMethod();
            // 获取当前请求URI
            String requestURI = httpServletRequest.getRequestURI();
            LOGGER.info("当前请求 {} token属性(Token)为空 请求类型 {}", requestURI, httpMethod);
            // mustLoginFlag = true 开启任何请求必须登录才可访问
            final Boolean mustLoginFlag = false;
            if (mustLoginFlag) {
            	throw new CustomException("请先登录");
            }
            }
        }
		return true;
	}

	
	
	public boolean CheckUrl(String url) {
		return UrlEnum.getUrlList().contains(url);
	}
	
	public  static void main(String [] args) {
		System.out.println(UrlEnum.getUrlList().size());
		for(int i=0;i<UrlEnum.getUrlList().size();i++) {
			System.out.println(UrlEnum.getUrlList().get(i));
		}
		System.out.println(UrlEnum.getUrlList().contains("prompt"));
	}
	
	/**
	 * 跳转报错页面
	 * 
	 * @param response
	 * @param
	 * @throws Exception
	 */
	public void errHanle(HttpServletRequest request,HttpServletResponse response,String method, stateCode code) {
		
		try {
			// 如果request.getHeader("X-Requested-With")
			// 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				// 告诉ajax我是重定向
				response.setHeader("REDIRECT", "REDIRECT");
				// 告诉ajax我重定向的路径
				Map<String, String> map = new TreeMap<String, String>();
				//map.put("url",LoginConfigEnum.LOGIN_SYSTEM_URL.getValue() + "/prompt.action?type=" + type );
				map.put("url",method);
				map.put("message", AesEncryptUtil.aesEncrypt(code.getMsg()));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("CONTENTPATH", JsonUtils.objToJson(map));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				response.sendRedirect("/"+method+"?code="+code.getName());
			}

		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
			} 
	
	/**
	 * 这里我们详细说明下为什么重写 可以对比父类方法，只是将executeLogin方法调用去除了
	 * 如果没有去除将会循环调用doGetAuthenticationInfo方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		this.sendChallenge(request, response);
		return false;
	}

	/**
	 * 检测Header里面是否包含Authorization字段，有就进行Token登录认证授权
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		    // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	        String token =null;
		if("POST".equals(httpServletRequest.getMethod())) {
			token = this.getAuthzHeader(request);
		}else {
			token = httpServletRequest.getParameter("authorization");
		}
		
		return token != null;
	}
	
	protected boolean isLoginAttempts(ServletRequest request) {
	    // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	String token=Cookies.getCookies(httpServletRequest, RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN);
	return token != null;
}
	

	
	/**
	 * 进行AccessToken登录认证授权
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		// 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
		JwtToken token = new JwtToken(this.getAuthzHeader(request));
		// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
		this.getSubject(request, response).login(token);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}
	

	/**
	 * 进行AccessToken登录认证授权
	 */
	protected boolean executeLogins(ServletRequest request, ServletResponse response) throws Exception {
		// 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		JwtToken token = new JwtToken(Cookies.getCookies(httpServletRequest, RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN));
		// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
		this.getSubject(request, response).login(token);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}
	
	/**
	 * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
	 */
	@SuppressWarnings("unused")
	private boolean refreshTokens(ServletRequest request, ServletResponse response) {
		// 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token =Cookies.getCookies(httpServletRequest, RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN);
		// 获取当前Token的帐号信息
		String account = JwtUtil.getClaim(token, RedisConstant.ACCOUNT);
		// 判断Redis中RefreshToken是否存在
		if (redis.hHasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,account)) {
			// Redis中RefreshToken还存在，获取RefreshToken的时间戳
			String json = redis.hget(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,account).toString();
			Admin admin=JsonUtils.jsonToBean(json, Admin.class);
			String currentTimeMillisRedis="";//admin.getCurrentTimeMillisRedis()
			// 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
			if (JwtUtil.getClaim(token, RedisConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
				// 获取当前最新时间戳
				String currentTimeMillis = String.valueOf(System.currentTimeMillis());
				// 读取配置文件，获取refreshTokenExpireTime属性
				// PropertiesUtil.readProperties("config.properties");
				// String refreshTokenExpireTime =
				// PropertiesUtil.getProperty("refreshTokenExpireTime");
				// 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
//				admin.setCurrentTimeMillisRedis(currentTimeMillis);
				redis.hset(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,account,JsonUtils.objectToJson(admin),Integer.parseInt(refreshTokenExpireTime));
				// 刷新AccessToken，设置时间戳为当前最新时间戳
				token = JwtUtil.sign(account, currentTimeMillis);
				// 将新刷新的AccessToken再次进行Shiro的登录
				JwtToken jwtToken = new JwtToken(token);
				// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
				this.getSubject(request, response).login(jwtToken);
				HttpServletResponse respones= (HttpServletResponse) response;
				Cookies.setCookies(respones,60*60*4,RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN,token);
				return true;
			}
		}
		return false;
	}


	/**
	 * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
	 */
	@SuppressWarnings("unused")
	private boolean refreshToken(ServletRequest request, ServletResponse response) {
		// 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
		String token = this.getAuthzHeader(request);
		// 获取当前Token的帐号信息
		String account = JwtUtil.getClaim(token, RedisConstant.ACCOUNT);
		// 判断Redis中RefreshToken是否存在
		if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
			// Redis中RefreshToken还存在，获取RefreshToken的时间戳
			String currentTimeMillisRedis = redis.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
			// 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
			if (JwtUtil.getClaim(token, RedisConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
				// 获取当前最新时间戳
				String currentTimeMillis = String.valueOf(System.currentTimeMillis());
				// 读取配置文件，获取refreshTokenExpireTime属性
				// PropertiesUtil.readProperties("config.properties");
				// String refreshTokenExpireTime =
				// PropertiesUtil.getProperty("refreshTokenExpireTime");
				// 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
				redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis,
						Integer.parseInt(refreshTokenExpireTime));
				// 刷新AccessToken，设置时间戳为当前最新时间戳
				token = JwtUtil.sign(account, currentTimeMillis);
				// 将新刷新的AccessToken再次进行Shiro的登录
				JwtToken jwtToken = new JwtToken(token);
				// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
				this.getSubject(request, response).login(jwtToken);
				// 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.setHeader("Authorization", token);
				httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
				return true;
			}
		}
		return false;
	}

	

	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

}