package com.datareport.config.chcache;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class ObjectUtil {

	private static Logger log = Logger.getLogger(ObjectUtil.class);

	/**
	 * 根据属性名获取属性值
	 */
	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			try {
				String getter = "get" + fieldName;
				Method method = o.getClass().getMethod(getter, new Class[] {});
				Object value = method.invoke(o, new Object[] {});
				return value;
			} catch (Exception e2) {
				return null;
			}

		}
	}

	/**
	 * 获取属性名数组
	 */
	private String[] getFiledName(Object o) {
		try {
			Class userCla = (Class) o.getClass();
			Field[] fields = getFields(userCla, true);
			String[] fieldNames = new String[fields.length];
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < fields.length; i++) {
				String nam = fields[i].getName();
				if (isBean(fields[i].getType().toString())) {
					ParamsReflect.getFieldValue(o, String.valueOf(fields[i].getName()));
				} else {
					// ParamsReflect.getBeanFieldValue(o,fields[i].getType());
				}

				if (nam.equals("page") || nam.equals("serialVersionUID") || nam.equals("cu")) {
					continue;
				}
				tmp.add(nam);
				// fieldNames[i] =nam;
			}
			fieldNames = tmp.toArray(new String[0]);
			return fieldNames;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	/**
	 * 获取属性名数组
	 */
	public static String getKey(Object o) {
		String sb = "cs";
		try {
			Class userCla = (Class) o.getClass();
			Field[] fields = getFields(userCla, true);
			for (int i = 0; i < fields.length; i++) {
				String nam = fields[i].getName();
				Object vaue = null;
				if (nam.equals("serialVersionUID") || nam.equals("cu")) {
					continue;
				}
				if (isBean(fields[i].getType().toString())) {
					Object vaue2 = ParamsReflect.getFieldValue(o, String.valueOf(fields[i].getName()));
					if (vaue2 != null) {
						sb += fields[i].getName() + ":" + vaue2 + ";";
					}

				} else {

					String firstLetter = nam.substring(0, 1).toUpperCase();
					String getter = "get" + firstLetter + nam.substring(1);
					Method method = o.getClass().getMethod(getter, new Class[] {});
					Object value = method.invoke(o, new Object[] {});
					if (value != null) {
						Field[] fs = ObjectUtil.getFields(value.getClass(), true);
						Class<?> clas = value.getClass();
						for (int j = 0; j < fs.length; j++) {
							Field f2 = null;
							Object fieldValue = null;
							f2 = fs[j];
							if (isBean(fs[j].getType().toString())) {
								if (f2.getName().equals("serialVersionUID") || f2.getName().equals("cu")) {
									continue;
								}
								Field f = null;
								try {
									f = clas.getDeclaredField(f2.getName());
								} catch (Exception e) {
									f = clas.getSuperclass().getDeclaredField(f2.getName());
								}
								f.setAccessible(true);
								fieldValue = f.get(value);
								if (fieldValue != null) {
									sb += f.getName() + ":" + fieldValue + ";";
								}

							}
						}
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String key = MD5Util.md5Encode(sb);
		sb = null;
		return key;

	}

	public static void main(String[] args) {
		String cs = " csdelState:1;o_id:1569293724260;guid:e2438115-dd27-441b-9747-0405c73496a7;companyId:1569293724260;dwmc:平原幼儿园;gxjgdm:331102550000;gxjgmc:丽水市莲都分局碧湖派出所;dwdz:丽水市莲都区碧湖镇平二村;fzrxm:郑寅音;fzrdh:13575378203;dwzt:1;tjsj:1569293724260;udf2:2;mosscode:21;torgan:com.fk.bean.TorganBean@333db729;parentId:son_001;parentc:2;card_type:2;isParent:false;publicKey:1n69ua6k;privateKey:ymcs2msr;IsPushOk:1;PushReturn:{\"code\":\"00000\",\"msg\":null,\"result\":\"\"};company:com.fk.bean.CompanyBean@18c3ff07;is_state:启用 ;oneLevelGaDm:330000000000;twoLevelGaDm:331100000000;threeLevelGaDm:331102000000;fourLevelGaDm:331102550000;showCount:10;totalPage:0;totalResult:0;currentPage:1;currentResult:0;entityOrField:false;params:;currentPage2:1;isNeedPageStr:true;";
		String cs1 = " csdelState:1;o_id:1569293724260;guid:e2438115-dd27-441b-9747-0405c73496a7;companyId:1569293724260;dwmc:平原幼儿园;gxjgdm:331102550000;gxjgmc:丽水市莲都分局碧湖派出所;dwdz:丽水市莲都区碧湖镇平二村;fzrxm:郑寅音;fzrdh:13575378203;dwzt:1;tjsj:1569293724260;udf2:2;mosscode:21;torgan:com.fk.bean.TorganBean@18bee682;parentId:son_001;parentc:2;card_type:2;isParent:false;publicKey:1n69ua6k;privateKey:ymcs2msr;IsPushOk:1;PushReturn:{\"code\":\"00000\",\"msg\":null,\"result\":\"\"};company:com.fk.bean.CompanyBean@1d0f6d7f;is_state:启用 ;oneLevelGaDm:330000000000;twoLevelGaDm:331100000000;threeLevelGaDm:331102000000;fourLevelGaDm:331102550000;showCount:10;totalPage:0;totalResult:0;currentPage:1;currentResult:0;entityOrField:false;params:;currentPage2:1;isNeedPageStr:true;";
		System.out.println(MD5Util.md5Encode(cs));
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 */
	private List getFiledsInfo(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		List list = new ArrayList();
		Map infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 */
	public Object[] getFiledValues(Object o) {
		String[] fieldNames = this.getFiledName(o);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			Object valuez = this.getFieldValueByName(fieldNames[i], o);
			if (valuez != null) {
				value[i] = valuez;
			}

		}
		return value;
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 */
	public String getFiledValueString(Object o) {
		StringBuilder sb = new StringBuilder();
		String[] fieldNames = this.getFiledName(o);
//        Object[] value = new Object[fieldNames.length];
//        for (int i = 0; i < fieldNames.length; i++) {
//            Object valuez=this.getFieldValueByName(fieldNames[i], o);
//            if(valuez !=null && valuez != "") {
//            	sb.append(fieldNames[i]+":"+valuez+";");
//            }
//            
//        }
		return sb.toString();
	}

	public static Boolean isBean(String object1) {
		boolean f = true;
		if (object1.toUpperCase().contains("INT")) {

		} else if (object1.toUpperCase().contains("INTEGER")) {

		} else if (object1.toUpperCase().contains("LONG")) {
		} else if (object1.toUpperCase().contains("SHORT")) {
		} else if (object1.toUpperCase().contains("BOOLEAN")) {
		} else if (object1.toUpperCase().contains("BYTE")) {
		} else if (object1.toUpperCase().contains("CHARACTER")) {
		} else if (object1.toUpperCase().contains("DOUBLE")) {
		} else if (object1.toUpperCase().contains("FLOAT")) {
		} else if (object1.toUpperCase().contains("STRING")) {
		} else {
			f = false;
		}
		return f;
	}

	/**
	 * 获取所有的成员变量,包括父类
	 * 
	 * @param clazz
	 * @param superClass 是否包括父类
	 * @return
	 * @throws Exception
	 */
	public static <T> Field[] getFields(Class<T> clazz, boolean superClass) throws Exception {

		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = null;
		if (superClass) {
			Class superClazz = clazz.getSuperclass();
			if (superClazz != null) {
				superFields = superClazz.getDeclaredFields();
			}
		}

		Field[] allFields = null;

		if (superFields == null || superFields.length == 0) {
			allFields = fields;
		} else {
			allFields = new Field[fields.length + superFields.length];
			for (int i = 0; i < fields.length; i++) {

				allFields[i] = fields[i];

			}
			for (int i = 0; i < superFields.length; i++) {
				allFields[fields.length + i] = superFields[i];
			}
		}

		return allFields;
	}

	/**
	 * 获取自定义子属性对象，传入指定对象名，在当前对象中找到子对象，再通过field找到子属性
	 * 
	 * @param obj         父对象名称
	 * @param claz        父对象class反射
	 * @param customClass 自定义判断的子对象类型
	 * @param field       属性名
	 * @return Object
	 */
	public static Object getCustomChildObj(Object obj, Class<?> claz, Class<?> customClass, String field) {
		Field[] fs = claz.getDeclaredFields();
		Field f = null;
		for (int i = 0; i < fs.length; i++) {
			f = fs[i];
		}
		return null;
	}

	/**
	 * 通过找到的子对象，获取到当前的属性，传入所需的属性名，得到属性值
	 * 
	 * @param o     父对象
	 * @param f     父对象下的子对象的Field对象
	 * @param field 所需要获取的属性名
	 * @return Object
	 */
	public static Object getChildObjectParam(Object o, Field f, String field) {
		f.setAccessible(true);
		Object obj = null;
		Class<?> childClass = null;
		Field childF = null;
		Object fieldValue = null;
		try {
			obj = f.get(o);
			childClass = obj.getClass();
			childF = childClass.getDeclaredField(field);
			childF.setAccessible(true);
			fieldValue = childF.get(obj);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fieldValue;
	}

}