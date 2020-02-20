package com.datareport.common.json;








import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Descrption:反射映射处理类
 * 
 * @author Administrator Date 2017年5月10日 下午3:59:12 ====================================================== Modify:
 *         author: Date 2017年5月10日 下午3:59:12
 */
public class ReflectUtil
{
    /**
     * Description：获取clazz的属性集合 author：Administrator Date 2017年5月10日 下午4:24:10
     * 
     * @param clazz
     * @return
     */
    public static Field[] getField(Class<?> clazz)
    {
        return clazz.getDeclaredFields();
    }
    
    /**
     * Description：根据字段名获取字段值 author：Administrator Date 2017年5月10日 下午4:42:38
     * 
     * @param thisClass
     * @param filedName
     * @return
     */
    public static Object getFiledValue(Object thisClass, String filedName)
    {
        Object value = null;
        try
        {
            String methodName = filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
            Method method = thisClass.getClass().getMethod("get" + methodName);
            value = method.invoke(thisClass);
        }
        catch (Exception e)
        {
            if (!filedName.contains("$"))
                e.printStackTrace();
        }
        return value;
    }
    
    /**
     * Description：根据字段名设置字段值 author：Administrator Date 2017年5月10日 下午4:42:38
     * 
     * @param thisClass
     * @param filedName
     * @return
     */
    public static void setFiledValue(Object thisClass, String fieldName, Object fieldValue, Class<?> clazz)
    {
        try
        {
            String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            invokeMethod(thisClass, "set" + methodName, new Class[] {clazz}, new Object[] {fieldValue});
        }
        catch (Exception e)
        {
            if (!fieldName.contains("$"))
                e.printStackTrace();
        }
    }
    
    /**
     * <code></code> Description:获取对象字段以及相对应的属性值的字符串，最终值，相当于重写了toString()方法 这是为了处理那些没有重写toString()的model而特殊处理
     * 
     * @param <T>
     * @author：kucs Date 2017年5月12日 下午4:41:48
     * @param obj
     * @return
     */
    public static <T> String getObjectToString(T obj)
    {
        if (obj == null)
            return null;
        String string = obj.toString();
        if (string.indexOf(obj.getClass().getName() + "@") > -1)
        {
            Field[] field = getField(obj.getClass());
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < field.length; i++)
            {
                Field f = field[i];
                String name = f.getName();
                String value = String.valueOf(getFiledValue(obj, name));
                sb.append(name);
                sb.append("=" + value);
                if (i != field.length - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        }
        else
        {
            return string;
        }
    }
    
    /**
     * <code></code> Description:获取对象字段以及相对应的属性值的字符串，最终值，相当于重写了toString()方法 这是为了处理那些没有重写toString()的model而特殊处理
     * 
     * @param <T>
     * @author：kucs Date 2017年5月12日 下午4:41:48
     * @param obj
     * @return
     */
    public static <T> String getObjectToString(List<T> list)
    {
        if (null == list || list.size() == 0)
            return null;
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < list.size(); i++)
        {
            sb.append(getObjectToString(list.get(i)));
            if (i != (list.size() - 1))
                sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Description：根据全限定类名，创建类对象 author：Administrator Date 2017年5月11日 上午10:49:26
     * 
     * @param clsName
     * @return
     * @throws Exception
     */
    public static Object getInstance(String clsName)
        throws Exception
    {
        try
        {
            Class<?> cls = Class.forName(clsName);
            return cls.newInstance();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @Description：根据全限定类名，创建类对象，创建对象异常则返回默认对象
     * @author kucs
     * @Date 2017年6月22日下午4:29:21
     * @param clsName
     * @param defaultObj
     * @return
     */
    public static Object getInstance(String clsName, Object defaultObj)
    {
        try
        {
            return getInstance(clsName);
        }
        catch (Exception e)
        {
            return defaultObj;
        }
    }
    
    /**
     * @Description：根据全限定类名，创建类对象，设置参数名和属性值。
     * @author kucs
     * @Date 2017年6月22日下午4:29:50
     * @param clsName
     * @param argType
     * @param argValues
     * @return
     * @throws Exception
     */
    public static Object getInstance(String clsName, Class[] argType, Object[] argValues)
        throws Exception
    {
        Class cls = Class.forName(clsName);
        return cls.getDeclaredConstructor(argType).newInstance(argValues);
    }
    
    /**
     * @Description：反射调用方法
     * @author kucs
     * @Date 2017年6月22日下午4:30:15
     * @param obj
     * @param methodName
     * @param argTypes
     * @param argValues
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Object obj, String methodName, Class[] argTypes, Object[] argValues)
        throws Exception
    {
        StringBuffer bf = new StringBuffer();
        try
        {
            bf.append("Class[" + obj.getClass().getName() + "],");
            bf.append("Method[" + methodName + "],");
            bf.append("ArgsType[");
            if (argTypes != null)
            {
                for (Class c : argTypes)
                {
                    bf.append(c.getName());
                    bf.append(",");
                }
            }
            bf.append("]");
            bf.append("ArgsValue[");
            if (argValues != null)
            {
                for (Object o : argValues)
                {
                    bf.append(o.toString());
                    bf.append(",");
                }
            }
            bf.append("]");
            Method method = obj.getClass().getMethod(methodName, argTypes);
            return method.invoke(obj, argValues);
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        catch (NoSuchMethodException e)
        {
            throw e;
        }
        catch (IllegalAccessException e)
        {
            throw e;
        }
        catch (InvocationTargetException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    /**
     * @Description：将List<Object>封装成指定的Bean 可以作为Excel读取一行的所有单元格内容后进一步处理
     * @author kucs
     * @Date 2017年5月15日上午10:42:16
     * @param @param list
     * @param @param clsName
     * @param @return
     * @return Object
     * @throws
     */
    public static Object fillBean(List<Object> list, String clsName)
    {
        Object object = null;
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            object = ReflectUtil.getInstance(clsName);
            Field[] field = ReflectUtil.getField(object.getClass());
            int i = 0;
            if (field[0].getName().equals("serialVersionUID"))
            {
                i = 1;
            }
            for (; i < field.length; i++)
            {
                String type = field[i].getType().getName();
                // Object obj_type = ReflectUtil.getInstance(type);
                Object obj_value = list.get(i - 1);
                if (type.equals("java.lang.Integer"))
                {
                    obj_value = Integer.parseInt(String.valueOf(obj_value));
                }
                else if (type.equals("java.lang.Float"))
                {
                    obj_value = Float.parseFloat(String.valueOf(obj_value));
                }
                else if (type.equals("java.lang.Long"))
                {
                    obj_value = Long.parseLong(String.valueOf(obj_value));
                }
                map.put(field[i].getName(), obj_value);
            }
            JsonUtils.fillBean(object, JsonUtils.fromObjectToJsonObject(map));
        }
        catch (Exception e)
        {
        }
        return object;
    }
}
