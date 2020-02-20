package com.datareport.common.http;







import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datareport.common.String.StringUtil;

public class HttpServletUtil {
    public static final String CONTENT_TYPE_UTF8 = "UTF-8";

    public static final String CHARACTOR_ENCODEING_UTF8 = "UTF-8";

    public static final String CONTENT_TYPE_SOAP_XML = "application/soap+xml";

    /**
     * @param @param  strResponse
     * @param @param  response
     * @param @throws Exception
     * @return void
     * @throws
     * @Description：返回应答数据的统一处理
     * @author LICL
     * @Date 2017年5月15日上午11:08:43
     */


    public static void sendResponse(String strResponse, HttpServletResponse response)
            throws Exception {
        synchronized (response) {
            PrintWriter out = null;
            try {
                response.setContentLength(strResponse.getBytes(CHARACTOR_ENCODEING_UTF8).length);
                out = response.getWriter();
                out.println(strResponse);
            } catch (UnsupportedEncodingException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            } finally {
                try {
                    response.flushBuffer();
                    if (out != null)
                        out.close();
                } catch (IOException e) {
                    // throw e;
                }
            }
        }
    }
    
    
    /**
     * 创建日期:2018年4月6日<br/>
     * 代码创建:黄聪<br/>
     * 功能描述:写数据到浏览器上<br/>
     * @param resp
     * @param json
     */
    public static void writeJson(String json,HttpServletResponse resp){
        PrintWriter out = null;
        try {
            //设定类容为json的格式
            resp.setContentType("application/json;charset=UTF-8");
            out = resp.getWriter();
            //写到客户端
            out.write(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                out.close();
            }
        }
    }

    /**
     * @param @param  req
     * @param @return
     * @return String IP地址
     * @throws
     * @Description：获取真实请求地址
     * @author LICL
     * @Date 2017年5月15日上午11:09:53
     */
    public static String getRemoteAddress(HttpServletRequest req) {
        // 识别通过HTTP代理或负载平衡器连接的客户端
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = req.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = req.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = req.getRemoteAddr();
        return ip;
    }

    /**
     * @param @param  req
     * @param @return
     * @return String
     * @throws
     * @Description：获取HTTP请求信息
     * @author LICL
     * @Date 2017年5月15日上午11:10:45
     */
    public static String getRequestInfo(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder("\n========== Http Servlet Request Infomation ==========\n");
        Enumeration<?> names = req.getHeaderNames();
        String name = null;
        String value = null;
        while (names.hasMoreElements()) {
            name = (String) names.nextElement();
            value = req.getHeader(name);
            sb.append(name).append(" : ").append(value).append("\n");
        }
        sb.append("\n== Request Parameters ==\n");
        names = req.getParameterNames();
        while (names.hasMoreElements()) {
            name = (String) names.nextElement();
            value = req.getParameter(name);
            sb.append(StringUtil.isNotBlank(name) ? name : "Without Parameter Name");
            sb.append(" : ");
            sb.append(StringUtil.isNotBlank(value) ? value : "Without Parameter Value");
            sb.append("\n");
        }
        sb.append("========================================");
        return sb.toString();
    }


  

    /**
     * @param response
     * @param strQrCode
     * @Description：输出png图片
     * @author LICL
     * @Date 2017年5月23日下午2:04:31
     */
    public static void outPNGStream(HttpServletResponse response, String strQrCode) {
        // ByteArrayOutputStream out =
        // QRCode.from(strQrCode).to(ImageType.PNG).stream();
        // response.setContentType("image/png");
        // response.setContentLength(out.size());
        // OutputStream outStream = response.getOutputStream();
        // outStream.write(out.toByteArray());
        // outStream.flush();
        // outStream.close();
    }
}
