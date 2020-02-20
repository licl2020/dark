//package com.datareport.config.shiro;
//
//
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.servlet.Filter;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
//import org.apache.shiro.mgt.DefaultSubjectDAO;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//import com.datareport.common.properties.PropertiesFileUtil;
//import com.datareport.config.filter.JwtFilter;
//
///**
// * 描述：
// *
// * @author caojing
// * @create 2019-01-27-13:38
// */
//@Configuration
//public class ShiroJwtConfig {
//	
//	public static Properties cu = PropertiesFileUtil.getProperties("application.properties");	
//
//    
//    /***
//     * 授权所用配置
//     *
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//    	
//    	ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//    	
//    	 // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        //设置我们自定义的JWT过滤器
//        filterMap.put("jwt", jwtFilterBean());
//        shiroFilterFactoryBean.setFilters(filterMap);
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        //设置登陆方法
//        shiroFilterFactoryBean.setLoginUrl("/Login");
//        //登陆成功后
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        // 设置无权限时跳转的 url;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized/无权限");
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // 放行不需要jwt认证的接口
//        filterChainDefinitionMap.put("/cssandjs/**", "anon");
//        filterChainDefinitionMap.put("/LoginVerify", "anon");
//        filterChainDefinitionMap.put("/Login", "anon");
//        filterChainDefinitionMap.put("/kickout", "anon");
//        filterChainDefinitionMap.put("/prompt", "anon");
//        // 所有请求通过我们自己的JWT Filter ,
//        filterChainDefinitionMap.put("/**", "jwt");
//        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//
//    }
//
//
//	@Bean("jwtFilter")
//	public JwtFilter jwtFilterBean() {
//		return new JwtFilter();
//	}
//    /**
//     * 安全管理器
//     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错，直接引用shiro-spring则不报错
//     *
//     * @return
//     */
//    @Bean
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        //设置自定义realm.
//        securityManager.setRealm(customRealm());
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);
//		securityManager.setCacheManager(new CustomCacheManager());
//        SecurityUtils.setSecurityManager(securityManager);
//        return securityManager;
//    }
//    
//    
//
//    
//    @Bean(name = "credentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        // 散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        // 散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setHashIterations(2);
//        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return hashedCredentialsMatcher;
//    }
//
//    @Bean
//    public CustomRealm customRealm() {
//        CustomRealm customRealm = new CustomRealm();
//        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
////        customRealm.setCacheManager(ehCacheManager());
//        return customRealm;
//    }
//
//    
//
//    
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//    
// /**
//     * *
//     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
//     * *
//     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
//     * * @return
//     */
//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }
//    
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
//        return authorizationAttributeSourceAdvisor;
//    }
//    
//
//}
//
