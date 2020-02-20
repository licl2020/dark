package com.datareport.config.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.datareport.err.JumpErrHtml;
import com.datareport.err.stateCode;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {




    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated()) {
        	JumpErrHtml.errHanle(httpRequest, httpResponse, "Login", stateCode.PLEASE_LOGIN_AFAIN);
        }
//        if (subject.getPrincipal() == null) {
//            //设置响应头
//            httpResponse.setCharacterEncoding("UTF-8");
//            httpResponse.setContentType("application/json");
//            //设置返回的数据
//            String result = JsonUtils.objToJson( new ResultTools(-100, "未登录","error"));
//            //写回给客户端
//            PrintWriter out = httpResponse.getWriter();
//            out.write(result);
//            //刷新和关闭输出流
//            out.flush();
//            out.close();
//        } else {
//            //设置响应头
//            System.out.println("=========onAccessDenied==========返回json====>>>>");
//            httpResponse.setCharacterEncoding("UTF-8");
//            httpResponse.setContentType("application/json");
//            //设置返回的数据
//            String result = JsonUtils.objToJson( new ResultTools(-100, "未登录","error"));;
//            //写回给客户端
//            PrintWriter out = httpResponse.getWriter();
//            out.write(result);
//            //刷新和关闭输出流
//            out.flush();
//            out.close();
//        }
        return false;
    }

}