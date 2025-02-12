package com.datareport.config.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.datareport.common.cookies.Cookies;
import com.datareport.err.CustomException;


/**
 * JAVA-JWT工具类
 * 
 * @author LICL
 */
@Component
public class JwtUtil {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	/**
	 * 过期时间改为从配置文件获取
	 */
	private static String accessTokenExpireTime;

	/**
	 * JWT认证加密私钥(Base64加密)
	 */
	private static String encryptJWTKey;

	@Value("${accessTokenExpireTime}")
	public void setAccessTokenExpireTime(String accessTokenExpireTime) {
		JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
	}

	@Value("${encryptJWTKey}")
	public void setEncryptJWTKey(String encryptJWTKey) {
		JwtUtil.encryptJWTKey = encryptJWTKey;
	}

	/**
	 * 校验token是否正确
	 * 
	 * @param token
	 *            Token
	 * @return boolean 是否正确
	 * @author Wang926454
	 * @date 2018/8/31 9:05
	 */
	@SuppressWarnings("unused")
	public static boolean verify(String token) {
		try {
			// 帐号加JWT私钥解密
			String secret = getClaim(token, RedisConstant.ACCOUNT) + Base64Util.decodeThrowsException(encryptJWTKey);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
			throw new CustomException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
		}
	}
	

	/**
	 * 校验token是否正确
	 * 
	 * @param token
	 *            Token
	 * @return boolean 是否正确
	 * @author Wang926454
	 * @date 2018/8/31 9:05
	 */
	@SuppressWarnings("unused")
	public static boolean verify(HttpServletRequest httpServletRequest) {
		try {
			// 帐号加JWT私钥解密
			String token=Cookies.getCookies(httpServletRequest, RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN);
			String secret = getClaim(token, RedisConstant.ACCOUNT) + Base64Util.decodeThrowsException(encryptJWTKey);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获得Token中的信息无需secret解密也能获得
	 * 
	 * @param token
	 * @param claim
	 * @return java.lang.String
	 * @author Wang926454
	 * @date 2018/9/7 16:54
	 */
	public static String getClaim(String token, String claim) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			// 只能输出String类型，如果是其他类型返回null
			return jwt.getClaim(claim).asString();
		} catch (JWTDecodeException e) {
			LOGGER.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
			throw new CustomException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
		}
	}

	/**
	 * 生成签名
	 * 
	 * @param account
	 *            帐号
	 * @return java.lang.String 返回加密的Token
	 * @author Wang926454
	 * @date 2018/8/31 9:07
	 */
	public static String sign(String account, String currentTimeMillis) {
		try {
			// 帐号加JWT私钥加密
			String secret = account + Base64Util.decodeThrowsException(encryptJWTKey);
			// 此处过期时间是以毫秒为单位，所以乘以1000
			Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带account帐号信息
			return JWT.create().withClaim("account", account).withClaim("currentTimeMillis", currentTimeMillis)
					.withExpiresAt(date).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
			throw new CustomException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
		}
	}
	
	
	/**
	 * 生成签名
	 * 
	 * @param account
	 *            帐号
	 * @return java.lang.String 返回加密的Token
	 * @author Wang926454
	 * @date 2018/8/31 9:07
	 */
	public static String sign2(String account, String currentTimeMillis) {
		try {
			// 帐号加JWT私钥加密
			String secret = account + Base64Util.decodeThrowsException(encryptJWTKey);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带account帐号信息
			return JWT.create().withClaim("account", account).withClaim("user_id", currentTimeMillis).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
			throw new CustomException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
		}
	}
}
