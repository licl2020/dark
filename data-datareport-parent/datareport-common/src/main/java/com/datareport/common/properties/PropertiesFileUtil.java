package com.datareport.common.properties;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesFileUtil
{
    
    public static Properties getProperties(String name)
    {
        Properties p = new Properties();
        try
        {
//            InputStream inputStream = PropertiesFileUtil.class.getClassLoader().getResourceAsStream(name);
//            p.load(inputStream);
            p.load(new InputStreamReader(PropertiesFileUtil.class.getClassLoader().getResourceAsStream(name), "UTF-8"));    
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        return p;
    }
}
