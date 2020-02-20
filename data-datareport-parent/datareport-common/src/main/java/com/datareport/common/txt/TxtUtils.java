package com.datareport.common.txt;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtUtils {


    //文件的删除
public static void deleteExcel(String path,String txtname){
	try{
		 File bcpFile = new File(path + File.separator + txtname);
			bcpFile.delete();
		}catch(NullPointerException n){
			System.out.println("Sorry,No such file");
			}
}


/**
 * 读取一个文本 一行一行读取
 *
 * @param path
 * @return
 * @throws IOException
 */
public static List<String> readListFile(String path) throws IOException {
    // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
    List<String> list = new ArrayList<String>();
    FileInputStream fis = new FileInputStream(path);
    // 防止路径乱码   如果utf-8 乱码  改GBK     eclipse里创建的txt  用UTF-8，在电脑上自己创建的txt  用GBK
    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    BufferedReader br = new BufferedReader(isr);
    String line = "";
    while ((line = br.readLine()) != null) {
        // 如果 t x t文件里的路径 不包含---字符串       这里是对里面的内容进行一个筛选
        if (line.lastIndexOf("---") < 0) {
            list.add(line);
        }
    }
    br.close();
    isr.close();
    fis.close();
	try{
		 File bcpFile = new File(path);
			bcpFile.delete();
			bcpFile.createNewFile();
		}catch(NullPointerException n){
			System.out.println("Sorry,No such file");
			}
    return list;
}

public static String readerTxt(String path,String txtname){
	 Writer out = null;
	 StringBuffer sb =new StringBuffer();
	try {
		 File dir = new File(path);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	     File bcpFile = new File(path + File.separator + txtname); // 相对路径，如果没有则要建立一个新的output。txt文件
	     try (
	    		 FileReader reader = new FileReader(bcpFile);
	             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
	        ) 
	        {
	            String line;
	            //网友推荐更加简洁的写法
	            while ((line = br.readLine()) != null) {
	                // 一次读入一行数据
	            	sb.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	} catch (Exception e) {
   }
	return sb.toString();
	

}

public static Map<String, String>  readerTxtMap(String path,String txtname){
	 Map<String, String> map=new HashMap<String, String>();
	try {
		 File dir = new File(path);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	     File bcpFile = new File(path + File.separator + txtname); // 相对路径，如果没有则要建立一个新的output。txt文件
	     try (
	    		 FileReader reader = new FileReader(bcpFile);
	             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
	        ) 
	        {
	            String line;
	            //网友推荐更加简洁的写法
	            while ((line = br.readLine()) != null) {
	            	line=line.substring(0, line.length()-1);
	            	String [] ss=line.split(",");
	    			for (int i = 0; i < ss.length; i++) {
	    			String [] each=ss[i].split("=");
	    			map.put(each[0], each[1]);
	    			}
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	} catch (Exception e) {
  }
	return map;
	

}


public static void writeTxt(String path,String txtname,String content){
	 Writer out = null;
	try {
		deleteExcel(path,txtname);
		 File dir = new File(path);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	     File bcpFile = new File(path + File.separator + txtname); // 相对路径，如果没有则要建立一个新的output。txt文件
	    
	            if(!bcpFile.exists())
	                bcpFile.createNewFile();
	            out = new OutputStreamWriter(new FileOutputStream(bcpFile,true), "UTF-8");
	            out.append(content);
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


public static void writeTxt(String path,String content){
	 Writer out = null;
	try {
	     File bcpFile = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件
	            if(bcpFile.exists()) {
	            	bcpFile.delete();
	            }else {
	                bcpFile.createNewFile();
	            }
	            out = new OutputStreamWriter(new FileOutputStream(bcpFile,true), "UTF-8");
	            out.append(content);
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

public static void writeTxt2(String path,String txtname,String content){
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

//创建txt
@SuppressWarnings("unused")
private static void CreateTxt(String dir){
	try {
	File dirPath = new File(dir);
	if (!dirPath.exists()){
		dirPath.createNewFile();}
		} catch (Exception e) {e.printStackTrace();
		}
	}
}
