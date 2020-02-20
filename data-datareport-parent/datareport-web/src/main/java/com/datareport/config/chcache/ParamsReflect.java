package com.datareport.config.chcache;


import java.lang.reflect.Field;


/**
 * 属性（对象）值反射获取工具类
 * @author 李成龙
 */
public class ParamsReflect {
	
	public static final String FILE_FIELD = "nameB,";
 
	/**
	 * 获取当前对象对应字段的属性（对象）
	 * 声明，需要注意在NoSuchFieldException异常捕捉中捕获自己需要的属性字段进行拦截，告诉当查询这些属性名的时候，指定是查找的哪些对象，如果不告诉它，它是不知道的
	 * @param obj	当前对象
	 * @param field	需要获取的属性名，可以是当前对象中的属性名， 也可以是当前对象中的对象的属性名
	 * @return	Object  当前对象指定属性值
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Object getFieldValue(Object obj, String field) throws Exception {
		Class<?> claz = obj.getClass();
		Field f = null;
		Object fieldValue = null;
		try {
			f = claz.getDeclaredField(field);
			
		} catch (Exception e) {
			f = claz.getSuperclass().getDeclaredField(field);
		} 
		f.setAccessible(true);
		fieldValue = f.get(obj);
		return fieldValue;
	}
	
	
	/**
	 * 获取当前对象对应字段的属性（对象）
	 * 声明，需要注意在NoSuchFieldException异常捕捉中捕获自己需要的属性字段进行拦截，告诉当查询这些属性名的时候，指定是查找的哪些对象，如果不告诉它，它是不知道的
	 * @param obj	当前对象
	 * @param field	需要获取的属性名，可以是当前对象中的属性名， 也可以是当前对象中的对象的属性名
	 * @return	Object  当前对象指定属性值
	 * @throws Exception 
	 */
	public static String getBeanFieldValue(Object obj) throws Exception {
		Field f = null;
		StringBuilder sb = new StringBuilder();
		try {
			
			 Field[] fs =ObjectUtil.getFields(obj.getClass(),true);
				Field f2 = null;
				Class<?> clas=obj.getClass();
				for (int i = 0; i < fs.length; i++) {
					Object fieldValue = null;
					f2 = fs[i];
					if(f2.getName().equals("serialVersionUID") || f2.getName().equals("cu")) {
						continue;
					}
					
					try {
						f = clas.getDeclaredField(f2.getName());
					} catch (Exception e) {
						f = clas.getSuperclass().getDeclaredField(f2.getName());
					}
					f.setAccessible(true);
					fieldValue = f.get(obj);
					if(fieldValue != null) {
						sb.append(f.getName()+":"+fieldValue+";");
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String key=sb.toString();
		key=null;
		return  key;
	}
	
}