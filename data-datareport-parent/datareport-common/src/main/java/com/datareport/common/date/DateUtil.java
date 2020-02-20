package com.datareport.common.date;









import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 时间转化工具
 * 
 * @author Administrator
 *
 */
public class DateUtil
{
    
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";
    
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS_ORACLE = "yyyy-mm-dd HH24:MM:SS";
    
    public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    
    public static final String DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";
    
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
    
    public static final String DATE_FORMAT_YYYY = "yyyy";
    
    public static final String DATE_FORMAT_EN_A_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
    
    public static final String DATE_FORMAT_EN_A_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
    
    public static final String DATE_FORMAT_EN_A_YYYYMMDDHH = "yyyy/MM/dd HH";
    
    public static final String DATE_FORMAT_EN_A_YYYYMMDD = "yyyy/MM/dd";
    
    public static final String DATE_FORMAT_EN_A_YYYYMM = "yyyy/MM";
    
    public static final String DATE_FORMAT_EN_A_YYYY = "yyyy";
    
    public static final String DATE_FORMAT_EN_B_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DATE_FORMAT_EN_B_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    
    public static final String DATE_FORMAT_EN_B_YYYYMMDDHH = "yyyy-MM-dd HH";
    
    public static final String DATE_FORMAT_EN_B_YYYYMMDD = "yyyy-MM-dd";
    
    public static final String DATE_FORMAT_EN_B_YYYYMM = "yyyy-MM";
    
    public static final String DATE_FORMAT_EN_B_YYYY = "yyyy";
    
    public static final String DATE_FORMAT_CN_YYYYMMDDHHMMSS = "yyyy'年'MM'月'dd'日' HH'时'mm'分'ss'秒'";
    
    public static final String DATE_FORMAT_CN_YYYYMMDDHHMM = "yyyy'年'MM'月'dd'日' HH'时'mm'分'";
    
    public static final String DATE_FORMAT_CN_YYYYMMDDHH = "yyyy'年'MM'月'dd'日' HH'时'";
    
    public static final String DATE_FORMAT_CN_YYYYMMDD = "yyyy'年'MM'月'dd'日'";
    
    public static final String DATE_FORMAT_CN_YYYYMM = "yyyy'年'MM'月'";
    
    public static final String DATE_FORMAT_CN_YYYY = "yyyy'年'";
    
    /** 匹配年月日字符串, 如: "2011-01-01", "2011/01/10", "2011.03.21", "2011 11 01", "20111210" */
    public static String MATCH_YEAR_MONTH_DAY = "(19|20)\\d\\d([- /.]?)(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])";
    
    
    
    /**
     * 根据条件获取时间
     */
	public static String getindex(String index){
		     Date d = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat(index);
	        return sdf.format(d);
	}
	
    /**
     * 获取明年年
     * 
     * @return
     */
    public static String getsub(int index, String pamment)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, +index);
        SimpleDateFormat format = new SimpleDateFormat(pamment);
        String time = format.format(c.getTime());
        return time;
    }
    
    /**
	 * 当前日期既为本月第一天 
	 * @return
	 */
	public static String first(){
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    //获取当前月第一天：
	    Calendar c = Calendar.getInstance();
	    c.add(Calendar.MONTH, 0);
	    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
	    String first = format.format(c.getTime());
//	    StringBuffer s = new StringBuffer(first);
//	    s.append(" ");
//	    s.append("00:00");
	   return first;
	}
	
	
	/**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth1(int year, int month) {     
        Calendar cal = Calendar.getInstance();   
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份 
        cal.set(Calendar.MONTH, month-1); 
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数 
        cal.set(Calendar.DAY_OF_MONTH,firstDay);  
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());  
    }
    
    
    /**
     * 获取指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
     public static String getLastDayOfMonth1(int year, int month) {     
         Calendar cal = Calendar.getInstance();     
         //设置年份  
         cal.set(Calendar.YEAR, year);  
         //设置月份  
         cal.set(Calendar.MONTH, month-1); 
         //获取某月最大天数
         int lastDay = cal.getActualMaximum(Calendar.DATE);
         //设置日历中月份的最大天数  
         cal.set(Calendar.DAY_OF_MONTH, lastDay);  
         //格式化日期
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
         return sdf.format(cal.getTime());
     }
	
	/**
	 * 时间转换
	 * @param s
	 * @return
	 */
	public static String dtime(Date s) {

		if (s != null) {
			SimpleDateFormat dateformat1 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String a1 = dateformat1.format(s);
			return a1;
		} else {
			return null;
		}

	}
	
	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static String last(){
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   Calendar ca = Calendar.getInstance();
		    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		    String last = format.format(ca.getTime());
//		    StringBuffer s = new StringBuffer(last);
//		    s.append(" ");
//		    s.append("23:59");
		return last;
	}
    
    /**
     * 计算剩下的是时间
     * @param end
     * @return
     */
    public static Long calculation(Long end)
    {
    	Long cs = System.currentTimeMillis();
    	Long l = end-cs;
        return l;
    }
    
    
//   public static void main(String[] args)
//    {
//	   long between = 1526272729878l -System.currentTimeMillis();;
//	  long day = between / (24 * 60 * 60 * 1000);
//      long hour = (between / (60 * 60 * 1000) - day * 24);
//      long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//      long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//      long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
//              - min * 60 * 1000 - s * 1000);
//      System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
//              + "毫秒");
//    }
    
    public static Long getTime(String date, String index)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(index);
        Date d = null;
        try
        {
            d = sdf.parse(date);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d.getTime();
    }
    
    /**
     * 获取当前系统时间，加上间隔以后的时间
     * @param index
     * @return
     */
    public static Long getNextDay(int index) {
	     Date date = new Date();
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(date);
       calendar.add(Calendar.DAY_OF_MONTH, +index);//+1今天的时间加一天
       date = calendar.getTime();
       return date.getTime();
   }
    
    /**
     * 根据时间获取字符串
     * @return
     */
    public static String getmonthod(String index, String data){
    	String mothod=null;
    	try {
    		 SimpleDateFormat dateformat = new SimpleDateFormat(index);//自定义时间格式
    		 Date d = dateformat.parse(data);//String转Date
    		 SimpleDateFormat sdf2= new SimpleDateFormat("M");
    		 mothod = sdf2.format(d);
		} catch (Exception e) {
		}
		return mothod;
    	
    }
    
    
    /**
     * 转时间戳
     * 
     * @param user_time
     * @return
     */
    public static Long getTime1()
    {
        Long l = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date d;
        try
        {
            d = sdf.parse(getdate2());
            l = d.getTime();
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return l;
    }
    
    
    /**
     * 转时间戳
     * 
     * @param user_time
     * @return
     */
    public static Long getTime3()
    {
        Long l = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try
        {
            d = sdf.parse(getdate2());
            l = d.getTime();
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return l;
    }
    
    /**
     * 获取年月日
     * 
     * @return
     */
    public static String getdate()
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        return sdf.format(d);
    }
    
    /**
     * 获取年月日
     * 
     * @return
     */
    public static String getdate1()
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
    
    /**
     * 获取年月
     */
    public static String getdate2()
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(d);
    }
    
    /**
     * 获取上个月年月日
     * 
     * @return
     */
    public static String sgydate()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        return time;
    }
    
    /**
     * 获取上个月年月日
     * 
     * @return
     */
    public static String sgydate(int index)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, index);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        return time;
    }
    
    /**
     * 获取上个月年月日
     * 
     * @return
     */
    public static Long sgydate(int index, String paramt)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, index);
        return c.getTime().getTime();
    }
    
    /**
     * 获取当前年份
     */
    public static String getNowYear()
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(d);
    }
    
    /**
     * 获取时间年月日
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(date);
    }
    
    
    /**
     * 获取时间年月日
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate3(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }
    
    /**
     * 获取时间年月日
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate1(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return simpleDateFormat.format(date);
    }
    
    /**
     * 获取时间年月日时分秒
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate4(long timeStamp, String inx)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inx);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 追加 000
     * 
     * @param timeStamp
     * @return
     */
    public static Date timeStampToDate5(Date timeStamp)
    {
    	Calendar cal1 = Calendar.getInstance();
		cal1.setTime(timeStamp);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		return cal1.getTime();
    }
    
    
    /**
     * 追加 000
     * 
     * @param timeStamp
     * @return
     */
    public static Long timeStampToDate7(String timeStamp, String index)
    {
    	 SimpleDateFormat sdf = new SimpleDateFormat(index);
    	  Date d = null;
          try
          {
              d = sdf.parse(timeStamp);
          }
          catch (ParseException e)
          {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
    	Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		
		return cal1.getTimeInMillis();
    }
    
    
    /**
     * 追加 59 59  00
     * 
     * @param timeStamp
     * @return
     */
    public static Long timeStampToDate8(String timeStamp, String index)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(index);
  	  Date d = null;
        try
        {
            d = sdf.parse(timeStamp);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, 23);
		cal1.set(Calendar.MINUTE, 59);
		cal1.set(Calendar.SECOND, 59);
		cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTimeInMillis();
    }
    
    
    /**
     * 追加 59 59  00
     * 
     * @param timeStamp
     * @return
     */
    public static Date timeStampToDate6(Date timeStamp)
    {
    	Calendar cal1 = Calendar.getInstance();
		cal1.setTime(timeStamp);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, 23);
		cal1.set(Calendar.MINUTE, 59);
		cal1.set(Calendar.SECOND, 59);
		cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
    }
    
    
    /**
     * 获取时间年月
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate2(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return simpleDateFormat.format(date);
    }
    
    /**
     * 获取时间年
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToYear(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }
    
    /**
     * 获取时间月
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToMonth(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        return simpleDateFormat.format(date);
    }
    
    /**
     * 获取时间日
     * 
     * @param timeStamp
     * @return
     */
    public static String timeStampToR(long timeStamp)
    {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(date);
    }
    
    /**
     * 转时间戳
     * 
     * @param user_time
     * @return
     */
    public static Long getTime2(String m)
    {
        Long l = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        StringBuffer s = new StringBuffer(getNowYear());
        s.append("/");
        s.append(m);
        Date d;
        try
        {
            d = sdf.parse(s.toString());
            l = d.getTime();
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return l;
    }
    
    /**
     * <code>Date</code> Description:获取当前时间的方法，统一使用该接口方便以后转换实现方式
     * 
     * @author：kucs Date 2017年5月13日 下午5:25:08
     * @return
     */
    public static Date now()
    {
        return new Date();
    }
    
    /**
     * <code>String</code> Description:获取当前时间字符串
     * 
     * @author：kucs Date 2017年5月13日 下午5:25:28
     * @param pattern 格式化样式
     * @return 经过格式化的当前时间字符串
     */
    public static String now(String pattern)
    {
        if (pattern == null || "".equals(pattern.trim()))
        {
            pattern = DATE_FORMAT_YYYYMMDDHHMMSS;
        }
        return formatDate(now(), pattern);
    }
    
    /**
     * <code>String</code> Description:格式化日志为指定样式的字符串
     * 
     * @author：kucs Date 2017年5月13日 下午5:26:09
     * @param date 给定的日期对象
     * @param pattern 格式化采用的样式
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String pattern)
    {
        if (null == pattern || pattern.length() == 0)
        {
            pattern = DATE_FORMAT_EN_A_YYYYMMDDHHMMSS;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    
    /**
     * <code>String</code> Description:对输入的日期字符串进行格式化
     * 
     * @author：kucs Date 2017年5月13日 下午5:26:43
     * @param strDate 需要进行格式化的日期字符串
     * @param pattern 格式化采用的样式
     * @return 格式化后的字符串
     */
    public static String formatDate(String strDate, String pattern)
    {
        if (strDate == null || strDate.trim().equals(""))
        {
            return "";
        }
        String formatStr = parseDateFormat(strDate);
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(strDate));
            formatter = new SimpleDateFormat(pattern);
            return formatter.format(calendar.getTime());
        }
        catch (Exception e)
        {
            // LoggerUtil.debug(DateUtil.class, "转换日期字符串格式时出错;" + e.getMessage());
            return "";
        }
    }
    
    /**
     * <code>Date</code> Description:将日期字符串转换为日期对象.
     * 
     * @author：kucs Date 2017年5月13日 下午5:27:47
     * @param strDate
     * @return 转换后的日期
     */
    public static Date parseDate(String strDate)
    {
        if (strDate == null || strDate.trim().equals(""))
        {
            return null;
        }
        String formatStr = parseDateFormat(strDate);
        return parseDate(strDate, formatStr);
    }
    
    /**
     * 解析日期字符串格式
     * 
     * @param strDate
     * @return
     */
    private static String parseDateFormat(String strDate)
    {
        String formatStr = "";
        if (Pattern.matches("^[1-9]\\d{3}(0[1-9]|1[012])$", strDate))
        {
            formatStr = DATE_FORMAT_YYYYMM;
        }
        else if (Pattern.matches("^[1-9]\\d{3}$", strDate))
        {
            formatStr = "yyyy";
        }
        else if (Pattern.matches("^\\d\\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$", strDate))
        {
            formatStr = "yyMMdd";
        }
        else if (Pattern.matches("^[1-9]\\d{3}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$", strDate))
        {
            formatStr = DATE_FORMAT_YYYYMMDD;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(/)(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01])$", strDate))
        {
            formatStr = DATE_FORMAT_EN_A_YYYYMMDD;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(-)(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01])$", strDate))
        {
            formatStr = DATE_FORMAT_EN_B_YYYYMMDD;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(.)(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01])$", strDate))
        {
            formatStr = "yyyy.MM.dd";
        }
        else if (Pattern.matches("^[1-9]\\d{3}( )(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01])$", strDate))
        {
            formatStr = "yyyy MM dd";
        }
        else if (Pattern.matches("^[1-9]\\d{3}(年)(0[1-9]|1[012])(月)(0[1-9]|[12][0-9]|3[01])(日)$", strDate))
        {
            formatStr = DATE_FORMAT_CN_YYYYMMDD;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(-)(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-3])(:)([0-5][0-9])$", strDate))
        {
            formatStr = DATE_FORMAT_EN_B_YYYYMMDDHHMM;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])([01][0-9]|2[0-3])([0-5][0-9]){2}$", strDate))
        {
            formatStr = DATE_FORMAT_YYYYMMDDHHMMSS;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(/)(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-3])(:)([0-5][0-9])\\5([0-5][0-9])$", strDate))
        {
            formatStr = DATE_FORMAT_EN_A_YYYYMMDDHHMMSS;
        }
        else if (Pattern.matches("^[1-9]\\d{3}(-)(0[1-9]|1[012])\\1(0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-3])(:)([0-5][0-9])\\5([0-5][0-9])$", strDate))
        {
            formatStr = DATE_FORMAT_EN_B_YYYYMMDDHHMMSS;
        }
        else
        {
            // LoggerUtil.error(DateUtil.class, "无法识别的字符串格式:" + strDate);
        }
        return formatStr;
    }
    
    
	/**
	 * 根据月份计算日期
	 * @param moth
	 * @param lt
	 */
	public static Long get(Integer moth, Long lt){
        Date date = new Date(lt);
        Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(calendar.MONTH, moth);  //将月份设置进去
		return calendar.getTime().getTime();
	}
	
    
    /**
     * 将指定格式的日期字符串转换为日期对象.
     * 
     * @param strDate 需要进行转换的日期字符串
     * @param pattern 日期字符串的格式
     * @return 日期对象
     */
    public static Date parseDate(String strDate, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            return formatter.parse(strDate);
        }
        catch (ParseException e)
        {
            // LoggerUtil.error(DateUtil.class, "解析和转换日期字符串出错,检查字符串是否是日期格式." + e);
            return null;
        }
    }
    
    /**
     * @Description：将指定格式的日期字符串转换为日期对象.
     * @author kucs
     * @Date 2017年5月16日上午11:10:39
     * @param strDate 需要进行转换的日期字符串
     * @param pattern 日期字符串的格式
     * @param
     * @param Exception
     * @return Date
     * @throws
     */
    public static Date parseDateWithException(String strDate, String pattern)
        throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            return formatter.parse(strDate);
        }
        catch (ParseException e)
        {
            throw new Exception("解析和转换日期字符串出错,检查字符串是否是日期格式.", e);
        }
    }
    
    /**
     * 获取偏移时间
     * 
     * @param date
     * @param trewingStr 传入格式:-mm:ss or -ss 前面必须要有符号
     * @return
     */
    public static Date getSkewingTime(Date date, String trewingStr)
    {
        if (trewingStr == null)
            return null;
        trewingStr = trewingStr.trim();
        String tmp = "";
        boolean isDesc = false;
        int minute = 0, second = 0;
        if (trewingStr.indexOf('-') >= 0)
            isDesc = true;
        // 去掉前面的符号
        trewingStr = trewingStr.substring(1, trewingStr.length());
        if (trewingStr.indexOf(':') >= 0)
        {
            tmp = trewingStr.substring(0, trewingStr.indexOf(':'));
            minute = Integer.parseInt(tmp);
            tmp = trewingStr.substring(trewingStr.indexOf(':') + 1);
            second = Integer.parseInt(tmp);
        }
        else
        {
            second = Integer.parseInt(trewingStr);
        }
        long trewing = (minute * 60 + second) * 1000;
        if (isDesc)
        {
            trewing = -trewing;
        }
        Date datetmp = new Date(date.getTime() + trewing);
        return datetmp;
    }
    
    /**
     * <code>Date</code> Description:获取时间所在月的第一天
     * 
     * @author：kucs Date 2017年5月13日 下午5:16:26
     * @param date
     * @return
     */
    public static Date getFirstDateAtMonth(Date date)
    {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.set(Calendar.DATE, time.getActualMinimum(Calendar.DAY_OF_MONTH));
        return time.getTime();
    }
    
    /**
     * <code>Date</code> Description:获取月的最后一天
     * 
     * @author：kucs Date 2017年5月13日 下午5:17:16
     * @param date
     * @return
     */
    public static Date getLastDateAtMonth(Date date)
    {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.set(Calendar.DATE, time.getActualMaximum(Calendar.DAY_OF_MONTH));
        return time.getTime();
    }
    
    /**
     * 获取当月最后一天59分
     * 
     * @return
     */
    public static Long getlast()
    {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        // 将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 23);
        // 将分钟至0
        ca.set(Calendar.MINUTE, 59);
        // 将秒至0
        ca.set(Calendar.SECOND, 59);
        // 将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        // 获取本月最后一天的时间戳
        return ca.getTimeInMillis();
    }
    
    /**
     * 获取上月月最后一天59分
     * 
     * @return
     */
    public static Long getlast1()
    {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1 - 1);
        // 将小时至0
        ca.set(Calendar.HOUR_OF_DAY, 23);
        // 将分钟至0
        ca.set(Calendar.MINUTE, 59);
        // 将秒至0
        ca.set(Calendar.SECOND, 59);
        // 将毫秒至0
        ca.set(Calendar.MILLISECOND, 0);
        // 获取本月最后一天的时间戳
        return ca.getTimeInMillis();
    }

    
    /**
     * 获取当月第一天0点0分0秒
     * 
     * @return
     */
    public static Long getone()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        // 将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        c.set(Calendar.MINUTE, 0);
        // 将秒至0
        c.set(Calendar.SECOND, 0);
        // 将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }
    
    /**
     * 获取上月月第一天0点0分0秒
     * 
     * @return
     */
    public static Long getone1()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        // 将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        c.set(Calendar.MINUTE, 0);
        // 将秒至0
        c.set(Calendar.SECOND, 0);
        // 将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }
    
    /**
     * 获取当前时间的Timestamp
     * 
     * @return
     */
    public static Timestamp getNowTimestamp()
    {
        return new Timestamp(new Date().getTime());
    }
    
    /**
     * 获取指定时间(字符串)的Timestamp
     * 
     * @param timestr
     * @return
     */
    public static Timestamp parseTimestamp(String timestr)
    {
        if (timestr == null || timestr.equals(""))
            return null;
        return new Timestamp(parseDate(timestr).getTime());
    }
    
    /**
     * 获取指定时间(Date)的Timestamp
     * 
     * @param time
     * @return
     */
    public static Timestamp parseTimestamp(Date time)
    {
        if (time == null)
            return null;
        return new Timestamp(time.getTime());
    }
    
    /**
     * @param sInitY
     * @param sInitM
     * @param sEndY
     * @param sEndM
     * @return
     */
    public static String[] getYMs(String sInitY, String sInitM, String sEndY, String sEndM)
    {
        int initM = Integer.parseInt(sInitM);
        int initY = Integer.parseInt(sInitY);
        int endM = Integer.parseInt(sEndM);
        int endY = Integer.parseInt(sEndY);
        String[] YMs = null;
        int totalM = (endY - initY) * 12 + (endM - initM) + 1;
        YMs = new String[totalM];
        Date initDate = parseDate(sInitY + sInitM);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < totalM; i++)
        {
            cal.setTime(initDate);
            cal.add(Calendar.MONTH, i);
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYYMM);
            YMs[i] = "_" + formatter.format(cal.getTime());
        }
        return YMs;
    }
    
    /**
     * <code>Date</code> Description:计算距离指定日期若干天的日期
     * 
     * @author：kucs Date 2017年5月13日 下午5:20:06
     * @param src
     * @param dayOffset 间隔天数
     * @return
     */
    public static Date getOffsetDay(Date src, int dayOffset)
    {
        Calendar now = Calendar.getInstance();
        now.setTime(src);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + dayOffset);
        return now.getTime();
    }
    
    /**
     * <code>Date</code> Description:计算距离指定日期若干月的日期
     * 
     * @author：kucs Date 2017年5月13日 下午5:23:47
     * @param src
     * @param monthOffset 间隔月数
     * @return
     */
    public static Date getOffsetMonth(Date src, int monthOffset)
    {
        Calendar now = Calendar.getInstance();
        now.setTime(src);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + monthOffset);
        return now.getTime();
    }
    
    /**
     * 获取当前时间指定区域的值
     * 
     * @param field 时间区域(Calendar.YEAR or Calendar.MONTH or Calendar.DATE, etc.)
     * @return 时间值
     */
    public static int getNowField(int field)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(field);
    }
    
    /**
     * 判断时间格式，验证时间是否符合格式
     * 
     * @throws ParseException
     * */
    public static boolean isValidDate(String strDate, String pattern)
    {
        boolean convertSuccess = true;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            formatter.setLenient(false);
            formatter.parse(strDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return convertSuccess;
    }
    
    
    /**
     * 获取当前年月日
     * 
     * @throws ParseException
     * */
    public static String getyymmdd()
    {
    	 Date d = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
         String dateNowStr = sdf.format(d);
          return dateNowStr;
    }
    
    
    public static void main(String[] args){
    }
    
}
