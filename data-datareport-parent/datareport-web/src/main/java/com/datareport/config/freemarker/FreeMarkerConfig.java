package com.datareport.config.freemarker;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.datareport.common.properties.PropertiesFileUtil;

@Configuration
public class FreeMarkerConfig {

	@Autowired
	private freemarker.template.Configuration configuration;
	
	@Autowired
	protected  FtlCheckPermission checkPermission;
	
	private static Properties cu = PropertiesFileUtil.getProperties("application.properties");	

				// Spring 初始化的时候加载配置
		 @PostConstruct
	     public void setConfigure() throws Exception {
				// 加载html的资源路径
//				configuration.setSharedVariable("projectName", projectName);
				configuration.setSharedVariable("basePath", cu.getProperty("basepath"));
				configuration.setSharedVariable("checkPermission", checkPermission);
    }
}
