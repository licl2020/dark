package com.datareport.common.md5;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Md5Utils {

	   public static String getMD5Pwd(String username, String pwd) {
           // 加密算法MD5
           // salt盐 username + salt
           // 迭代次数
           String md5Pwd = new SimpleHash("MD5", pwd,
                   ByteSource.Util.bytes(username + "salt"), 2).toHex();
           return md5Pwd;
       }
	   
}
