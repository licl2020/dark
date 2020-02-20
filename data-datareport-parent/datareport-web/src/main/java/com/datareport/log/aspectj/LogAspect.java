package com.datareport.log.aspectj;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.datareport.bean.Admin;
import com.datareport.bean.SystemLog;
import com.datareport.common.ip.IpUtils;
import com.datareport.common.json.JsonUtils;
import com.datareport.log.annotation.Log;
import com.datareport.service.ISystemLogService;
import com.datareport.util.UserUtil;

import lombok.extern.log4j.Log4j;

@Aspect
@Component
@Log4j
public class LogAspect {

    @Resource
    private ISystemLogService logService;
    
    @Autowired
	private UserUtil userUtil;
    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.wwj.springboot.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(com.datareport.log.annotation.Log)")
    public void operationLog(){}

//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;
    
    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res =  joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
//                String sql = SqlUtils.getMybatisSql(joinPoint,sqlSessionFactory);
//                System.out.println(sql);
                addOperationLog(joinPoint,res,time);
            }catch (Exception e){
            	log.info("LogAspect 操作失败：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addOperationLog(JoinPoint joinPoint, Object res, long time){
        //获得登录用户信息
    	  Admin admin = userUtil.getUser() ;
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        SystemLog operationLog = new SystemLog();
        //获取内网地址IpUtils.intranetIp()
        //获取外网地址IpUtils.internetIp()
        operationLog.setIpAddress(IpUtils.intranetIp());
        operationLog.setRunTime(time);
        operationLog.setReturnValue(JSONObject.toJSONString(res).getBytes());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String queryString = request.getQueryString();   
        Object[] args = joinPoint.getArgs();   
        String method =request.getMethod();
        if("POST".equals(method)){   
           	// 获取参数
			Map<String, String[]> parameterMap = request.getParameterMap();
			// 写入参数
			if (null != parameterMap
					&& parameterMap.size() > 0) {
				// 使用json转换工具 将参数转为json串，以便存入数据库
				String jsons = JsonUtils.formJsonStr(parameterMap);
				if(jsons != null) {
					operationLog.setArgs((JsonUtils.objToJson(args[0])+","+jsons).getBytes());
				}else {
					jsons="无";
					operationLog.setArgs(jsons.getBytes());
				}
				 
			}
    	
    		}else if("GET".equals(method)){  
    			if(queryString != null) {
    				 operationLog.setArgs(JsonUtils.objToJson(queryString).getBytes());
				}else {
					queryString="无";
					 operationLog.setArgs(queryString.getBytes());
				}
    			
    			}  
       
        operationLog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setUserId(admin.getId());
        operationLog.setUserName(admin.getuName());
        Log annotation = signature.getMethod().getAnnotation(Log.class);
        if(annotation != null){
            operationLog.setLogLevel(annotation.level());
            operationLog.setLogDescribe(getDetail(((MethodSignature)joinPoint.getSignature()).getParameterNames(),joinPoint.getArgs(),annotation));
            operationLog.setOperationType(annotation.operationType().getValue());
            operationLog.setOperationUnit(annotation.operationUnit().getValue());
        }
        //TODO 这里保存日志
        log.info("记录日志:" + operationLog.toString());
        logService.save(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     * @param argNames 方法参数名称数组
     * @param args 方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, Log annotation){
        //获得登录用户信息
    	Admin admin = userUtil.getUser() ;
        Map<Object, Object> map = new HashMap<>(4);
        for(int i = 0;i < argNames.length;i++){
            map.put(argNames[i],args[i]);
        }

        String detail = annotation.detail();
        try {
            detail = "'" + admin.getcName() + "'=》" + annotation.detail();
//            for (Map.Entry<Object, Object> entry : map.entrySet()) {
//                Object k = entry.getKey();
//                Object v = entry.getValue();
//                detail = detail.replace("{{" + k + "}}", JSONObject.toJSONString(v));
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return detail;
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        log.info("进入方法前执行.....");
    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        log.info("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp){
        log.info("方法异常时执行.....");
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp){
        log.info("方法最后执行.....");
    }
}
