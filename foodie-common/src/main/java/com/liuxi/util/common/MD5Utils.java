package com.liuxi.util.common;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * <p>
 * MD5加密
 * </P>
 * @author liu xi
 * @date 2022/4/17 4:42
 */
public class MD5Utils {

    /**
     * 对字符串进行md5加密
     * @param strValue
     * @return
     * @throws Exception
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
    }
}
