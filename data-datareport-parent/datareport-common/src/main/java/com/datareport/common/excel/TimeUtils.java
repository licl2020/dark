package com.datareport.common.excel;








import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


/**
 * 字符串<=======>时间戳 相互转换
 * 
 * @author Administrator
 *
 */
public class TimeUtils
{
    
    /**
     * 将字符串转为时间戳
     * 
     * @param user_time
     * @returnyyyy-MM-dd HH:m:ss
     */
    public static Long getTime(String user_time, String format)
    {
        Long re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        try
        {
            
            d = sdf.parse(user_time);
            re_time = d.getTime();
            
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }
    
    /**
     * 获取当前年月日
     * 
     * @return
     */
    public static String getymd()
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(d);
    }
    
    /**
     * <code></code>
     * 
     * @Description:根据源表名，compnay唯一标识符以及时间(年月YYYYMM)返回月度分表名
     * @author：kucs
     * @Date 2017年6月23日 上午10:10:08
     * @param company_udf2
     * @param datetime
     * @param srcTableName
     * @return
     * @throws Exception
     */
    public static String getTableName(String company_Id, String srcTableName)
        throws Exception
    {
        if (StringUtils.isEmpty(srcTableName))
            throw new Exception("源表名不能为空");
        if (StringUtils.isEmpty(company_Id))
            throw new Exception("单位唯一标识符不能为空");
        return srcTableName + "_" + company_Id;
        
    }
    
    /**
     * <code></code>
     * 
     * @Description:根据源表名，compnay唯一标识符以及时间(年月YYYYMM)创建月度分表
     * @author：kucs
     * @Date 2017年6月23日 上午10:07:32
     * @param company_udf2
     * @param datetime
     * @param srcTableName
     * @throws Exception
     */
    public void createTable(String company_udf2, String srcTableName)
        throws Exception
    {
        // service.createTables(getTableName(company_udf2, srcTableName), srcTableName);
    }
    
    private String tables;
    
    public String getTables()
    {
        return tables;
    }
    
    public void setTables(String tables)
    {
        this.tables = tables;
    }
    
}
