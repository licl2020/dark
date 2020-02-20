//package com.datareport.err;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.ShiroException;
//import org.apache.shiro.authz.UnauthenticatedException;
//import org.apache.shiro.authz.UnauthorizedException;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import com.datareport.common.aes.AesEncryptUtil;
//import com.datareport.common.json.JsonUtils;
//import com.datareport.common.msg.ResultTools;
//
//
///**
// * 异常控制处理器
// * 
// * @author LICL
// */
//@RestControllerAdvice
//public class ExceptionAdvice2 {
//
//	/**
//	 * 捕捉所有Shiro异常
//	 */
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	@ExceptionHandler(ShiroException.class)
//	public ResultTools handle401(ShiroException e) {
//		return new ResultTools(-100, "error", "无权访问(Unauthorized):" + e.getMessage());
//	}
//
//	/**
//	 * 单独捕捉Shiro(UnauthorizedException)异常 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
//	 */
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	@ExceptionHandler(UnauthorizedException.class)
//	public ResultTools handle401(UnauthorizedException e) {
//		return new ResultTools(-100, "error","无权访问(Unauthorized):当前Subject没有此请求所需权限(" + e.getMessage() + ")");
//	}
//
//	/**
//	 * 单独捕捉Shiro(UnauthenticatedException)异常
//	 * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
//	 */
//	@ResponseStatus(HttpStatus.UNAUTHORIZED)
//	@ExceptionHandler(UnauthenticatedException.class)
//	public ResultTools handle401(UnauthenticatedException e) {
//		return new ResultTools(-100, "error","无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)");
//	}
//
//	/**
//	 * 捕捉校验异常(BindException)
//	 */
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(BindException.class)
//	public ResultTools validException(BindException e) {
//		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//		Map<String, Object> result = this.getValidError(fieldErrors);
//		return new ResultTools(-100, "error",result.get("errorMsg").toString()+result.get("errorList"));
//	}
//
//	/**
//	 * 捕捉校验异常(MethodArgumentNotValidException)
//	 */
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResultTools validException(MethodArgumentNotValidException e) {
//		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//		Map<String, Object> result = this.getValidError(fieldErrors);
//		return new ResultTools(-100, "error",result.get("errorMsg").toString()+result.get("errorList"));
//	}
//
//	/**
//	 * 捕捉404异常
//	 */
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public ResultTools handle(NoHandlerFoundException e) {
//		return new ResultTools(-100, "error",e.getMessage());
//	}
//
//	/**
//	 * 捕捉其他所有异常
//	 */
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(Exception.class)
//	public ResultTools globalException(HttpServletRequest request, Throwable ex) {
//		System.out.println(ex);
//		return new ResultTools(-100, "error",ex.toString() + ": " + ex.getMessage());
//	}
//
//	/**
//	 * 捕捉其他所有自定义异常
//	 */
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(CustomException.class)
//	public ResultTools handle(CustomException e) {
//		return new ResultTools(-100, "error",e.getMessage());
//	}
//
//	/**
//	 * 获取状态码
//	 */
//	@SuppressWarnings("unused")
//	private HttpStatus getStatus(HttpServletRequest request) {
//		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//		if (statusCode == null) {
//			return HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return HttpStatus.valueOf(statusCode);
//	}
//
//	/**
//	 * 获取校验错误信息
//	 */
//	private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
//		Map<String, Object> result = new HashMap<String, Object>(16);
//		List<String> errorList = new ArrayList<String>();
//		StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
//		for (FieldError error : fieldErrors) {
//			errorList.add(error.getField() + "-" + error.getDefaultMessage());
//			errorMsg.append(error.getField() + "-" + error.getDefaultMessage() + ".");
//		}
//		result.put("errorList", errorList);
//		result.put("errorMsg", errorMsg);
//		return result;
//	}
//	
//	/**
//	 * 跳转报错页面
//	 * 
//	 * @param response
//	 * @param
//	 * @throws Exception
//	 */
//	public void errHanle(HttpServletRequest request, HttpServletResponse response,String method, String msg) throws Exception {
//		// 如果request.getHeader("X-Requested-With")
//		// 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
//
//		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
//
//			// 告诉ajax我是重定向
//			response.setHeader("REDIRECT", "REDIRECT");
//			// 告诉ajax我重定向的路径
//			Map<String, String> map = new TreeMap<String, String>();
//			//map.put("url",LoginConfigEnum.LOGIN_SYSTEM_URL.getValue() + "/prompt.action?type=" + type );
//			map.put("url",method);
//			map.put("message", AesEncryptUtil.aesEncrypt(msg));
//			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//			response.setHeader("CONTENTPATH", JsonUtils.objToJson(map));
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		} else {
//			response.sendRedirect(method);
//		}
//	} 
//}
