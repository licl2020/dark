package com.datareport.common.excel;





import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class ResponseUtil {
	
	
	
	/***
	 * 输出ecxel
	 * @param response	弹出excel
	 */
	public static void excel2003(HttpServletResponse response, HSSFWorkbook wb, String title) {
		try {
			response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename="+title);    
	        OutputStream ouputStream = response.getOutputStream();
	        wb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();
	        System.out.println("输出excel2003");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/***
	 * 输出ecxel
	 * @param response	弹出excel
	 */
	public static void excel2007(HttpServletResponse response, SXSSFWorkbook wb, String title) {
		try {
			response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename="+title);    
	        OutputStream ouputStream = response.getOutputStream();
	        wb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();
			System.out.println("输出excel2007");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 输出ecxel
	 * @param response	弹出excel
	 */
	public static void excel20072(HttpServletResponse response, Workbook wb, String title) {
		try {
			response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename="+title);    
	        OutputStream ouputStream = response.getOutputStream();
	        wb.write(ouputStream);    
	        ouputStream.flush();    
	        ouputStream.close();
	        System.out.println("输出excel2007");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 写出简单消息
	 * @param response	写出流对象
	 * @param text	要写的文本
	 */
	public static void out(HttpServletResponse response, String text) {
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(text);
			out.flush();
			out.close();
			System.out.println("浏览器输出：" + text);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description：输出png图片
	 * @author kucs
	 * @Date 2017年5月23日下午2:04:31
	 * @param response
	 * @param strQrCode
	 */
	public static void outPNGStream(HttpServletResponse response, String strQrCode){
		//			ByteArrayOutputStream out = QRCode.from(strQrCode).to(ImageType.PNG).stream();
//			response.setContentType("image/png");
//			response.setContentLength(out.size());
//			OutputStream outStream = response.getOutputStream();
//			outStream.write(out.toByteArray());
//			outStream.flush();
//			outStream.close();
		System.out.println("输出二维码 png格式图片");
	}

}
