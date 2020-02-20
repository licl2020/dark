package com.datareport.common.txt;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

import com.datareport.common.excel.Excel;
import com.datareport.common.excel.ReflectUtils;

public class TxtUtil<E> {

	private E e;
	int length = 30;
	
   public TxtUtil(E e) {
		this.e = e;
	}

	@SuppressWarnings("unchecked")
	public E get() throws InstantiationException, IllegalAccessException {
		return (E) e.getClass().newInstance();
	}
	
	public  <T> void writeTxt(List<T> list,String path,String txtname){
		try {
			File f = new File(path + File.separator + txtname);
			StringBuffer write = new StringBuffer(); 
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e.getClass());
		Excel excel = null;
			if(f.length()<=0) {
				try {
					for (Field field : fields) {
						field.setAccessible(true);
						excel = field.getAnnotation(Excel.class);
						if (excel == null || excel.skip() == true) {
							continue;
						}
						// 写入标题
						 write.append(appentStr4Length(excel.name(),length));
					}
				} catch (Exception e) {
				}
				writeTxt(path,txtname,write.toString());
			}
			if(list.size()>0) {
				StringBuffer writedata= new StringBuffer(); 
				for (T t : list) {
					Object o = null;
					for (Field field : fields) {

						field.setAccessible(true);

						// 忽略标记skip的字段
						excel = field.getAnnotation(Excel.class);
						if (excel == null || excel.skip() == true) {
							continue;
						}
						o = field.get(t);
						// 如果数据为空，跳过
						String h=null;
						if (o != null){
							h=field.get(t).toString();
							
						}
						writedata.append(appentStr4Length(h,length)+",");
					}
				}
				writeTxt(path,txtname,writedata.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void writeTxt(String path,String txtname,String content){
		 Writer out = null;
		try {
			 File dir = new File(path);
		        if (!dir.exists()) {
		            dir.mkdirs();
		        }
		     File bcpFile = new File(path + File.separator + txtname); // 相对路径，如果没有则要建立一个新的output。txt文件
		    
		            if(!bcpFile.exists())
		                bcpFile.createNewFile();
		            out = new OutputStreamWriter(new FileOutputStream(bcpFile,true), "UTF-8");
		            out.append(content+"\r\n");
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
	       if (out != null) {
	           try {
	               out.flush();
	               out.close();
	           } catch (IOException e) {
	           }
	       }
	   }
		

	}
	
	

public static String appentStr4Length(String str , int length){
    if(str == null){
        str = "";
    }
    try {
        int strLen = 0;//计算原字符串所占长度,规定中文占两个,其他占一个
        for(int i = 0 ; i<str.length(); i++){
            if(isChinese(str.charAt(i))){
                strLen = strLen + 2;
            }else{
                strLen = strLen + 1;
            }
        }
        if(strLen>=length){
            return str;
        }
        int remain = length - strLen;//计算所需补充空格长度
        for(int i =0 ; i< remain ;i++){
            str = str + " ";
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return str;
}

//根据Unicode编码完美的判断中文汉字和符号
private static boolean isChinese(char c) {
 Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
 if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
         || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
         || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
         || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
         || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
         || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
         || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
     return true;
 }
 return false;
}
}
