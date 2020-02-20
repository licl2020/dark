package com.datareport.common.http;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import lombok.extern.log4j.Log4j;
@Log4j
public class HttpRequest {
	   private static RequestConfig requestConfig = RequestConfig.custom()
	            .setSocketTimeout(5000)
	            .setConnectTimeout(5000)
	            .setConnectionRequestTimeout(5000)
	            .build();
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "&" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    public static String httpPost(String url,String key,String jsonData) throws Exception
    {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {

            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-Type","application/json");
			httpPost.addHeader("SECRET_KEY",key);
			httpPost.setEntity(new StringEntity(jsonData, Charset.forName("UTF-8")));

            httpResponse = httpClient.execute(httpPost);

            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

        }catch (Exception var){
        	log.error("http请求异常"+var);
            var.printStackTrace();
        }finally {
            if(reader != null){
                reader.close();
            }
            if(httpResponse != null){
                httpResponse.close();
            }
            httpClient.close();
        }
        return response.toString();

    }
    
    public static String httpPost2(String url,String jsonData) throws Exception
    {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {

            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-Type","application/json");
			httpPost.setEntity(new StringEntity(jsonData, Charset.forName("UTF-8")));

            httpResponse = httpClient.execute(httpPost);

            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
               
            }

        }catch (Exception var){
            var.printStackTrace();
        }finally {
            if(reader != null){
                reader.close();
            }
            if(httpResponse != null){
                httpResponse.close();
            }
            httpClient.close();
        }
        return response.toString();

    }

    
   
    

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        return result;
    }   
    
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostKey(String url, String param,String key) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("SECRET_KEY",key);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        return result;
    }
    
}

