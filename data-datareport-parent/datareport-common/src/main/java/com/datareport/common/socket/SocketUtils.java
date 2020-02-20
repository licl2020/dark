package com.datareport.common.socket;



import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

import com.datareport.common.json.JsonUtils;

import lombok.extern.log4j.Log4j;


 
/**
 * 文件传输Client端<br>
 * 功能说明：
 *
 * @author licl
 * @version 1.0
 */
@Log4j
public class SocketUtils extends Socket {
 
    private Socket client;
 
    private FileInputStream fis;
 
    private DataOutputStream dos;
    
 
    /**
     * 构造函数<br/>
     * 与服务器建立连接
     * @throws Exception
     */
    public SocketUtils(String ip,int port) throws Exception {
    	super(ip, port);
        this.client = this;
        log.info("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }
    
    
 
    /**
     * 向服务端传输文件
     * @param fileDate 文件地址
     * @param fileSuffixName 文件后缀名
     * @return
     * @throws Exception
     */
    public String sendFile(String fileUrl,String fileSuffixName) throws Exception {
    	String fmPath=null;
        try {
            File file = new File(fileUrl);
            if(file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());
 
                // 文件名和长度
                dos.writeUTF(fileSuffixName);
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
 
                // 开始传输文件
                log.info("======== 开始传输文件 ========");
                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    System.out.print("| " + (100*progress/file.length()) + "% |");
                    
                }
                log.info("======== 文件传输成功 ========");
                client.shutdownOutput();
                InputStream is = client.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                String info = dis.readUTF();
                @SuppressWarnings("unchecked")
				Map<String,String> map = JsonUtils.StringToMap(info);
                if(map.get("code").equals("200")) {
                	fmPath=map.get("url");
                	log.info("访问地址:"+fmPath);
                }else {
                	log.info("很遗憾:"+map.get("msg"));
                }
                dis.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            client.close();
        }
		return fmPath;
    }
    
    
    /**
       * 向服务端传输网络文件
     * @param fileUrl 网络文件地址
     * @param fileSuffixName 文件名包含后缀 例如 file.txt
     * @return
     * @throws Exception
     */
    public String sendFileUrl(String fileUrl,String fileName) throws Exception {
    	String upPath=saveToFile(fileUrl,fileName);
    	String path=sendFile(upPath,fileName.substring(fileName.lastIndexOf(".") + 1));
    	delete(upPath);
		return path;
    }
    
    
    /**
     * 向服务端传输文件
     * @param fileContent 文件文件内容
     * @param fileSuffixName 文件后缀名
     * @return
     * @throws Exception
     */
    public String sendFileContent(String fileContent,String fileSuffixName) throws Exception {
    	String upPath=saveToFileContent(fileContent,fileSuffixName);
    	String path=sendFile(upPath,fileSuffixName);
    	delete(upPath);
		return path;
    }
    
    
    /**
     * 向服务端传输文件
     * @param fileContent 文件文件内容
     * @param fileSuffixName 文件后缀名
     * @return
     * @throws Exception
     */
    public static String copyFile(String path,String mpath,String fileSuffixName) {
    	try {
    		 InputStream input = new FileInputStream(path); 
        	 File dir = new File(mpath);
    	        if (!dir.exists()) {
    	            dir.mkdirs();
    	        }
    	     mpath=mpath+String.valueOf(System.currentTimeMillis())+"."+fileSuffixName;
        	 FileOutputStream output = new FileOutputStream(mpath); 
        	 FileCopyUtils.copy(input, output);
		} catch (Exception e) {
		}
    	
		return mpath;
    }
    
    
    /** 
     * 网络文件保存
     *  
     * @param destUrl 
     *            String 
     * @param fileName 
     *            String 
     * @throws Exception 
     */  
    public String saveToFile(String destUrl, String fileName) throws IOException {  
    	 String configPath = "." + File.separator + "config" + File.separator;
        FileOutputStream fos = null;  
        BufferedInputStream bis = null;  
        HttpURLConnection httpUrl = null;  
        URL url = null;  
        byte[] buf = new byte[1024];  
        int size = 0;  
  
        // 建立链接  
        url = new URL(destUrl);  
        httpUrl = (HttpURLConnection) url.openConnection();  
        // 连接指定的资源  
        httpUrl.connect();  
        // 获取网络输入流  
        bis = new BufferedInputStream(httpUrl.getInputStream());  
        // 建立文件  
        fos = new FileOutputStream(configPath+fileName);  
        System.out.println(httpUrl.getContentLength());
        log.info("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件["  
                + fileName + "]");
        // 保存文件  
        while ((size = bis.read(buf)) != -1)  
            fos.write(buf, 0, size);  
  
        fos.close();  
        bis.close();  
        httpUrl.disconnect();
		return configPath+fileName;  
    }  
    
    public  String saveToFileContent(String fileContent,String fileSuffixName) throws Exception{
    	 String configPath = "." + File.separator + "config" + File.separator;
    	 String fileName=String.valueOf(System.currentTimeMillis())+"."+fileSuffixName;
    	 writeTxt(configPath,fileName,fileContent);
		return configPath+fileName;
    }
    
    //文件的删除
public static void delete(String path){
	try{
		 File bcpFile = new File(path);
			bcpFile.delete();
			log.info("清除临时文件成功");
		}catch(NullPointerException n){
			 log.info("Sorry,No such file");
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
  
 
    /**
     * 入口
     * @param args
     */
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        try {
        	//new SocketUtils("112.13.91.39",8889).saveToFile("http://112.13.91.39:8888/file/xlsx/201908/28/1566958144564.xlsx","cs.xlsx");
        	for(int i=0;i<1000;i++) {
        		new SocketUtils("172.168.20.69",65433).sendFile("D:\\java\\config\\dzjtsb.xlsx","xlsx");
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}