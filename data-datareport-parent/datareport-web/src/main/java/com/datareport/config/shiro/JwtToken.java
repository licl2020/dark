package com.datareport.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 * 
 * @author LICL
 */
public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 1900286977895826147L;

	/**
	 * Token
	 */
	private String token;

	public JwtToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}