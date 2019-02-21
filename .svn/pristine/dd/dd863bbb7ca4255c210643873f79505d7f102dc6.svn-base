package com.shabro.comm.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by diluntech on 15/6/1.
 */
public class MD5 {

    public static String getMD5(String password){
            return DigestUtils.md5Hex(password);
    }

    public static String getMD5AndSalt(String password,String salt){
        return getMD5(password+getMD5(salt));
    }
}
