package com.datareport.config.redis;





import javax.annotation.PostConstruct;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datareport.common.json.JsonUtils;



@Component
public class RedisClientExecute {

	 @Autowired
	 private   RedisClient redisService;
	 
	  private static RedisClientExecute redisexecute; 
	     

		@PostConstruct 
		  public void init() { 
			redisexecute = this; 
			redisexecute.redisService = redisService; 
		}
		
		/**
		 * 判断hash表中是否有该项的值
		 * 
		 * @param key
		 *            键 不能为null
		 * @param item
		 *            项 不能为null
		 * @return true 存在 false不存在
		 */
		public static boolean hHasKey(String key, String item) {
			return  redisexecute.redisService.hHasKey(key, item);
		}
		
	    
	    /** 
	     * 删除hash表中的值 
	     * @param key 键 不能为null 
	     * @param item 项 可以使多个 不能为null 
	     */  
	    public  static void hdel(String key, Object... item){    
	    	redisexecute.redisService.hdel(key,item);  
	    }   
	    
	    /** 
	     * 删除hash表中的值 
	     * @param key 键 不能为null 
	     * @param item 项 可以使多个 不能为null 
	     */  
	    public  static void hset(String key,String item,Object value,long time){   
	    	redisexecute.redisService.hset(key,item,value,time);  
	    }
	    
	    public  static Object hget(String key,String item){   
	    	Object s=redisexecute.redisService.hget(key,item);
	         return JsonUtils.jsonToBean(s.toString(), SimpleAuthorizationInfo.class);
	    }
	    
	    
	    
		
		
}
