package com.datareport.config.freemarker;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
@Configuration
public class FtlCheckPermission implements TemplateMethodModelEx {


	@Override
	public Object exec(List args) throws TemplateModelException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String rescCode = args.get(0).toString();
		return "狗死了";
	}
	}
