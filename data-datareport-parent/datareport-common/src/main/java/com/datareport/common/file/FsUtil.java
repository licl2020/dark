package com.datareport.common.file;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
 
/**
 * @author yangxin_ryan
 * 机器磁盘文件的操作
 */
@Component
@Log4j
public class FsUtil {
 
 
    /**
     * 判断文件是否存在
     * @param file
     */
    public void checkFileExists(File file) {
        if (file.exists()) {
        	log.info("待写入文件存在");
        } else {
        	log.info("待写入文件不存在, 创建文件成功");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
    }
    
    /**
        * 创建文件夹
     * @param savepath
     * @param destDirName
     * @return
     */
    public static String createDir(String savepath,String destDirName)
    {
        String path =savepath + destDirName; // 路径
        File dir = new File(path);
        if (dir.exists())
        {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return null;
        }
        if (!destDirName.endsWith(File.separator))
        {
            destDirName = destDirName + File.separator;
        }
        // 创建目录
        if (dir.mkdirs())
        {
            System.out.println("创建目录" + destDirName + "成功！");

            return path+ "/";
        }
        else
        {
            System.out.println("创建目录" + destDirName + "失败！");
            return null;
        }
    }
 
    /**
     * 判断文件夹是否存在
     * @param file
     */
    public static  void checkDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                log.info("目录存在");
            } else {
            	log.info("同名文件存在, 不能创建");
            }
        } else {
        	log.info("目录不存在，创建目录");
            file.mkdir();
        }
    }
}
