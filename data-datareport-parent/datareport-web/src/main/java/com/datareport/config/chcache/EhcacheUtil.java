package com.datareport.config.chcache;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.datareport.common.json.JsonUtils;


@Component
public class EhcacheUtil {

	@Autowired
	private CacheManager cacheManager;
	
	/**
	 * Map判断是否包含数据
	 * 
	 * @param key
	 * @return String
	 */
	
	/**
	 * Map判断是否包含数据
	 * 
	 * @param key
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public boolean containsKey(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		Map<String, String> map = cache.get(cacheName, HashMap.class);
		if (map == null || map.size()==0) {
			return false;
		}
		return map.containsKey(key);
	}


	
	/**
	 * 根据key获取数据
	 * @param  key 取值key  map_key redis队列名
	 * @param  dbid 数据库索引
	 * @return
	 * @throws Exception
	 */
	public  <T>T getbean(String cacheName, String key,Class<T> t){
		String json = get(cacheName,key);
		T t1 = null;
		try {
			t1 = t.newInstance();
			if(!StringUtils.isEmpty(json)){
				t1 = JsonUtils.jsonToBean(json, t);
			}else{
				 t1 = null;
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			 t1 = null;
		}
		return t1;
	}
	

	
	  /**
		 * 获取list
		 * @param  key 取值key  map_key redis队列名
		 * @param  dbid 数据库索引
		 * @return
		 * @throws Exception
		 */
		public  <T>List<T> getList(String cacheName, String key,Class<T> t){
			String json = "";
			 List<T> list = new ArrayList<T>();
			try {
					json = get(cacheName,key);
					   if(!StringUtils.isEmpty(json)){
						   list =JsonUtils.jsonToList(json, t);
				           }
			} catch (Exception e) {
			}
			
			return list;
		}
	
	/**
	 * Map获取数据
	 * 
	 * @param key
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String get(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		Map<String, String> map = cache.get(cacheName, HashMap.class);
		return map.get(key);
	}

	
	
	
	/**
	 * Map添加数据
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public void put(String cacheName, String key, String value) {
		Cache cache = cacheManager.getCache(cacheName);
		Map<String, String> map = cache.get(cacheName, HashMap.class);
		if (map == null || map.size()==0) {
			map = new HashMap<>();
		}
		map.put(key, value);
		cache.put(cacheName, map);
	}
	
	/**
	 * 清空数据
	 * 
	 * @param cacheName
	 */
	public void removeCache(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}
	
}
