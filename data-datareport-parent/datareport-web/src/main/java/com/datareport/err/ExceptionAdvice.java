package com.datareport.err;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.datareport.common.msg.ResultTools;


/**
 * 异常控制处理器
 * 
 * @author LICL
 */
@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * 捕捉所有Shiro异常
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ShiroException.class)
	public void handle401(ShiroException e) {
		
		err("autherror",stateCode.NO_ACCESS);
	}

	/**
	 * 单独捕捉Shiro(UnauthorizedException)异常 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public void handle401(UnauthorizedException e) {
		err("autherror",stateCode.NO_ACCESS);
	}

	/**
	 * 单独捕捉Shiro(UnauthenticatedException)异常
	 * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthenticatedException.class)
	public void handle401(UnauthenticatedException e) {
		err("prompt",stateCode.PLEASE_LOGIN_FIRST);
	}

	 /**
     * 一般的参数绑定时候抛出的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public ResultTools handleBindException(ParamException ex){
    	System.out.println(ex);
        return new ResultTools(-100,ex.getMessage(), "error");
    }

	/**
	 * 捕捉404异常
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public void handle(NoHandlerFoundException e) {
		err("autherror",stateCode.FOUR_HUNDRED_AND_FOUR);
	}

	/**
	 * 捕捉其他所有异常
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void globalException(HttpServletRequest request, Throwable ex) {
		err("autherror",stateCode.Exception);
	}

	/**
	 * 捕捉其他所有自定义异常
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CustomException.class)
	public void handle(CustomException e) {
		err("autherror",stateCode.CustomException);
	}

	/**
	 * 获取状态码
	 */
	@SuppressWarnings("unused")
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

	
	public void err(String method,stateCode code) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		HttpServletResponse response=sra.getResponse();
		JumpErrHtml.errHanle(request,response,method,code);
	}
	
 
}
