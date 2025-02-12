package com.datareport.common.excel;





import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil<E> {

	private E e;

	private  SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private int etimes = 0;

	public ExcelUtil(E e) {
		this.e = e;
	}

	@SuppressWarnings("unchecked")
	public E get() throws InstantiationException, IllegalAccessException {
		return (E) e.getClass().newInstance();
	}

	/**
	 * 将数据写入到Excel文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sheetName
	 *            工作表名称
	 * @param title
	 *            工作表标题栏
	 * @param data
	 *            工作表数据
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public  void writeToFile(String filePath, String[] sheetName,
                                   List<? extends Object[]> title,
                                   List<? extends List<? extends Object[]>> data)
			throws FileNotFoundException, IOException {
		// 创建并获取工作簿对象
		Workbook wb = getWorkBook(sheetName, title, data);
		// 写入到文件
		FileOutputStream out = new FileOutputStream(filePath);
		wb.write(out);
		out.close();
	}

	/**
	 * 创建工作簿对象<br>
	 * <font color="red">工作表名称，工作表标题，工作表数据最好能够对应起来</font><br>
	 * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据<br>
	 * <b> 注意：<br>
	 * 需要为每个工作表指定<font color="red">工作表名称，工作表标题，工作表数据</font><br>
	 * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的<br>
	 * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中 </b>
	 * 
	 * @param sheetName
	 *            工作表名称的数组
	 * @param title
	 *            每个工作表名称的数组集合
	 * @param data
	 *            每个工作表数据的集合的集合
	 * @return Workbook工作簿
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public  Workbook getWorkBook(String[] sheetName,
                                       List<? extends Object[]> title,
                                       List<? extends List<? extends Object[]>> data)
			throws FileNotFoundException, IOException {
		// 创建工作簿
		Workbook wb = new SXSSFWorkbook();
		// 创建一个工作表sheet
		Sheet sheet = null;
		// 申明行
		Row row = null;
		// 申明单元格
		Cell cell = null;
		// 单元格样式
		CellStyle titleStyle = wb.createCellStyle();
		CellStyle cellStyle = wb.createCellStyle();
		// 字体样式
		Font font = wb.createFont();
		// 粗体
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		// 水平居中
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 水平居中
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		cellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);

		// 标题数据
		Object[] title_temp = null;

		// 行数据
		Object[] rowData = null;

		// 工作表数据
		List<? extends Object[]> sheetData = null;

		// 遍历sheet
		for (int sheetNumber = 0; sheetNumber < sheetName.length; sheetNumber++) {
			// 创建工作表
			sheet = wb.createSheet();
			// 设置默认列宽
			sheet.setDefaultColumnWidth(18);
			// 设置工作表名称
			wb.setSheetName(sheetNumber, sheetName[sheetNumber]);
			// 设置标题
			title_temp = title.get(sheetNumber);
			row = sheet.createRow(0);

			// 写入标题
			for (int i = 0; i < title_temp.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(title_temp[i].toString());
			}

			try {
				sheetData = data.get(sheetNumber);
			} catch (Exception e) {
				continue;
			}
			// 写入行数据
			for (int rowNumber = 0; rowNumber < sheetData.size(); rowNumber++) {
				// 如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1
				row = sheet.createRow(title_temp == null ? rowNumber
						: (rowNumber + 1));
				rowData = sheetData.get(rowNumber);
				for (int columnNumber = 0; columnNumber < rowData.length; columnNumber++) {
					cell = row.createCell(columnNumber);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(rowData[columnNumber] + "");
				}
			}
		}
		return wb;
	}

	/**
	 * 创建工作簿对象<br>
	 * <font color="red">工作表名称，工作表标题，工作表数据最好能够对应起来</font><br>
	 * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据<br>
	 * <b> 注意：<br>
	 * 需要为每个工作表指定<font color="red">工作表名称，工作表标题，工作表数据</font><br>
	 * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的<br>
	 * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中 </b>
	 * 
	 * @param sheetName
	 *            工作表名称的数组
	 * @param title
	 *            每个工作表名称的数组集合
	 * @param data
	 *            每个工作表数据的集合的集合
	 * @return Workbook工作簿
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public  Workbook getWorkBook2003(String[] sheetName,
                                           List<? extends Object[]> title,
                                           List<? extends List<? extends Object[]>> data)
			throws FileNotFoundException, IOException {
		// 创建工作簿
		Workbook wb = new SXSSFWorkbook();
		// 创建一个工作表sheet
		Sheet sheet = null;
		// 申明行
		Row row = null;
		// 申明单元格
		Cell cell = null;
		// 单元格样式
		CellStyle titleStyle = wb.createCellStyle();
		CellStyle cellStyle = wb.createCellStyle();
		// 字体样式
		Font font = wb.createFont();
		// 粗体
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		// 水平居中
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 水平居中
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		cellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);

		// 标题数据
		Object[] title_temp = null;

		// 行数据
		Object[] rowData = null;

		// 工作表数据
		List<? extends Object[]> sheetData = null;

		// 遍历sheet
		for (int sheetNumber = 0; sheetNumber < sheetName.length; sheetNumber++) {
			// 创建工作表
			sheet = wb.createSheet();
			// 设置默认列宽
			sheet.setDefaultColumnWidth(18);
			// 设置工作表名称
			wb.setSheetName(sheetNumber, sheetName[sheetNumber]);
			// 设置标题
			title_temp = title.get(sheetNumber);
			row = sheet.createRow(0);

			// 写入标题
			for (int i = 0; i < title_temp.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(title_temp[i].toString());
			}

			try {
				sheetData = data.get(sheetNumber);
			} catch (Exception e) {
				continue;
			}
			// 写入行数据
			for (int rowNumber = 0; rowNumber < sheetData.size(); rowNumber++) {
				// 如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1
				row = sheet.createRow(title_temp == null ? rowNumber
						: (rowNumber + 1));
				rowData = sheetData.get(rowNumber);
				for (int columnNumber = 0; columnNumber < rowData.length; columnNumber++) {
					cell = row.createCell(columnNumber);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(rowData[columnNumber] + "");
				}
			}
		}
		return wb;
	}

	/**
	 * 将数据写入到EXCEL文档
	 * 
	 * @param list
	 *            数据集合
	 * @param edf
	 *            数据格式化，比如有些数字代表的状态，像是0:女，1：男，或者0：正常，1：锁定，变成可读的文字
	 *            该字段仅仅针对Boolean,Integer两种类型作处理
	 * @param filePath
	 *            文件路径
	 * @throws Exception
	 *             读出2007
	 */
	public  <T> void writeToFile(List<T> list, String filePath)
			throws Exception {
		// 创建并获取工作簿对象
		// Workbook wb = getWorkBook(list,title);
		// 写入到文件
		FileOutputStream out = new FileOutputStream(filePath);
		// wb.write(out);
		out.close();
	}


	/**
	 * 获得Workbook对象
	 * 
	 * @param list
	 *            数据集合
	 * @return Workbook
	 * @throws Exception
	 */
	public  <T> Workbook getWorkBook(List<T> list, String title)
			throws Exception {
		// 创建工作簿
		Workbook wb = new SXSSFWorkbook();

		if (list == null || list.size() == 0)
			return wb;
		
		//限制条数
		int pointsDataLimit = 3000;
		
		//算出批数
		Integer size = list.size();
		
		//判断是否有必要分批
        if(pointsDataLimit<size){
        	
        	   //分批数
        	   int part = size/pointsDataLimit;
		       
		       for (int i = 0; i < part; i++) {
		    	   
		    	   List<T> listPage = list.subList(0, pointsDataLimit);
		    	     //处理
		    	   wb=setWorkBook(wb,listPage,title+(i+1));
		    	   
		    	    //剔除
		    	    list.subList(0, pointsDataLimit).clear();
				  }
		       
		       if(!list.isEmpty()){
		    	   String s = String.valueOf(part+1);
		    	   wb=setWorkBook(wb,list,title+s);
		        }
		       
        }else{
        	wb=setWorkBook(wb,list,title+"1");
        	
        }

	

		return wb;
	}
	
	

	/**
	 * 获得Workbook对象
	 * 
	 * @param list 数据集合
	 * @param url 追加的excel
	 * @return Workbook
	 * @throws Exception
	 */
	public  <T> Workbook getWorkBook(List<T> list, String title,String url)
			throws Exception {
		// 创建工作簿
		Workbook wb = null;
		
		if (list == null || list.size() == 0)
			return wb;
		int count=0;
		int title_count=0;
		boolean f = false;
		 if(null != url && is_cz(url)) {
				InputStream fs = new FileInputStream(url);//获取excel
				 wb = new XSSFWorkbook(fs);
				fs.close();
				title_count=wb.getNumberOfSheets();
				Sheet sheet =wb.getSheetAt(title_count-1);
				 count=sheet.getLastRowNum();
			 f=true;
		 }else {
			 wb = new XSSFWorkbook();
		 }
		 
		
		//限制条数
		int pointsDataLimit = 3000;
		
		//判断如果之前的工作薄数据还存在，则先进行填充，剩余数据新建工作薄处理
		int exe_count=pointsDataLimit-count;
		if(f && exe_count>0) {
			 if(exe_count>list.size()) {
				 exe_count=list.size();
			 }
			List<T> listPage = list.subList(0,exe_count);
			 wb=fillWorkBook(wb,listPage,title+1);
			 //剔除
	    	 list.subList(0, exe_count).clear();
		}
		
		//算出批数
		Integer size = list.size();
		
		//判断是否有必要分批
        if(pointsDataLimit<size){
        	
        	   //分批数
        	   int part = size/pointsDataLimit;
		       
		       for (int i = 0; i < part; i++) {
		    	   
		    	    	 title_count=title_count+1;
		    	   
		    	   List<T> listPage = list.subList(0, pointsDataLimit);
		    	     //处理
		    	     wb=setWorkBook(wb,listPage,title+(title_count));
		    	    //剔除
		    	    list.subList(0, pointsDataLimit).clear();
				  }
		       
		       if(!list.isEmpty()){
		    	   wb=setWorkBook(wb,list,title+(title_count+1));
		        }
		       
        }else if(list.size()>0){
        	 wb=setWorkBook(wb,list,title+(title_count+1));
        }

	

		return wb;
	}
	
	
	/**
	 * 获得Workbook对象
	 * 
	 * @param list
	 *            数据集合
	 * @return Workbook
	 * @throws Exception
	 */
	public  <T> Workbook getWorkBook2(List<T> list, String title)
			throws Exception {
		// 创建工作簿
		Workbook wb = new SXSSFWorkbook();
		wb=setWorkBook(wb,list,title);
		return wb;
	}
	
	
	/**
	 * 判断文件是否存在
	 * @param url
	 * @return
	 */
	public  boolean is_cz(String url) {
		boolean f =true;
		try {
			File file = new File(url);
			if (!file.exists()) {
				f=false;
	        }
		} catch (Exception e) {
			f=false;
		}
		return f;
		
	}
	
	
	public  <T> Workbook setWorkBook(Workbook wb, List<T> list, String title) throws Exception {
		// 创建一个工作表sheet
		Sheet sheet = wb.createSheet(title);
		// 申明行
		Row row = sheet.createRow(0);
		// 申明单元格
		Cell cell = null;

		CreationHelper createHelper = wb.getCreationHelper();

		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e.getClass());

		XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
		titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 设置前景色
		titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(159,
				213, 183)));
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);

		Font font = wb.createFont();
		font.setColor(HSSFColor.BROWN.index);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 设置字体
		titleStyle.setFont(font);

		int columnIndex = 0;
		Excel excel = null;
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				excel = field.getAnnotation(Excel.class);
				if (excel == null || excel.skip() == true) {
					continue;
				}
				// 列宽注意乘256
				//sheet.setColumnWidth(columnIndex, excel.width() * 256);
				// 写入标题
				cell = row.createCell(columnIndex);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(excel.name());

				columnIndex++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}


		int rowIndex = 1;

		CellStyle cs = wb.createCellStyle();

		for (T t : list) {
			row = sheet.createRow(rowIndex);
			columnIndex = 0;
			Object o = null;
			for (Field field : fields) {

				field.setAccessible(true);

				// 忽略标记skip的字段
				excel = field.getAnnotation(Excel.class);
				if (excel == null || excel.skip() == true) {
					continue;
				}
				// 数据
				cell = row.createCell(columnIndex);

				o = field.get(t);
				// 如果数据为空，跳过
				if (o == null){
					columnIndex++;
					continue;
				}

				// 处理日期类型
				if (o instanceof Date) {
					cs.setDataFormat(createHelper.createDataFormat().getFormat(
							"yyyy-MM-dd HH:mm:ss"));
					cell.setCellStyle(cs);
					cell.setCellValue((Date) field.get(t));
				} else if (o instanceof Double || o instanceof Float) {
					cell.setCellValue((Double) field.get(t));
				} else if (o instanceof Boolean) {
					Boolean bool = (Boolean) field.get(t);
					cell.setCellValue(bool);

				} else if (o instanceof Integer) {
					Integer intValue = (Integer) field.get(t);
					cell.setCellValue(intValue);
				} else {
					cell.setCellValue(field.get(t).toString());
				}

				columnIndex++;
			}

			rowIndex++;
		}
		return wb;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param wb
	 * @param list
	 * @param title
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public  <T> Workbook fillWorkBook(Workbook wb, List<T> list, String title) throws Exception {
		
		// 创建一个工作表sheet
		Sheet sheet =wb.getSheetAt(wb.getNumberOfSheets()-1);
		// 申明行
		Row row = null;
		// 申明单元格
		Cell cell = null;
		CreationHelper createHelper = wb.getCreationHelper();
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e.getClass());
		
		Excel excel = null;
		int rowIndex =(sheet.getLastRowNum()+1);
		int columnIndex = 0;
		CellStyle cs = wb.createCellStyle();
		for (T t : list) {
			row = sheet.createRow(rowIndex);
			columnIndex = 0;
			Object o = null;
			for (Field field : fields) {

				field.setAccessible(true);

				// 忽略标记skip的字段
				excel = field.getAnnotation(Excel.class);
				if (excel == null || excel.skip() == true) {
					continue;
				}
				// 数据
				cell = row.createCell(columnIndex);

				o = field.get(t);
				// 如果数据为空，跳过
				if (o == null){
					columnIndex++;
					continue;
				}

				// 处理日期类型
				if (o instanceof Date) {
					cs.setDataFormat(createHelper.createDataFormat().getFormat(
							"yyyy-MM-dd HH:mm:ss"));
					cell.setCellStyle(cs);
					cell.setCellValue((Date) field.get(t));
				} else if (o instanceof Double || o instanceof Float) {
					cell.setCellValue((Double) field.get(t));
				} else if (o instanceof Boolean) {
					Boolean bool = (Boolean) field.get(t);
					cell.setCellValue(bool);

				} else if (o instanceof Integer) {
					Integer intValue = (Integer) field.get(t);
					cell.setCellValue(intValue);
				} else {
					cell.setCellValue(field.get(t).toString());
				}

				columnIndex++;
			}

			rowIndex++;
		}
		return wb;
	}

	/**
	 * 获得Workbook对象
	 * 
	 * @param list
	 *            数据集合
	 * @return Workbook
	 * @throws Exception
	 */
	public  <T> HSSFWorkbook getWorkBook2003(List<T> list, String title)
			throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		if (list == null || list.size() == 0)
			return wb;
		
		//限制条数
		int pointsDataLimit = 3000;
		
		//算出批数
		Integer size = list.size();
		
		//判断是否有必要分批
        if(pointsDataLimit<size){
        	
        	   //分批数
        	   int part = size/pointsDataLimit;
		       
		       for (int i = 0; i < part; i++) {
		    	   
		    	   List<T> listPage = list.subList(0, pointsDataLimit);
		    	     //处理
		    	   wb=setWorkBook2003(wb,listPage,title+(i+1));
		    	   
		    	    //剔除
		    	    list.subList(0, pointsDataLimit).clear();
				  }
		       
		       if(!list.isEmpty()){
		    	   String s = String.valueOf(part+1);
		    	   wb=setWorkBook2003(wb,list,title+s);
		        }
		       
        }else{
        	wb=setWorkBook2003(wb,list,title+"1");
        	
        }

	
		return wb;
	}
	
	
	/**
	 * 获得Workbook对象
	 * 
	 * @param list
	 *            数据集合
	 * @return Workbook
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public  <T> HSSFWorkbook setWorkBook2003(HSSFWorkbook wb, List<T> list, String title)
			throws Exception {
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e.getClass());
		HSSFSheet sheet = wb.createSheet(title);
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = wb.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		int columnIndex = 0;
		Excel excel = null;
		for (Field field : fields) {
			field.setAccessible(true);
			excel = field.getAnnotation(Excel.class);
			if (excel == null || excel.skip() == true) {
				continue;
			}
			sheet.setColumnWidth((short) columnIndex,
					(short) (excel.width() * 256));
			HSSFCell cell = row.createCell((short) columnIndex);
			cell.setCellValue(excel.name());
			cell.setCellStyle(style);
			columnIndex++;
		}
		int rowIndex = 1;
		for (T t : list) {
			row = sheet.createRow((int) rowIndex);
			columnIndex = 0;
			Object o = null;
			for (Field field : fields) {

				field.setAccessible(true);

				// 忽略标记skip的字段
				excel = field.getAnnotation(Excel.class);
				if (excel == null || excel.skip() == true) {
					continue;
				}
				// 数据
				o = field.get(t);
				// 如果数据为空，跳过
				if (o == null){
					columnIndex++;
					continue;
				}
				// 处理日期类型
				if (o instanceof Date) {
					HSSFCell cell = row.createCell((short) columnIndex);
					cell.setCellStyle(style2);
					cell.setCellValue((Date) field.get(t));
				} else if (o instanceof Double || o instanceof Float) {
					HSSFCell cell = row.createCell((short) columnIndex);
					cell.setCellStyle(style2);
					cell.setCellValue((Double) field.get(t));
				} else if (o instanceof Boolean) {
					Boolean bool = (Boolean) field.get(t);
					HSSFCell cell = row.createCell((short) columnIndex);
					cell.setCellStyle(style2);
					cell.setCellValue(bool);

				} else if (o instanceof Integer) {
					Integer intValue = (Integer) field.get(t);
					HSSFCell cell = row.createCell((short) columnIndex);
					cell.setCellStyle(style2);
					cell.setCellValue(intValue);
				} else {
					HSSFCell cell = row.createCell((short) columnIndex);
					cell.setCellStyle(style2);
					cell.setCellValue(field.get(t).toString());
				}

				columnIndex++;
			}

			rowIndex++;
		}

		return wb;
	}

	/**
	 * 从文件读取数据，最好是所有的单元格都是文本格式，日期格式要求yyyy-MM-dd HH:mm:ss,布尔类型0：真，1：假
	 * 
	 * @param edf
	 *            数据格式化
	 * 
	 * @param file
	 *            Excel文件，支持xlsx后缀，xls的没写，基本一样
	 * @return
	 * @throws Exception
	 *             读取2007
	 */
	public List<E> readFromFile(File file) throws Exception {
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e
				.getClass());

		Map<String, String> textToKey = new HashMap<String, String>();

		Excel _excel = null;
		for (Field field : fields) {
			_excel = field.getAnnotation(Excel.class);
			if (_excel == null || _excel.skip() == true) {
				continue;
			}
			textToKey.put(_excel.name(), field.getName());
		}

		InputStream is = new FileInputStream(file);

		Workbook wb = new XSSFWorkbook(is);
		is.close();
		List<E> list = new ArrayList<E>();
		E e = null;
		for(int j=0;j<wb.getNumberOfSheets();j++){
			Sheet sheet = wb.getSheetAt(j);
			Row title = sheet.getRow(0);
			// 标题数组，后面用到，根据索引去标题名称，通过标题名称去字段名称用到 textToKey
			String[] titles = new String[title.getPhysicalNumberOfCells()];
			for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
				titles[i] = title.getCell(i).getStringCellValue();
			}

			int rowIndex = 0;
			int columnCount = titles.length;
			Cell cell = null;
			Row row = null;

			for (Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {

				row = it.next();
				if (rowIndex++ == 0) {
					continue;
				}

				if (row == null) {
					break;
				}

				e = get();

				for (int i = 0; i < columnCount; i++) {
					cell = row.getCell(i);
					etimes = 0;
					if (cell != null) {
						readCellContent(textToKey.get(titles[i]), fields, cell, e);
					}
				}
				list.add(e);
			}
		}
		return list;
	}

	/**
	 * 从文件读取数据，最好是所有的单元格都是文本格式，日期格式要求yyyy-MM-dd HH:mm:ss,布尔类型0：真，1：假
	 * 
	 * @param edf
	 *            数据格式化
	 * 
	 * @param file
	 *            Excel文件，支持xlsx后缀，xls的没写，基本一样
	 * @return
	 * @throws Exception
	 *             读取2003
	 */
	@SuppressWarnings("deprecation")
	public List<E> readFromFile2003(File file) throws Exception {
		Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(e
				.getClass());

		Map<String, String> textToKey = new HashMap<String, String>();

		Excel _excel = null;
		for (Field field : fields) {
			_excel = field.getAnnotation(Excel.class);
			if (_excel == null || _excel.skip() == true) {
				continue;
			}
			textToKey.put(_excel.name(), field.getName());
		}

		InputStream is = new FileInputStream(file);

		HSSFWorkbook hwb = new HSSFWorkbook(is);
		is.close();
		List<E> list = new ArrayList<E>();

		try {
			E e = null;
			for(int j=0;j<hwb.getNumberOfSheets();j++){
				HSSFSheet sheet = hwb.getSheetAt(j);
				HSSFRow title = sheet.getRow(0);
				// 标题数组，后面用到，根据索引去标题名称，通过标题名称去字段名称用到 textToKey
				String[] titles = new String[title.getPhysicalNumberOfCells()];
				for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
					titles[i] = title.getCell((short) i).getStringCellValue();
				}
				int rowIndex = 0;
				int columnCount = titles.length;
				HSSFCell cell = null;
				HSSFRow row = null;
				for (Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {

					row = (HSSFRow)it.next();
					if (rowIndex++ == 0) {
						continue;
					}

					if (row == null) {
						break;
					}

					e = get();

					for (int i = 0; i < columnCount; i++) {
						cell = row.getCell((short) i);
						etimes = 0;
						if (cell != null) {
							readCellContent2003(textToKey.get(titles[i]), fields, cell,
									e);
						}
					}
					list.add(e);
				}
			}
		} catch (Exception e) {
			
		}
		
		
		return list;
	}

	/**
	 * 从单元格读取数据，根据不同的数据类型，使用不同的方式读取<br>
	 * 有时候POI自作聪明，经常和我们期待的数据格式不一样，会报异常，<br>
	 * 我们这里采取强硬的方式<br>
	 * 使用各种方法，知道尝试到读到数据为止，然后根据Bean的数据类型，进行相应的转换<br>
	 * 如果尝试完了（总共7次），还是不能得到数据，那么抛个异常出来，没办法了
	 * 
	 * @param key
	 *            当前单元格对应的Bean字段
	 * @param fields
	 *            Bean所有的字段数组
	 * @param cell
	 *            单元格对象
	 * @param e
	 * @throws Exception
	 */
	public void readCellContent(String key, Field[] fields, Cell cell, E e)
			throws Exception {

		Object o = null;
		DecimalFormat df = new DecimalFormat("#");
		try {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_BOOLEAN:
				o = cell.getBooleanCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				o =df.format(cell.getNumericCellValue());
				break;
			case XSSFCell.CELL_TYPE_STRING:
				o = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				o = cell.getErrorCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				o = null;
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				o = cell.getCellFormula();
				break;
			default:
				o = null;
				break;
			}

			if (o == null)
				return;

			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals(key)) {
					if (field.getType().equals(Date.class)) {
						if (o.getClass().equals(Date.class)) {
							field.set(e, o);
						} else {
							field.set(e, sdf.parse(o.toString()));
						}
					} else if (field.getType().equals(String.class)) {
						if (o.getClass().equals(String.class)) {
							field.set(e, o);
						} else {
							field.set(e, o.toString());
						}
					} else if (field.getType().equals(Long.class)) {
						if (o.getClass().equals(Long.class)) {
							field.set(e, o);
						} else {
							field.set(e, Long.parseLong(o.toString()));
						}
					} else if (field.getType().equals(Integer.class)) {
						if (o.getClass().equals(Integer.class)) {
							field.set(e, o);
						} else {
							field.set(e, Integer.parseInt(o.toString()));
						}

					}
				} else if (field.getType().equals(BigDecimal.class)) {
					if (o.getClass().equals(BigDecimal.class)) {
						field.set(e, o);
					} else {
						field.set(e, BigDecimal.valueOf(Double.parseDouble(o
								.toString())));
					}
				} else if (field.getType().equals(Boolean.class)) {
					if (o.getClass().equals(Boolean.class)) {
						field.set(e, o);
					} else {
						field.set(e, Boolean.parseBoolean(o.toString()));

					}
				} else if (field.getType().equals(Float.class)) {
					if (o.getClass().equals(Float.class)) {
						field.set(e, o);
					} else {
						field.set(e, Float.parseFloat(o.toString()));
					}
				} else if (field.getType().equals(Double.class)) {
					if (o.getClass().equals(Double.class)) {
						field.set(e, o);
					} else {
						field.set(e, Double.parseDouble(o.toString()));
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// 如果还是读到的数据格式还是不对，只能放弃了
			if (etimes > 7) {
				throw ex;
			}
			etimes++;
			if (o == null) {
				readCellContent(key, fields, cell, e);
			}
		}
	}

	/**
	 * 从单元格读取数据，根据不同的数据类型，使用不同的方式读取<br>
	 * 有时候POI自作聪明，经常和我们期待的数据格式不一样，会报异常，<br>
	 * 我们这里采取强硬的方式<br>
	 * 使用各种方法，知道尝试到读到数据为止，然后根据Bean的数据类型，进行相应的转换<br>
	 * 如果尝试完了（总共7次），还是不能得到数据，那么抛个异常出来，没办法了
	 * 
	 * @param key
	 *            当前单元格对应的Bean字段
	 * @param fields
	 *            Bean所有的字段数组
	 * @param cell
	 *            单元格对象
	 * @param e
	 * @throws Exception
	 */
	public void readCellContent2003(String key, Field[] fields, HSSFCell cell,
                                    E e) throws Exception {
		DecimalFormat df = new DecimalFormat("#");
		Object o = null;
		try {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_BOOLEAN:
				o = cell.getBooleanCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				o =df.format(cell.getNumericCellValue());
				break;
			case XSSFCell.CELL_TYPE_STRING:
				o = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				o = cell.getErrorCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				o = null;
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				o = cell.getCellFormula();
				break;
			default:
				o = null;
				break;
			}

			if (o == null)
				return;

			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals(key)) {
					if (field.getType().equals(Date.class)) {
						if (o.getClass().equals(Date.class)) {
							field.set(e, o);
						} else {
							field.set(e, sdf.parse(o.toString()));
						}
					} else if (field.getType().equals(String.class)) {
						if (o.getClass().equals(String.class)) {
							field.set(e, o);
						} else {
							field.set(e, o.toString());
						}
					} else if (field.getType().equals(Long.class)) {
						if (o.getClass().equals(Long.class)) {
							field.set(e, o);
						} else {
							field.set(e, Long.parseLong(o.toString()));
						}
					} else if (field.getType().equals(Integer.class)) {
						if (o.getClass().equals(Integer.class)) {
							field.set(e, o);
						} else {
							field.set(e, Integer.parseInt(o.toString()));
						}

					}
				} else if (field.getType().equals(BigDecimal.class)) {
					if (o.getClass().equals(BigDecimal.class)) {
						field.set(e, o);
					} else {
						field.set(e, BigDecimal.valueOf(Double.parseDouble(o
								.toString())));
					}
				} else if (field.getType().equals(Boolean.class)) {
					if (o.getClass().equals(Boolean.class)) {
						field.set(e, o);
					} else {
						field.set(e, Boolean.parseBoolean(o.toString()));

					}
				} else if (field.getType().equals(Float.class)) {
					if (o.getClass().equals(Float.class)) {
						field.set(e, o);
					} else {
						field.set(e, Float.parseFloat(o.toString()));
					}
				} else if (field.getType().equals(Double.class)) {
					if (o.getClass().equals(Double.class)) {
						field.set(e, o);
					} else {
						field.set(e, Double.parseDouble(o.toString()));
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// 如果还是读到的数据格式还是不对，只能放弃了
			if (etimes > 7) {
				throw ex;
			}
			etimes++;
			if (o == null) {
				readCellContent2003(key, fields, cell, e);
			}
		}
	}

	/**
	 * 
	 * @param file
	 * @param index
	 *            1 2007 2 2003
	 * @return
	 * @throws Exception
	 */
	public List<E> improtExcel(File file) throws Exception {
		List<E> list = new ArrayList<E>();
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			list = readFromFile2003(file);
		} else if ("xlsx".equals(extension)) {
			list = readFromFile(file);
		} else {
			throw new IOException("不支持的文件类型");
		}

		return list;
	}

	/**
	 * 
	 * @param list
	 * @param index
	 *            1 2007 2 2003
	 * @return
	 * @throws Exception
	 */
	public  <T> void exportExcel(List<T> list, Integer index,
                                       String title) throws Exception {
		switch (index) {
		case 1:
			Workbook wb = getWorkBook(list, title);
			FileOutputStream output=new FileOutputStream("C:/Users/Administrator/Desktop/场所信息11.xlsx");
			wb.write(output);  
			output.flush();  
			break;
		case 2:
			HSSFWorkbook wb1 = getWorkBook2003(list, title);
			FileOutputStream output1=new FileOutputStream("C:/Users/Administrator/Desktop/场所信息123.xls");
			wb1.write(output1);  
			output1.flush(); 
			break;
		default:
			break;
		}

	}
	
	/**
	 * 
	 * @param list
	 * @param index
	 *            2007 
	 * @return
	 * @throws Exception
	 */
	public  <T> String exportExcel(List<T> list,String title,String savepath,String filename,String parentUrl) throws Exception {
		    String path=savepath+File.separator+filename+".xlsx";
			Workbook wb = getWorkBook(list, title,parentUrl);
			FileOutputStream output=new FileOutputStream(path);
			wb.write(output);  
			output.flush();  
		return path;

	}
	
	
	/**
	 * 
	 * @param list
	 * @param index
	 *            1 2007 2 2003
	 * @return
	 * @throws Exception
	 */
	public  <T>String  exportExcel(List<T> list,
                                       String title,String path,String filename) throws Exception {
		String savePath=null;
		Workbook wb = getWorkBook2(list, title);
		savePath=path+File.separator+filename+".xlsx";
		FileOutputStream output=new FileOutputStream(savePath);
		wb.write(output);  
		output.flush();  
		return savePath;

	}
	
	
	
	
	/**
	 * 
	 * @param list
	 * @param index
	 *            1 2007 2 2003
	 * @return
	 * @throws Exception
	 */
	public  <T> void exportExcel(List<T> list, Integer index,
                                       String title, HttpServletResponse response) throws Exception {
		switch (index) {
		case 1:
			Workbook wb = getWorkBook(list, title);
			ResponseUtil.excel20072(response, wb, TimeUtils.getymd() + ".xlsx");
			break;
		case 2:
			HSSFWorkbook wb1 = getWorkBook2003(list, title);
			ResponseUtil.excel2003(response, wb1, TimeUtils.getymd() + ".xls");
			break;
		default:
			break;
		}

	}
	
	
	
	public static void main(String[] args) throws Exception {  

      
//		List<CsBean> list = new ArrayList<CsBean>();
//		for(int i=1;i<6123;i++) {
//			CsBean c = new CsBean();
//			c.setName("张三"+i);
//			c.setAge(1+i);
//			list.add(c);
//		}
//		String path=new ExcelUtil(new CsBean()).exportExcel(list, "中国","C:\\Users\\16472\\Desktop","2012xx",null);
//		System.out.println(path);
//		List<CsBean> list2 = new ArrayList<CsBean>();
//		for(int i=1;i<30001;i++) {
//			CsBean c = new CsBean();
//			c.setName("历史"+i);
//			c.setAge(1+i);
//			list2.add(c);
//		}
//		System.out.println(new ExcelUtil(new CsBean()).exportExcel(list2, "中国","C:\\Users\\16472\\Desktop","2012xx",path));
	}

}
