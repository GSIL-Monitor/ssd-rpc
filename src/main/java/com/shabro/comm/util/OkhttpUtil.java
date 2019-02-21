package com.shabro.rpc.util;

import com.google.gson.Gson;
import okhttp3.*;
//import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by liuhong on 2017/12/8.
 */
public class OkhttpUtil {

    /**
     *
     * @param url
     * @param formBody
     * @param T
     * @param <T>
     * @return json to gson
     * @throws IOException
     */
    public static  <T> T post(String url, RequestBody formBody,Class<T> T) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).post(formBody)
                .build();
        Response response  = client.newCall(request).execute();
        if(response.isSuccessful()){
            String resJson = response.body().string();
            T obj =  new Gson().fromJson(resJson,T);
            return  obj;
        }
        return null;
    }
    /**
     *
     * @param url
     * @param formBody
     * @param T
     * @param <T>
     * @return json to gson
     * @throws IOException
     */
    public static  <T> T get(String url, Map<String ,Object> paramBody ,Map<String ,Object> paramHeard ,Class<T> T) throws IOException {
        OkHttpClient client = new OkHttpClient();

        StringBuilder getDate = new StringBuilder("");
        if(paramBody!=null){
            List<String> list = new ArrayList<>();
            list.addAll(paramBody.keySet());
            for (int i=0;i<list.size();i++){
                if(i==0){
                    getDate.append("?").append(list.get(i)).append("=").append(paramBody.get(list.get(i)));
                }
                else {
                    getDate.append("&").append(list.get(i)).append("=").append(paramBody.get(list.get(i).toString()));
                }
            }
        }
        //System.out.println(url+getDate.toString());
        List<String> list = new ArrayList<String>();
        list.addAll(paramHeard.keySet());
        Request request = null;
        if(paramHeard!=null){
            request = new Request.Builder()
                    .url(url+getDate.toString())
                    .header(list.get(0), paramHeard.get(list.get(0)).toString())
                    .build();
        }
        else {
            request = new Request.Builder().url(url+getDate.toString()).build();
        }
        //System.out.println(url+getDate.toString());
        Response response  = client.newCall(request).execute();
        if(response.isSuccessful()){
            String resJson = response.body().string();
            System.out.println(resJson);
            T obj =  new Gson().fromJson(resJson,T);
            return  obj;
        }
        return null;
    }

    //
    public static  RequestBody body(Map<String ,String> data){
        Map<String ,Object> param = new HashMap<String ,Object>();
        String paramStr = new Gson().toJson(data);
        RequestBody formBody = new FormBody.Builder().add("param", paramStr).build();
        return formBody;
    }
   /* @Test
    public void test() throws IOException {
       Map<String,Object> map = new HashMap<String, Object>();
        map.put("a",1);
        map.put("b", 3);
        get("http://www.baidu.com",map , Objects.class);
    }*/

    public static class StringUtils {

        public static boolean  isBlank(String str){
            boolean isBlank = false;
             if(str == null || str.length() == 0 ||str.equals("") )
              return true;

             return isBlank;
        }

        public static boolean  StringsIsBlank(String ... str){
            if(str ==null){
                return true;
            }
            int lenth = str.length;
            for(int i=0;i<lenth;i++){
                if(str[i] == null || "".equals(str[i].trim())){
                    return true;
                }
            }
            return false;
        }

        public static boolean  isBlank(Integer str){
            boolean isBlank = false;
            if(str == null  )
                return true;

            return isBlank;
        }

        public static String getNoNull(String str){
            str = str==null ?"" : str;

            return str;
        }

        public static long getHalfTime(){
            Calendar ca = Calendar.getInstance();

            ca.set(Calendar.MILLISECOND, 0);
            ca.set(Calendar.SECOND, 0);
            int min = ca.get(Calendar.MINUTE);
            if(min <=30){
                ca.set(Calendar.MINUTE, 0);
            }else{
                ca.set(Calendar.MINUTE, 30);
            }

            long halft = ca.getTimeInMillis();
            return halft;

        }
    }
}
