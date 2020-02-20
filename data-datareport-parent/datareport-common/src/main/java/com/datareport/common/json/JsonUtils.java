package com.datareport.common.json;




import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
 
/**
 * 
 * @Title: JsonUtils.java
 * @Package com.lee.utils
 * @Description: 自定义响应结构, 转换类
 * Copyright: Copyright (c) 2016
 * Company:Nathan.Lee.Salvatore
 * 
 * @author leechenxiang
 * @date 2016年4月29日 下午11:05:03
 * @version V1.0
 */
public class JsonUtils {
 
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
 
    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
    		MAPPER.setSerializationInclusion(Include.NON_EMPTY);  
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList2(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    
    /** 将JSON字符串转换为map */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json)
    {
        Map<String, Object> map = (Map<String, Object>)JSONObject.parse(json);
        return map;
    }
    
    /** 将obj转换成map */
    public static Map<String, Object> objToMap(Object obj)
    {
        return jsonToMap(objToJson(obj));
    }
    
    /** 将map转换成obj */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass)
    {
        // return jsonToMap(objToJson(obj));
        return jsonToBean(objToJson(map), beanClass);
    }
    
    /** 字符串转json */
    public static String objToJson(Object obj)
    {
        return JSONObject.toJSONString(obj);
    }
    
    /**
     * List<T> 转 json 保存到数据库
     */
    public static <T> String listToJson(List<T> ts)
    {
    	
        String jsons = JSON.toJSONString(ts);
        return jsons;
    }
    
    /**
     * OBJECT 转 对象 
     */
    public static <T> T ObjectBeanToJson(Object object,Class<T> clazz)
    {
		  net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(object);
        return (T)net.sf.json.JSONObject.toBean(json,clazz);
    }
    
    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz)
    {
        @SuppressWarnings("unchecked")
        List<T> ts = (List<T>)JSONArray.parseArray(jsonString, clazz);
        return ts;
    }
    
    /** 将json字符串转换为对象 */
    public static <T> T jsonToBean(String jsonStr, Class<T> beanClass)
    {
        return (T)JSONObject.parseObject(jsonStr, beanClass);
    }
    
    /** 测试JSON格式是否正常 */
    public static boolean isJson(String json)
    {
        try
        {
            jsonToMap(json);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * 用JSON字符串生成指定的bean实例
     * 
     * @param jsonStr JSON格式字符串
     * @param beanClass 目标bean的Class
     * @return 生成的bean实例
     * @throws Exception
     */
    public static Object formBean(String jsonStr, Class<?> beanClass)
        throws Exception
    {
        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(jsonStr);
        return formBean(jsonObj, beanClass);
    }
    
    /**
     * 用JSON对象生成指定的bean实例
     * 
     * @param jsonObj 包含数据的JSON对象
     * @param beanClass 目标bean的Class
     * @return 生成的bean实例
     * @throws Exception
     */
    public static Object formBean(net.sf.json.JSONObject jsonObj, Class<?> beanClass)
        throws Exception
    {
        Object bean = null;
        Iterator keys = jsonObj.keys();
        if (keys.hasNext())
        {
            String key = (String)keys.next();
            if (beanClass.getName().endsWith("." + key))
            {
                bean = ReflectUtil.getInstance(beanClass.getName());
                net.sf.json.JSONObject value = (net.sf.json.JSONObject)jsonObj.get(key);
                fillBean(bean, value);
            }
            else
            {
                throw new IllegalArgumentException("提供的JSON对象无法解析为制定的Bean.");
            }
        }
        return bean;
    }
    
    /**
     * 用JSON对象填充给定的bean实例
     * 
     * @param bean 目标填充的bean实例
     * @param jsonObj 包含填充值的JSON对象
     * @throws Exception
     */
    public static void fillBean(Object bean, net.sf.json.JSONObject jsonObj)
        throws Exception
    {
        if (jsonObj.isNullObject() || jsonObj.isEmpty())
        {
            return;
        }
        Iterator keys = jsonObj.keys();
        while (keys.hasNext())
        {
            String key = (String)keys.next();
            Object value = jsonObj.get(key);
            if (value instanceof net.sf.json.JSONArray)
            {
                net.sf.json.JSONArray ja = (net.sf.json.JSONArray)value;
                if (value.toString().indexOf('{') == -1 && value.toString().indexOf('{') == -1)
                {
                    for (Object obj : ja)
                    {
                        ReflectUtil.invokeMethod(bean, "add" + key, new Class[] {String.class}, new Object[] {obj});
                    }
                }
                else
                {
                    String arrBeanClassName = makeClassName(bean, key);
                    for (Object obj : ja)
                    {
                        Object arrBean = ReflectUtil.getInstance(arrBeanClassName);
                        fillBean(arrBean, (net.sf.json.JSONObject)obj);
                        ReflectUtil.invokeMethod(bean, "add" + key, new Class[] {arrBean.getClass()}, new Object[] {arrBean});
                    }
                }
            }
            else if (value instanceof net.sf.json.JSONObject)
            {
                String subBeanClassName = makeClassName(bean, key);
                Object subBean = ReflectUtil.getInstance(subBeanClassName);
                fillBean(subBean, (net.sf.json.JSONObject)value);
                ReflectUtil.invokeMethod(bean, "set" + captureString(key), new Class[] {subBean.getClass()}, new Object[] {subBean});
            }
            else if (value instanceof String)
            {
                ReflectUtil.invokeMethod(bean, "set" + captureString(key), new Class[] {String.class}, new Object[] {value});
            }
            else if (value instanceof Integer)
            {
                try
                {
                    bean.getClass().getMethod("set" + captureString(key), new Class[] {Integer.class});
                    // String strVal = ((Integer) value).toString();
                    ReflectUtil.invokeMethod(bean, "set" + captureString(key), new Class[] {Integer.class}, new Object[] {value});
                }
                catch (NoSuchMethodException e)
                {
                    BigDecimal decVal = BigDecimal.valueOf(((Integer)value).longValue());
                    ReflectUtil.invokeMethod(bean, "set" + captureString(key), new Class[] {BigDecimal.class}, new Object[] {decVal});
                }
            }
            else
            {
                throw new Exception("不支持的数据格式:" + value.getClass().getName());
            }
        }
    }
    
    
    /**
     * @Description：字符串首字母大写
     * @author kucs
     * @Date 2017年5月15日上午10:24:54
     * @param @param str
     * @param @return
     * @return String
     * @throws
     */
    public static String captureString(String str)
    {
        if (null == str || "".equals(str) || str.trim().length() == 0)
            return null;
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
    
    
    /**
     * @Description：将POJO转换为JsonArray
     * @author kucs
     * @Date 2017年5月15日上午9:14:27
     * @param @param bean
     * @param @return
     * @return JSONObject
     * @throws
     */
    public static net.sf.json.JSONArray fromObjectToJSONArray(Object bean)
    {
        return net.sf.json.JSONArray.fromObject(bean);
    }
    
    /**
     * @param <T>
     * @Description：JsonArray转换为Bean
     * @author kucs
     * @Date 2017年5月15日上午9:14:27
     * @param @param bean
     * @param @return
     * @return net.sf.json.JSONObject
     * @throws
     */
    public static <T> List<T> fillBeanByJsonArray(net.sf.json.JSONArray jsonArray, Class<?> clazz)
    {
        Collection<?> collection = net.sf.json.JSONArray.toCollection(jsonArray, clazz);
        List<T> list = new ArrayList<T>();
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext())
        {
            @SuppressWarnings("unchecked")
            T t = (T)iterator.next();
            list.add(t);
        }
        return list;
    }
    
    /**
     * @Description：将POJO转换为JsonObject
     * @author kucs
     * @Date 2017年5月15日上午9:14:27
     * @param @param bean
     * @param @return
     * @return JSONObject
     * @throws
     */
    public static net.sf.json.JSONObject fromObjectToJsonObject(Object bean)
    {
        return net.sf.json.JSONObject.fromObject(bean);
    }
    
    /**
     * 将POJO的JavaBean转换为Json字符串
     * 
     * @param bean
     * @return
     */
    public static String formJsonStr(Object bean)
    {
        return fromObjectToJsonObject(bean).toString();
    }
    
    /**
     * 生成属性对象的ClassName
     * 
     * @param bean 目标获取的对象实例
     * @param key 属性对象的类名(不含package)
     * @return 含package完整类名
     */
    private static String makeClassName(Object bean, String key)
    {
        int idx = bean.getClass().getName().lastIndexOf(".");
        String newClassName = replaceSpan(bean.getClass().getName(), key, idx + 1, bean.getClass().getName().length());
        return newClassName;
    }
    /**
     * 替换字符串中的部分片段
     * 
     * @param input
     * @param replacement
     * @param start
     * @param end
     * @return
     */
    public static String replaceSpan(String input, String replacement, int start, int end)
    {
        StringBuilder sb = new StringBuilder(input);
        sb.replace(start, end, replacement);
        return sb.toString();}
    /**
     * 将对象转换成Json格式的字符串
     * 
     * @param obj 需要被转换的对象
     * @return json格式字符串
     */
    public static String toString(Object obj)
    {
        return net.sf.json.JSONObject.fromObject(obj).toString();
    }
    
    /**
     * 去掉生成的json串中某节点为null或者[]的情况
     * 
     */
    public static String repalceNodeOfNull(String jsonStr)
    {
        if (jsonStr.indexOf("null") != -1)
        {
            return jsonStr.replace("null", "\"\"");
        }
        else if (jsonStr.indexOf("[]") != -1)
        {
            return jsonStr.replace("[]", "\"\"");
        }
        else
        {
            return jsonStr;
        }
    }
    
    /**
     * 将JSONObjec对象转换成Map-List集合
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> JsonToMap(net.sf.json.JSONObject json)
    {
        Map<String, Object> columnValMap = new HashMap<String, Object>();
        Iterator jsonIterator = json.keys();
        
        while (jsonIterator.hasNext())
        {
            Object key = jsonIterator.next();
            Object JsonValObj = json.get((String)key);
            if (JsonValObj instanceof net.sf.json.JSONArray)
            {
                columnValMap.put((String)key, JsonToList((net.sf.json.JSONArray)JsonValObj));
            }
            else if (JsonValObj instanceof net.sf.json.JSONObject)
            {
                columnValMap.put((String)key, JsonToMap((net.sf.json.JSONObject)JsonValObj));
            }
            else
            {
                columnValMap.put((String)key, JsonValObj);
            }
        }
        return columnValMap;
    }
    
    /**
     * 将json形式的字符串转成Map对象
     * 
     * @param content
     * @return
     */
    public static Map StringToMap(String content)
    {
        Map map = new HashMap();
        if (content != null && !"".equals(content.trim()))
        {
            net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(content);
            map = JsonToMap(jsonObj);
        }
        return map;
    }
    
    /**
     * 将JSONArray对象转换成Map-List集合
     * 
     * @param jsonArr
     * @return
     */
    public static Object JsonToList(net.sf.json.JSONArray jsonArr)
    {
        List<Object> jsonObjList = new ArrayList<Object>();
        for (int i = 0; i < jsonArr.size(); i++)
        {
            Object obj = jsonArr.get(i);
            if (obj instanceof net.sf.json.JSONArray)
            {
                jsonObjList.add(JsonToList((net.sf.json.JSONArray)obj));
            }
            else if (obj instanceof net.sf.json.JSONObject)
            {
                jsonObjList.add(JsonToMap((net.sf.json.JSONObject)obj));
            }
            else
            {
                jsonObjList.add(obj);
            }
        }
        return jsonObjList;
    }
    
}