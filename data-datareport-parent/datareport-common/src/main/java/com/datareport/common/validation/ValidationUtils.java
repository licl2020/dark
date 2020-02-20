package com.datareport.common.validation;





import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.datareport.common.String.StringUtil;

public class ValidationUtils {

	public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
	//public static final String PHONE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//手机号
	public static final String PHONE = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";//手机号
	public static final String MOBILE = "^[0][1-9]{2,3}-[0-9]{5,10}$";//座机
	public static final String Name = "^[\u4e00-\u9fa5\\a-zA-Z]+$";//姓名校验(正则有忽视数字的bug)所以同时需要满足hasdigit方法
	
	public static final String SZ_ZM = "^[A-Za-z0-9]+$";//数字字母
	
	public static final String SZ = "^(0|[1-9][0-9]*)$";//数字
	
	//规范的经度表示法ddd°mm′ss.sss″  
	public static final String J_D="/^((\\d|[1-9]\\d|1[0-7]\\d)[°](\\d|[0-5]\\d)[′](\\d|[0-5]\\d)(\\.\\d{1,6})?[\\″]$)|(180[°]0[′]0[\\″]$)/";
	//有分没秒的表示法ddd°mm.mmm′ 
	public static final String J_D2="/^((\\d|[1-9]\\d|1[0-7]\\d)[°](\\d|[0-5]\\d)(\\.\\d{1,6})?[′]$)|(180[°]0[′]$)/";
	//只有度的表示法 ddd.ddd°  
	public static final String J_D3="/^((\\d|[1-9]\\d|1[0-7]\\d)(\\.\\d{1,6})?[°]$)|(180[°]$)/";
	
	public static final String W_D="/^((\\d|[1-8]\\d)[°](\\d|[0-5]\\d)[′](\\d|[0-5]\\d)(\\.\\d{1,6})?[\\″]$)|(90[°]0[′]0[\\″]$)/";
	public static final String W_D2="/^((\\d|[1-8]\\d)[°](\\d|[0-5]\\d)(\\.\\d{1,6})?[′]$)|(90[°]0[′]$)/";
	public static final String W_D3="/^((\\d|[1-8]\\d)(\\.\\d{1,6})?[°]$)|(90[°]$)/";
	
	 /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$";
 
    /**
     * 正则表达式：验证港澳台证
     */
    public static final String REGEX_ID_HKMT = "^([HMhmCc]{1}[a-zA-Z0-9]{1}[0-9]{7}$)";
    // public static final String REGEX_ID_HKMT = "/([A-Z]{1,2}[0-9]{6}([0-9A]))|(^[1|5|7][0-9]{6}\\([0-9Aa]\\))|([A-Z][0-9]{9})/";
    /**
     * 正则表达式：验证护照
     */
//    public static final String REGEX_ID_PASSPORT = "/^[a-zA-Z0-9]{5,17}$/";)
    public static final String REGEX_ID_PASSPORT = "^([P|p|D|d|E|e|S|s|G|g]{1}[a-zA-Z0-9]{1}[0-9]{7}$)";
    /**
     * 正则表达式：验证军官证
     */
//    public static final String REGEX_ID_COO = " /^[a-zA-Z0-9]{7,21}$/";
    public static final String REGEX_ID_COO = " /[\\u4e00-\\u9fa5](字第){1}(\\d{4,8})(号?)$/";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
 
    //************************************网上找的，一般难用到***********************************
    /**
     * 检查字符串中是否还有HTML标签
     */
    public static final String HTMLTAGHAS = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />";
    /**
     * 检查IP是否合法
     */
    public static final String IPADRESS = "\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}";
    /**
     * 检查QQ号是否合法
     */
    public static final String QQCODE = "[1-9][0-9]{4,13}";
    /**
     * 检查邮编是否合法
     */
    public static final String POSTCODE = "[1-9]\\d{5}(?!\\d)";
    /**
     * 正整数
     */
    public static final String POSITIVE_INTEGER = "^[0-9]\\d*$";
    /**
     * 正浮点数
     */
    public static final String POSITIVE_FLOAT = "^[1-9]\\d*.\\d*|0.\\d*[0-9]\\d*$";
    /**
     * 整数或小数
     */
    public static final String POSITIVE_DOUBLE = "^[0-9]+(\\.[0-9]+)?$";
    /**
     * 年月日 2012-1-1,2012/1/1,2012.1.1
     */
    public static final String DATE_YMD = "^\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}$";

	
    /**
     * 校验证件格式
     * @param index
     * @param num
     * @return
     */
    public static boolean iscard(Integer index,String num){
      boolean f =true;
      if(StringUtil.isEmpty(index) || StringUtil.isEmpty(num)){
    	  index=1234567;
      }
    	switch (index) {

    	        //身份证 
				case 112:
					f=IdCardValidator.isValidatedAllIdcard(num);
					break;
				
				//浙江省居住证	
				case 116:
							
					break;
				
				//护照
				case 414:
					f=ishz(num);
					break;
					
				//台湾居民来往大陆通行证
				case 511:
					//f=ist(num);
					break;
				
				//港澳居民来往内地通行证
				case 516:
					f=isGa(num);
					break;
				
				//港澳台居民居住证
				case 902:
					//f=isGat(num);
					break;
					
				//其他证件
				case 990:
					
					break;
				
				//没有数据时默认
				case 0:
					
					break;
		
				default:
					f=false;
					break;
		}
    	return f;
    }
    
    
    
    /**
     * 护照
     * @param str
     * @return
     */
    public static boolean ishz(String str) {
    	if(Regular(str,REGEX_ID_PASSPORT)){
    		 return true; 
    	}else{
    		 return false; 
    	}
	  } 
    /**
     * 港澳通行证
     * @param str
     * @return
     */
    public static boolean isGa(String str) {
	    return Regular(str,REGEX_ID_HKMT); 
	  } 
    
    /**
     * 港澳通行证
     * @param str
     * @return
     */
    public static boolean isGat(String str) {
    	if(isGa(str) || ist(str)){
   		 return true; 
   	}else{
   		 return false; 
   	}
	  } 
    
    /**
     * 台湾通行证验证
     * @param str
     * @return
     */
    public static boolean ist(String str) {
    	if(Regular(str,REGEX_ID_HKMT)){
   		 return true; 
   	}else{
   		 return false; 
   	}
	  } 
	public static boolean isEmail(String str) {
	    return Regular(str,EMAIL); 
	  } 
	
	public static boolean isName(String str) {
	    return Regular(str,Name); 
	  } 
	
	public static boolean isMobile(String str) {
	    return Regular(str,MOBILE); 
	  } 
	
	public static boolean isTel(String str) {
		return Regular(str,PHONE); 
	} 
	
	public static boolean isSz_zm(String str) {
		return Regular(str,SZ_ZM); 
	} 
	
	public static boolean isSz(String str) {
		return Regular(str,SZ); 
	} 
	
	
	private static boolean Regular(String str , String pattern) {
		if(null == str || str.trim().length()<=0) {
			return false;
		}
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	//验证是否包含数字
	public static boolean HasDigit(String content) {
	    boolean flag = false;
	    Pattern p = Pattern.compile(".*\\d+.*");
	    Matcher m = p.matcher(content);
	    if (m.matches()) {
	        flag = true;
	    }
	    return flag;
	}
	
	/**
	 * 验证精度
	 * @param j
	 * @return
	 */
	public static boolean ValiJd(String j){
		boolean f;
		try {
			 String[] s1 = j.split("°");
			 String sb =s1[1];
			 if(!StringUtil.isEmpty(sb)){
				 String[] s2= sb.split("′");
				 String sb1 =s2[1];
				 if(!StringUtil.isEmpty(sb1)){  
					 String[] s3 = sb1.split("″");
					 Pattern p = Pattern.compile(J_D);
					 Matcher m = p.matcher(j);
	                 f = m.matches();  
	             }else{  
	            	 Pattern p = Pattern.compile(J_D2);
					 Matcher m = p.matcher(j);
					 f = m.matches(); 
	             }
			 }else{  
				 Pattern p = Pattern.compile(J_D3);
				 Matcher m = p.matcher(j);
				 f = m.matches();   
	         }
		} catch (Exception e) {
			f=false;
		}
		
		return f; 
		
		
	}
	
	public static void main(String[] args){
		String j = "100°40′36.40″";
		System.out.println(iscard(516,"hA1014962"));
	}
	
	
	/**
	 * 验证wei度
	 * @param w
	 * @return
	 */
	public static boolean ValiWd(String w){
		boolean f;
		try {
			 String[] s1 = w.split("°");
			 String sb =s1[1];
			 if(!StringUtil.isEmpty(sb)){
				 String[] s2= sb.split("′");
				 String sb1 =s2[1];
				 if(!StringUtil.isEmpty(sb1)){  
					 String[] s3 = sb1.split("″");
					 Pattern p = Pattern.compile(W_D);
					 Matcher m = p.matcher(w);
	                 f = m.matches();  
	             }else{  
	            	 Pattern p = Pattern.compile(W_D2);
					 Matcher m = p.matcher(w);
					 f = m.matches(); 
	             }
			 }else{  
				 Pattern p = Pattern.compile(W_D3);
				 Matcher m = p.matcher(w);
				 f = m.matches();   
	         }
		} catch (Exception e) {
			f=false;
		}
		
		return f; 
		
		
	}
	


}
	
	
