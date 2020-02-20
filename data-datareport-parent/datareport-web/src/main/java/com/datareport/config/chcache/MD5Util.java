package com.datareport.config.chcache;






import java.security.MessageDigest;

/**
 * @Descrption:MD5加密工具
 * @author LICL
 * @Date 2017年6月13日 上午11:32:43 ======================================================
 * @Modify:
 * @author:
 * @Date 2017年6月13日上午11:32:43
 */
public class MD5Util
{
    private static final String MD5_KEY = "MD5";
    
    // private static final String MD5_KEY_TOKEN="gakm_MD5Util_LICL_28907";
    /***
     * MD5加密 生成32位md5码
     * 
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance(MD5_KEY);
        }
        catch (Exception e)
        {
            return "";
        }
        StringBuffer hexValue = new StringBuffer();
        try {
            // inStr += MD5_KEY_TOKEN;
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
          
            for (int i = 0; i < md5Bytes.length; i++)
            {
                int val = ((int)md5Bytes[i]) & 0xff;
                if (val < 16)
                {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
   
        return hexValue.toString();
    }
    
    public static void main(String[] args)
        throws Exception
    {
//        String str = "5D24152342AFB788C2342324152342AF" + DateUtil.now(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
//        System.out.println(str);
//        System.out.println("MD5后：" + md5Encode(str));
//        System.out.println("MD5后大写：" + md5Encode(str).toUpperCase());
    }
}
