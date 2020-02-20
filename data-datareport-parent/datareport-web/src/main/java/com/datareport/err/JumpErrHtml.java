package com.datareport.err;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datareport.common.aes.AesEncryptUtil;
import com.datareport.common.json.JsonUtils;

/**
 * 统一跳转
 * 
 * @author 16472
 *
 */
public class JumpErrHtml {

	/**
	 * 跳转报错页面
	 * 
	 * @param response
	 * @param
	 * @throws Exception
	 */
	public static void errHanle(HttpServletRequest request, HttpServletResponse response, String method,
			stateCode code) {
		// 如果request.getHeader("X-Requested-With")
		// 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
		try {
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {

				// 告诉ajax我是重定向
				response.setHeader("REDIRECT", "REDIRECT");
				// 告诉ajax我重定向的路径
				Map<String, String> map = new TreeMap<String, String>();
				// map.put("url",LoginConfigEnum.LOGIN_SYSTEM_URL.getValue() +
				// "/prompt.action?type=" + type );
				map.put("url", method);
				map.put("message", AesEncryptUtil.aesEncrypt(code.getMsg()));
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("CONTENTPATH", JsonUtils.objToJson(map));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				response.sendRedirect("/prompt" + "?code=" + code.getName());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
