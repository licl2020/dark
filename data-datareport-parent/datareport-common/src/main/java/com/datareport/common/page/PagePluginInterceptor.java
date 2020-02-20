package com.datareport.common.page;











import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.datareport.common.String.StringUtil;



@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PagePluginInterceptor implements Interceptor
{
    private static String dialect = "";
    
    private static String pageSqlId = ".*elect.*";
    
    public Object intercept(Invocation ivk)
        throws Throwable
    {
        if (ivk.getTarget() instanceof RoutingStatementHandler)
        {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler)getAttributeValue(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement)getAttributeValue(delegate, "mappedStatement");
            BoundSql boundSql1 = delegate.getBoundSql();
            Object parameterObject1 = boundSql1.getParameterObject();
            boolean f = true;
            @SuppressWarnings("rawtypes")
			Map<String, Class> pageField1 = getFieldNames(parameterObject1.getClass());
            Page page1 = null;
           
            if (pageField1.containsKey("page"))
            {
                page1 = (Page)getAttributeValue(parameterObject1, "page");
                if (page1 == null)
                {
                    f = false;
                }
            }
            if (f)
            {
                // 重新设定编码集
                // ((Connection)
                // ivk.getArgs()[0]).prepareStatement("set names 'utf8mb4'").executeQuery();
                // 补充创建时间(create_time)字段
                if (mappedStatement.getId().indexOf("add") > -1 || mappedStatement.getId().indexOf("insert") > -1)
                {
                    Object parameterObject = delegate.getBoundSql().getParameterObject();
                    Object createTime =getAttributeValue(parameterObject, "create_time");
                    if (createTime == null)
                    {
                        setAttribute(parameterObject, "create_time", System.currentTimeMillis());
                    }
                }
                // 分页istPageLoad
                if (mappedStatement.getId().matches(pageSqlId))
                {
                    BoundSql boundSql = delegate.getBoundSql();
                    Object parameterObject = boundSql.getParameterObject();
                    if (parameterObject == null)
                    {
                        throw new NullPointerException("parameterObject尚未实例化！");
                        // System.out.println("parameterObject尚未实例化！");
                    }
                    else
                    {
                        Connection connection = (Connection)ivk.getArgs()[0];
                        String sql = boundSql.getSql();
                        String countSql = "";
                        if ("oracle".equals(dialect))
                        {
                            countSql = "select count(0) from (" + sql + ")  total";
                        }
                        else
                        {
                            countSql = "select count(0) from (" + sql + ") as total";
                        }
                        PreparedStatement countStmt = connection.prepareStatement(countSql);
                        BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
                        setParameters(countStmt, mappedStatement, countBS, parameterObject);
                        ResultSet rs = countStmt.executeQuery();
                        int count = 0;
                        if (rs.next())
                        {
                            count = rs.getInt(1);
                        }
                        rs.close();
                        countStmt.close();
                        Page page = null;
                        if (parameterObject instanceof Page)
                        {
                            page = (Page)parameterObject;
                            page.setEntityOrField(true);
                            page.setTotalResult(count);
                        }
                        else
                        {
                            @SuppressWarnings("rawtypes")
							Map<String, Class> pageField = getFieldNames(parameterObject.getClass());
                            if (pageField.containsKey("page"))
                            {
                                page = (Page)getAttributeValue(parameterObject, "page");
                                if (page == null)
                                {
                                    page = new Page();
                                }
                                page.setEntityOrField(false);
                                page.setTotalResult(count);
                               setAttribute(parameterObject, "page", page);
                            }
                            else
                            {
                                // throw new
                                // NoSuchFieldException(parameterObject.getClass().getName()+"不存  page 属性！");
                                System.out.println("无page属性");
                            }
                        }
                        String pageSql = generatePageSql(sql, page);
                        page.getMobileStr();
                        page.getPageStr();
                        //
                        // LoggerUtil.info(this.getClass(),"==================================");
                        // LoggerUtil.info(this.getClass(),pageSql);
                        // LoggerUtil.info(this.getClass(),"==================================");
                        //
                        setAttribute(boundSql, "sql", pageSql);
                    }
                }
            }
        }
        return ivk.proceed();
    }
    
    @SuppressWarnings("unchecked")
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
        throws SQLException
    {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null)
        {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++)
            {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT)
                {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null)
                    {
                        value = null;
                    }
                    else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
                    {
                        value = parameterObject;
                    }
                    else if (boundSql.hasAdditionalParameter(propertyName))
                    {
                        value = boundSql.getAdditionalParameter(propertyName);
                    }
                    else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName()))
                    {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null)
                        {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    }
                    else
                    {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null)
                    {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }
    
    private String generatePageSql(String sql, Page page)
    {
        if (page != null && !StringUtil.isEmpty(dialect) && page.getShowCount() > 0)
        {
            StringBuffer pageSql = new StringBuffer();
            if ("oracle".equals(dialect))
            {
            	pageSql.append("select * from ( select row_.*, rownum rownum_ from (");
                pageSql.append(sql);
                pageSql.append(") row_ ) where rownum_ <=");
                pageSql.append(page.getCurrentResult() + page.getShowCount());
                pageSql.append("and rownum_ >");
                pageSql.append(page.getCurrentResult());
            	
//            	pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
//              pageSql.append(sql);
//              pageSql.append(")   tmp_tb where ROWNUM<=");
//              pageSql.append(page.getCurrentResult() + page.getShowCount());
//              pageSql.append(") where row_id>");
//              pageSql.append(page.getCurrentResult());
            }
            else if ("mysql".equalsIgnoreCase(dialect))
            {
                pageSql.append("select * from (");
                pageSql.append(sql);
                pageSql.append(" limit ").append((page.getCurrentPage() - 1) * page.getShowCount()).append(",").append(page.getShowCount());
                pageSql.append(") as tmp_tb ");
            }
            return pageSql.toString();
        }
        else
        {
            return sql;
        }
    }
    
    @Override
    public Object plugin(Object arg0)
    {
        return Plugin.wrap(arg0, this);
    }
    
    @Override
    public void setProperties(Properties p)
    {
        dialect = p.getProperty("dialect");
        if (StringUtil.isEmpty(dialect))
        {
            try
            {
                throw new PropertyException("dialect property is not found!");
            }
            catch (PropertyException e)
            {
                e.printStackTrace();
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (StringUtil.isEmpty(pageSqlId))
        {
            try
            {
                throw new PropertyException("pageSqlId property is not found!");
            }
            catch (PropertyException e)
            {
                e.printStackTrace();
            }
        }
    }
    
	/**
	 * 获取类的所有属性与属性的类型
	 *
	 * @param clazz
	 *            类
	 * @return 该类的所有属性名与属性类型(包含父类属性)
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Class> getFieldNames(Class clazz)
	{
		Map<String, Class> attrMap = new HashMap<String, Class>();
		
		try
		{
			
			for (; !clazz.equals(Object.class); clazz = clazz.getSuperclass())
			{
				Field[] fs = clazz.getDeclaredFields();
				for (Field f : fs)
				{
					attrMap.put(f.getName(), f.getType());
				}
			}
			attrMap.remove("serialVersionUID");
		} catch (Exception e)
		{
		}
		return attrMap;
	}
	
	
	/**
	 * 从对象中取值
	 *
	 * @param obj
	 *            对象
	 * @param attrName
	 *            要取值的属性名
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public static Object getAttributeValue(Object obj, String attrName)
	{
		try
		{
//			 for(Class clazz=obj.getClass(); !clazz.equals(Object.class);
//			 clazz=clazz.getSuperclass()) {
//			 Field[] fs = clazz.getDeclaredFields();
//			 for(Field f : fs) {
//			 if(f.getName().equalsIgnoreCase(attrName)) {
//			 f.setAccessible(true);
//			 Object value = f.get(obj);
//			 f.setAccessible(false);
//			 return value;
//			 }
//			 }
//			 }

			Class clazz = obj.getClass();
			while (!clazz.equals(Object.class))
			{
				try
				{
					Field f = clazz.getDeclaredField(attrName);
					f.setAccessible(true);
					Object value = f.get(obj);
					f.setAccessible(false);
					return value;
				} catch (NoSuchFieldException e)
				{
					clazz = clazz.getSuperclass();
				}
			}

			return null;

		} catch (Exception e)
		{
		}
		return null;
	}
	
	/**
	 * 给对象的属性赋值
	 *
	 * @param obj
	 *            对象
	 * @param attrName
	 *            对象的属性名
	 * @param value
	 *            对象的属性值
	 */
	@SuppressWarnings("unchecked")
	public static void setAttribute(Object obj, String attrName, Object value)
	{
		try
		{
			// for(Class clazz=obj.getClass(); !clazz.equals(Object.class);
			// clazz=clazz.getSuperclass()) {
			// Field[] fs = clazz.getDeclaredFields();
			// for(Field f : fs) {
			// if(f.getName().equalsIgnoreCase(attrName)) {
			// f.setAccessible(true);
			// f.set(obj, parseToObject(value, f.getType()));
			// f.setAccessible(false);
			// return;
			// }
			// }
			// }

			Class clazz = obj.getClass();
			while (!clazz.equals(Object.class))
			{
				try
				{
					Field f = clazz.getDeclaredField(attrName);
					f.setAccessible(true);
					f.set(obj, parseToObject(value, f.getType()));
					f.setAccessible(false);
					return;
				} catch (NoSuchFieldException e)
				{
					clazz = clazz.getSuperclass();
				}
			}
		} catch (Exception e)
		{
		}
	}
	
	
	/***
	 * 转换类型
	 *
	 * @param value
	 *            字符串的值
	 * @param type
	 *            要转换的类型
	 * @return 转换后的值
	 */
	@SuppressWarnings("unchecked")
	public static <T> Object parseToObject(Object value, Class<T> type)
	{
		if (value == null || type == String.class)
		{
			return value;
		}
		if (type == Character.class || type == char.class)
		{
			char[] chars = value.toString().toCharArray();
			return chars.length > 0 ? chars.length > 1 ? chars : chars[0] : Character.MIN_VALUE;
		}
		if (type == Boolean.class || type == boolean.class)
		{
			return Boolean.parseBoolean(value.toString());
		}
		// 处理boolean值转换
		Object oldValue = value;
		value = value.toString().equalsIgnoreCase("true") ? 1 : value.toString().equalsIgnoreCase("false") ? 0 : value;
		if (type == Long.class || type == long.class)
		{
			return Long.parseLong(value.toString());
		}
		if (type == BigDecimal.class)
		{
			return new BigDecimal(value.toString());
		}
		if (type == Integer.class || type == int.class)
		{
			return Integer.parseInt(value.toString());
		}
		if (type == Double.class || type == double.class)
		{
			return Double.parseDouble(value.toString());
		}
		if (type == Float.class || type == float.class)
		{
			return Float.parseFloat(value.toString());
		}
		if (type == Byte.class || type == byte.class)
		{
			return Byte.parseByte(value.toString());
		}
		if (type == Short.class || type == short.class)
		{
			return Short.parseShort(value.toString());
		}
		return (T) oldValue;
	}


}
