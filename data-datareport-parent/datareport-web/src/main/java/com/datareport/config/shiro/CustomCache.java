package com.datareport.config.shiro;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.datareport.common.json.JsonUtils;
import com.datareport.common.properties.PropertiesFileUtil;
import com.datareport.config.jwt.JwtUtil;
import com.datareport.config.jwt.RedisConstant;
import com.datareport.config.redis.RedisClientExecute;


/**
 * 重写Shiro的Cache保存读取
 * 
 * @author lwx
 */
public class CustomCache<K, V> implements Cache<K, V> {
	 
		public static Properties cu = PropertiesFileUtil.getProperties("application.properties");

	/**
	 * 缓存的key名称获取为shiro:cache:account
	 * 
	 * @param key
	 * @return java.lang.String
	 * @author Wang926454
	 * @date 2018/9/4 18:33
	 */
	private String getKey(Object key) {
		return JwtUtil.getClaim(key.toString(), RedisConstant.USER_ID);
	}

	/**
	 * 获取缓存
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object get(Object key) throws CacheException {
		if (!RedisClientExecute.hHasKey(RedisConstant.PREFIX_SHIRO_CACHE,this.getKey(key))) {
			return null;
		}
		return RedisClientExecute.hget(RedisConstant.PREFIX_SHIRO_CACHE,this.getKey(key));
	}

	/**
	 * 保存缓存
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) throws CacheException {
		RedisClientExecute.hset(RedisConstant.PREFIX_SHIRO_CACHE,this.getKey(key).toString(),JsonUtils.objectToJson(value),Long.valueOf(cu.getProperty("shiroCacheExpireTime")));
		return "";
	}

	/**
	 * 移除缓存
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object remove(Object key) throws CacheException {
		if (!RedisClientExecute.hHasKey(RedisConstant.PREFIX_SHIRO_CACHE,this.getKey(key))) {
			return null;
		}
		RedisClientExecute.hdel(RedisConstant.PREFIX_SHIRO_CACHE,this.getKey(key));
		return null;
	}

	/**
	 * 清空所有缓存
	 */
	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub

	}

	/**
	 * 缓存的个数
	 */
	@Override
	public Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取所有的key
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 获取所有的value
	 */
	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}
}
