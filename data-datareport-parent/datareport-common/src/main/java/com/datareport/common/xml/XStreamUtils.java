package com.datareport.common.xml;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class XStreamUtils {

	/**
	 *   获取bean
	 * @param <T>
	 * @param xml
	 * @param t
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public static  <T>T getBean(String xml,Class<T> t){
		T t1 = null;
		try {
			XStream xstream = new XStream(new Xpp3Driver(new NoNameCoder()));
			xstream.autodetectAnnotations(true);
		    xstream.processAnnotations(t);
		    xstream.setupDefaultSecurity(xstream); // to be removed after 1.5
			xstream.allowTypesByWildcard(new String[] {
		                "com.report.bean.zdrk.**"
		        });
//			Class<?>[] classes = new Class[] {t};
//			xstream.allowTypes(classes);
			xstream.addPermission(AnyTypePermission.ANY);
			t1 = t.newInstance();
			t1 = (T) xstream.fromXML(xml);
		} catch (InstantiationException | IllegalAccessException e) {
			 t1 = null;
		}
		return t1;
	}
	

	
	/**
	 *   获取xml
	 * @param <T>
	 * @param xml
	 * @param t
	 * @return
	 */
	public static  <T> String getXml(T t){
		String  xml=null;
		try {
			XStream xstream = new XStream(new Xpp3Driver(new NoNameCoder()));
			xstream.autodetectAnnotations(true);
		    xstream.processAnnotations(t.getClass());
		    xml=xstream.toXML(t);
		} catch (Exception e) {
		}
		return xml;
	}
	//xstream.omitField(Province.class,”ID”);
}
