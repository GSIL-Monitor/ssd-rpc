package com.shabro.comm.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 转换工具
 * Created by vanya on 2017/8/10.
 */
public class AuthConvert {

    /**
     * 将认证服务器返回结果转为map(login)
     * 1.0
     * */
    public static Map loginResultTomap(String result){
        String [] str = result.split(",");
        Map map = new HashMap();
        map.put(str[0],str[1]);//token
        map.put(str[2],str[3]);//用户（app）id
        map.put(str[4],str[5]);//用户手机
        return map;
    }



    /**
     * 将认证服务器返回结果转为map(check)
     * 1.0
     * */
    public static Map checkResultTomap(String result){
        String [] str = result.split(",");
        Map map = new HashMap();
        map.put(str[0],str[1]);//结果
        map.put(str[2],str[3]);//用户手机
        map.put(str[4],str[5]);//用户（app）id
        map.put(str[6],str[7]);//用户类型（决定token表单的user_id填写的是手机号还是id,默认手机号）
        map.put(str[8],str[9]);//商城用户类型,APP可能为空
        map.put(str[10],str[11]);//mallUserId 商城用
        map.put(str[12],str[13]);//STATUS 200
        return map;
    }
}
