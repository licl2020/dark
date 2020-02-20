package com.datareport.common.xml;


import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.*;

@Slf4j
public class XmlUtil {

    /**
     * 将对象直接转换成String类型的XML输出
     *
     * @param obj 指定对象(包含XML注解)
     * @return 返回XML
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            // 将对象序列化为Xml
            Marshaller marshaller = context.createMarshaller();
            // 格式化Xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, "gbk");
//            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, Boolean.TRUE);

            // 将对象转换成输出流形式的XML
            marshaller.marshal(obj, sw);

        } catch (JAXBException e) {
            log.error("生成 xml 报错", e);
        }
        return sw.toString();
    }

    /**
     * 将file类型的xml装换成对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertXmlFileToT(Class<T> clazz, InputStream inputStream) {
        T xmlObject = null;
        FileReader fr = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshal = context.createUnmarshaller();
            xmlObject = (T) unmarshal.unmarshal(inputStream);
        } catch (JAXBException e) {
            log.error("解析 xml 报错", e);
        } catch (Exception e) {
            log.error("解析 xml 报错", e);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return xmlObject;
    }

    /**
     * 将file类型的xml装换成对象
     */
//    public static <T> T convertXmlFileToT(Class<T> clazz, String xmlPath) {
//        return convertXmlFileToT(clazz, new File(xmlPath));
//    }

    /**
     * 将file类型的xml装换成对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertXmlFileToT(Class<T> clazz, String fileName) {
        T xmlObject = null;
//        FileReader fr = null;
        InputStreamReader isr = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshal = context.createUnmarshaller();
//            fr = new FileReader(file);
            isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            xmlObject = (T) unmarshal.unmarshal(isr);
        } catch (JAXBException e) {
            log.error("解析 xml 报错", e);
        } catch (Exception e) {
            log.error("解析 xml 报错", e);
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return xmlObject;
    }

    /**
     * 将String类型的Xml转换成对象
     */
    @SuppressWarnings("unchecked")
    public <T> T convertXmlStrToT(Class<T> clazz, String xmlStr) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            log.error("生成 xml 报错", e);
        }
        return xmlObject;
    }

    /**
     * 根据xml路径将其转为对象
     */
    public <T> String convertPathToT(Class<T> clazz, String path) {
        JAXBContext context = null;
        // 创建输出流
        FileWriter fw = null;
        try {
            context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // 将对象转换成输出流形式的xml
            try {
                fw = new FileWriter(path);
            } catch (Exception e) {
                log.error("读取文件报错", e);
                e.printStackTrace();
            }
            marshaller.marshal(clazz, fw);
        } catch (JAXBException e1) {
            log.error("解析 xml 报错", e1);
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return fw.toString();
    }
}