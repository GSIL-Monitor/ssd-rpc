package com.shabro.comm.util.httpclient;

import java.util.Map;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

import org.apache.http.Consts;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;


/**
 * Created by Administrator on 2016/11/26.
 */
@Service
public class HttpApiService {
    private static Logger log = Logger.getLogger("locus");

    @Autowired
    private HttpClientPool  httpCliPool;

    /**
     * token验证服务
     */
    public String checkByApi(String loginServerUrl,Map<String,Object> params) {
        String strURL = loginServerUrl + "/check?token=" + params.get("token") + "&appType=" + params.get("appType") + "&appId="+params.get("appId")+"&userType=" + params.get("userType");

        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            httpConn.disconnect();
            //System.out.println(buffer.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return buffer.toString();
    }

    /**
     * 清除登录salt缓存
     */
    public String clearSaltByApi(String loginServerUrl, Map<String,Object> params) {
        String strURL = loginServerUrl + "/clearSaltCache?username=" + params.get("username") + "&password=" + params.get("password") + "&appType=" + params.get("appType") + "&appId="+params.get("appId")+"&userType=" + params.get("userType");
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            httpConn.disconnect();
            System.out.println(buffer.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return buffer.toString();
    }

    /**
     * 登陆验证服务器
     */
    public String loginByApi(String loginServerUrl, Map<String,Object> params) {
        String strURL = loginServerUrl + "/login?username=" + params.get("username") + "&password=" + params.get("password") + "&appType=" + params.get("appType") + "&appId="+params.get("appId")+"&userType=" + params.get("userType");
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            httpConn.disconnect();
            System.out.println(buffer.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return buffer.toString();
    }


    public String postJson(String url, String jsonStr) {
        String returnStr = null;
        if(url==null || "".equals(url))
        {
            return returnStr;
        }

        HttpPost httpPost = new HttpPost(url);
        try {
            long currentTime=System.currentTimeMillis();

            //httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setHeader("Content-Type", "text/plain");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
            StringEntity req = new StringEntity(jsonStr);
            //req.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            //req.setContentType("application/json; charset=utf-8");  // "text/json"
            httpPost.setEntity(req);
            log.debug(currentTime + " 开始发送 请求：url" + url);

            CloseableHttpResponse response = httpCliPool.getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse="";
                if(entity != null)
                {
                    resopnse= EntityUtils.toString(entity, "utf-8");
                }
                log.debug(currentTime + " 接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                httpPost.releaseConnection();

                //httpPost.abort();
                log.info(currentTime+" 接收响应：url"+url+" status="+status+" resopnse="+EntityUtils.toString(entity,"utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.releaseConnection();
            //httpPost.abort();
            log.error(" Exception"+e.toString()+" url="+url+" jsonstr="+jsonStr);
        }
        return returnStr;
    }

    public String postXml(String url, String xmlStr, String charset) {
        String returnStr = null;
        if (url==null || "".equals(url)) {
            return returnStr;
        }

        // 设置数据按哪种charset进行getBytes
        Charset cs = null;
        if (charset != null && !"".equals(charset)){
            if ("UTF-8".equals(charset.toUpperCase())){
                cs = Consts.UTF_8;
            }
        }
        if (cs == null){
            cs = Consts.ISO_8859_1;
        }

        HttpPost httpPost = new HttpPost(url);
        try {
            long currentTime=System.currentTimeMillis();

            httpPost.setHeader("Content-Type", " text/xml; charset=utf-8");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
            StringEntity req = new StringEntity(xmlStr, cs);
            httpPost.setEntity(req);
            log.debug(currentTime + " 开始发送 请求：url" + url);

            CloseableHttpResponse response = httpCliPool.getConnection().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse="";
                if(entity != null) {
                    resopnse= EntityUtils.toString(entity, "utf-8");
                }
                log.debug(currentTime + " 接收响应：url" + url + " status=" + status);
                return entity != null ? resopnse : null;
            } else {
                HttpEntity entity = response.getEntity();
                httpPost.releaseConnection();

                //httpPost.abort();
                log.info(currentTime+" 接收响应：url"+url+" status="+status+" resopnse="+EntityUtils.toString(entity,"utf-8"));
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.releaseConnection();
            //httpPost.abort();
            log.error(" Exception"+e.toString()+" url="+url+" xmlstr="+xmlStr);
        }
        return returnStr;
    }

    public static String postJsonURLConn(String url, String jsonStr) throws Exception {
        String result = null;
        if(url==null || "".equals(url))
        {
            return result;
        }

        URL realUrl;
        BufferedReader in = null;
        try {
            realUrl = new URL(url);
            try {
                URLConnection conn2 = realUrl.openConnection();
                HttpURLConnection conn = (HttpURLConnection) conn2;

                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                //conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                //conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(3000); 	// 20000
                conn.setReadTimeout(5000); 	//300000

                // 以下两种组装POST数据的方式都OK
                // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                // 要注意的是connection.getOutputStream会隐含的进行connect。
                //conn.connect();

                //POST请求
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                //String str = new String(jsonStr.getBytes("ISO8859-1"), "UTF-8");
                out.writeBytes(jsonStr);

                //out.write(jsonStr.getBytes("UTF-8"));

                // 获取所有响应头字段
//                Map<String,List<String>> map = conn.getHeaderFields();
//                // 遍历所有的响应头字段
//                for(String key : map.keySet()) {
//                    System.out.println(key+ "--->" + map.get(key));
//                }

                int resCode = conn.getResponseCode();
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if (resCode >= 200 && resCode < 300) {
                    String line;
                    while ((line = in.readLine())!= null) {
                        result += line;
                    }
                }
                //in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally{
               in.close();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
