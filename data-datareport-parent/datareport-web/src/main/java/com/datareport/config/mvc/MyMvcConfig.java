package com.datareport.config.mvc;



import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.datareport.interceptor.LoginInterceptor;



@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport  {
	
	
//	public static Properties cu = PropertiesFileUtil.getProperties("configure.properties");	
	
    
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    
    
	
	@Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //setUseSuffixPatternMatch 后缀模式匹配
        configurer.setUseSuffixPatternMatch(true);
        //setUseTrailingSlashMatch 自动后缀路径模式匹配
        configurer.setUseTrailingSlashMatch(true);
    }
	
		@Bean
		public FastJsonConfig fastJsonConfig() {
			FastJsonConfig fastJsonConfig = new FastJsonConfig();
			SerializerFeature writeMapNullValue = SerializerFeature.WriteMapNullValue;
			SerializerFeature WriteNullStringAsEmpty = SerializerFeature.WriteNullStringAsEmpty;
			SerializerFeature WriteNullNumberAsZero = SerializerFeature.WriteNullNumberAsZero;
			SerializerFeature WriteNullListAsEmpty = SerializerFeature.WriteNullListAsEmpty;
			fastJsonConfig.setSerializerFeatures(writeMapNullValue, WriteNullStringAsEmpty, 
					WriteNullNumberAsZero, WriteNullListAsEmpty);
			return fastJsonConfig;
		}
	 
		@Bean
		public HttpMessageConverters fastJsonHttpMessageConverters(
				@Qualifier("fastJsonConfig") FastJsonConfig fastJsonConfig) {
			FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
			fastConverter.setFastJsonConfig(fastJsonConfig);
			HttpMessageConverter<?> converter = fastConverter;
			return new HttpMessageConverters(converter);
		}

    


	   /**
     * 添加类型转换器和格式化器
     * @author fxbin
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new DateFormatter());
    }
    
    
    
    /**
     * 跨域支持
     * @author fxbin
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }
    
    
    /**
     * 配置消息转换器--这里我用的是alibaba 开源的 fastjson
     * @author fxbin
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //5.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
    }
	
//    @Bean
//    public LonginInterceptor longinInterceptor(){
//        return new LonginInterceptor();
//    }
//    
//    @Bean
//    public PathInterceptor pathInterceptor(){
//        return new PathInterceptor();
//    }

//    
//    @Bean(name="multipartResolver")
// 	public MultipartResolver multipartResolver(){
// 		return new CommonsMultipartResolver();
// 	}
//     
    
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("Login");
            registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
            super.addViewControllers(registry);
        }


	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/**")
	            .addResourceLocations("classpath:/META-INF/resources/")
	            .addResourceLocations("classpath:/resources/")
	            .addResourceLocations("classpath:/static/")
	            .addResourceLocations("classpath:/public/");
//	    registry.addResourceHandler("/"+cu.getProperty("BACKUPS_VISIT_TAG")+"/**").addResourceLocations("file:/"+cu.getProperty("BACKUPS_SAVE_URL"));
	    super.addResourceHandlers(registry);
	}
	
	  @Bean
	    public Validator validator(){
	        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
	                .configure()
	                .addProperty( "hibernate.validator.fail_fast", "false" )
	                .buildValidatorFactory();
	        Validator validator = validatorFactory.getValidator();

	        return validator;
	    }
	
//	@Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        //setUseSuffixPatternMatch 后缀模式匹配
//        configurer.setUseSuffixPatternMatch(true);
//        //setUseTrailingSlashMatch 自动后缀路径模式匹配
//        configurer.setUseTrailingSlashMatch(true);
//    }

    //配置默认的核心控制器，这里的处理的静态资源是放置在web根目录
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    //拦截配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//addPathPatterns 添加拦截
    	//excludePathPatterns 忽略拦截
    	registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/cssandjs/**","/LoginVerify","/prompt","/Login","/kickout","/exit","/errors","/autherror","/error","/modular/childs");
    	super.addInterceptors(registry);
    }

    //消息序列化和反序列化，主要用来处理处理request 和response 里的数据的
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }



    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
    
    

}