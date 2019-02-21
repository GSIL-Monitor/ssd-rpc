package com.shabro.comm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.apache.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;


/* 当前有4种手机短信：
【沙师弟货运云商】车主您好，现有{1}/{2}，需{3}米{4}，从{5}至{6}，装货时间{7}，运费：{8}。请打开沙师弟货车导航APP立即查看，回复td退订
【沙师弟货运云商】您的验证码为{1}，请于{2}分钟内正确输入。
【沙师弟货运云商】您从{1}发往{2}(订单号为{3})的货物已经装货完成，准备运输中，订单回执码为{4}，请妥善保管。
【沙师弟货运云商】您从{1}发往{2}(订单号为{3})的货物已经完成运输，欢迎再次使用。
*/

/**
 * Created by smallnoodle on 2015/4/7.
 */
public class SMS {
    private static Logger log = Logger.getLogger("locus");

    // 容联云通讯--沙师弟短信编号
//    private static final String ylgjAppId = "8a48b5514cc6710b014cdae555600ceb";//运力管家APPID
//    private static final String hcdhAppId = "8a48b5514cc6710b014cdae28c030ce9";//货车导航APPId
//    private static final String messageCode = "38178";//注册或密码找回短信;
//    private static final String ReceiptCode = "38177";//短信回执码；
//    private static final String completeCode = "38176";//完成运输；
//    private static final String hycode = "123096";//货源短信；

    // 创蓝短信供应商基本信息
    private static final String cl_url = "http://sms.253.com/msg/send";   // 应用地址 http://sms.253.com/msg/
    private static final String cl_un = "N3020995";                    // 账号  N5473131
    private static final String cl_pw = "nbtIA8rg4w70ad";              // 密码  Ua2sjec9Coa002
    private static final String cl_rd = "1";                           // 是否需要状态报告，需要1，不需要0
    private static final String cl_ex = null;                          // 扩展码

//    private static CCPRestSmsSDK initSMS(String appId) {
//        //初始化SDK
//        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//
//        //******************************注释*********************************************
//        //*初始化服务器地址和端口                                                       *
//        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
//        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
//        //*******************************************************************************
//        restAPI.init("app.cloopen.com", "8883");
//
//        //******************************注释*********************************************
//        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
//        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
//        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
//        //*******************************************************************************
//        restAPI.setAccount("aaf98f894cc66b36014cdac7e3b20cdf", "ce116d6b7a0149638d37d0388de60231");
//
//
//        //******************************注释*********************************************
//        //*初始化应用ID                                                                 *
//        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
//        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
//        //*******************************************************************************
//        restAPI.setAppId(appId);
//
//
//        //******************************注释****************************************************************
//        //*调用发送模板短信的接口发送短信                                                                  *
//        //*参数顺序说明：                                                                                  *
//        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
//        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
//        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
//        //*第三个参数是要替换的内容数组。																														       *
//        //**************************************************************************************************
//
//        //**************************************举例说明***********************************************************************
//        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
//        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
//        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
//        //*********************************************************************************************************************
//        //result = restAPI.sendTemplateSMS(tel,type ,new String[]{vcode});
//
//        //printResult(result);//输出结果
//        return restAPI;
//    }

    /** 云通信短信发送
     */
    public static String CloudCommMsg(String phone, String msg) {
        //String msg = "【253云通讯】您好，你的验证码是123456";
        String res = "";
        String content = null;
        try {
            content = "un="+cl_un+"&pw="+cl_pw+"&msg="+msg+"&phone="+phone+"&rd="+cl_rd+"&ex=null";
            res = postCloudComm(cl_url, content, phone);
            //res = CloudCommBatchSend(cl_url, cl_un, cl_pw, phone, msg, cl_rd, cl_ex);
            //System.out.println(res);
            // TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
        return res;
    }

    /** 云通信短信发送方法
     */
    public static String postCloudComm(String strURL, String params, String phone) {
        String result=null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); //UTF-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            //log.info("postCloudComm : tel=" + phone + " length=" + length);
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                result = new String(data, "UTF-8"); //UTF-8编码
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 云通信短信发送方法
     * @param url 应用地址，类似于http://ip:port/msg/
     * @param un 账号
     * @param pw 密码
     * @param phone 手机号码，多个号码使用","分割
     * @param msg 短信内容
     * @param rd 是否需要状态报告，需要1，不需要0
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    private static String CloudCommBatchSend(String url, String un, String pw, String phone, String msg,
                                             String rd, String ex) throws Exception {
        //HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();

        try {
            URI base = new URI(url, false);
            //method.setURI(new URI(base, "", false));
            method.setURI(new URI(base, "/send", false));
            method.setQueryString(new NameValuePair[] {
                    new NameValuePair("un", un),
                    new NameValuePair("pw", pw),
                    new NameValuePair("phone", phone),
                    new NameValuePair("msg", msg),
                    new NameValuePair("rd", rd),
                    new NameValuePair("ex", ex) });

            int result = client.executeMethod(method);

            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                in.close();// 1
                baos.close();// 2
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
    }

//    private static void printResult(HashMap<String, Object> result) {
//        System.out.println("SDKTestGetSubAccounts result=" + result);
//        if ("000000".equals(result.get("statusCode"))) {
//            //正常返回输出data包体信息（map）
//            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
//            Set<String> keySet = data.keySet();
//            for (String key : keySet) {
//                Object object = data.get(key);
//                System.out.println(key + " = " + object);
//            }
//        } else {
//            //异常返回输出错误码和错误信息
//            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
//        }
//    }

    private static HashMap<String, Object> parseResult(String msg, String str) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (!BaseUtil.stringNotNull(str)){
            result.put("statusCode", "1");
            return result;
        }

        String code = null;
        String[] vars = str.split(",");
        String iszero=vars[1].substring(0, 1);
        if("0".equals(iszero)){
            code = "0";//vars[1].substring(0, 1);
            String[] msgid=str.split("\n");
            result.put("msgid", msgid[1]);
        }else{
            code = vars[1].substring(0,3);
            result.put("msgid", null);
        }
        result.put("time", ""+vars[0]);
        result.put("statusCode", code);

        //log.info("parseResult :  msg=" + msg + " procTime" + result.get("time"));

        // String ss = "20170120103206,0\n17012010320622728";
        // String ss2 = "20170120103206,101\n17012010320622728";
        //System.out.println("MsgSendResult=" + str);
//        String res = str.substring(15, 16);
//        if ("0".equals(res)) {
//            result.put("statusCode", "000000");
//        }else if (!"0".equals(res)) {
//            res = str.substring(15, 18);
//            result.put("statusCode", res);
//            //System.out.println("错误信息="+ str +" 发送信息= "+ msg);
//        }

        return result;
    }

    // 注册或密码找回短信V2 【沙师弟货运云商】您的验证码为{1}，请于{2}分钟内正确输入。
    public static HashMap ylgjMessage(String tel, String vcode, String time) {
        String msg = "【沙师弟货运云商】您的验证码为"+vcode+"，请于"+time+"分钟内正确输入。";
        String result = CloudCommMsg(tel, msg);
        return parseResult(msg, result);
    }

    // 货源推荐 【沙师弟货运云商】车主您好，现有{1}/{2}，需{3}米{4}，从{5}至{6}，装货时间{7}，运费：{8}。请打开沙师弟货车导航APP立即查看，回复td退订
    public static HashMap<String, Object> hyMessage(String tel,String names,String types,String length,String cartype,String start,String end,String date,String money){
        String msg = "【沙师弟货运云商】车主您好，现有"+names+"/"+types+"，需"+length+"米"+cartype+"，从"+start+"至"+end+
                     "，装货时间"+date+"，运费："+money+"。请打开沙师弟货车导航APP立即查看！";

        //log.info("hyMessage start: tel=" + tel + " msg=" + msg);
        String result = CloudCommMsg(tel, msg);
        //log.info("hyMessage   end: tel=" + tel + " msg=" + msg);
        return parseResult(msg, result);
    }

    // 订单回执码V2 【沙师弟货运云商】您从{1}发往{2}(订单号为{3})的货物已经装货完成，准备运输中，订单回执码为{4}，请妥善保管。
    public static  HashMap ylgjReceiptMessage(String tel, String startAddress, String arriveAddress, String bidId, String vcode) {
        String msg = "【沙师弟货运云商】您从"+startAddress+"发往"+arriveAddress+"(订单号为"+bidId+
                     ")的货物已经装货完成，准备运输中，订单回执码为"+vcode+"，请妥善保管。";
        String result = CloudCommMsg(tel, msg);
        return parseResult(msg, result);
    }

    // 完成运输  【沙师弟货运云商】您从{1}发往{2}(订单号为{3})的货物已经完成运输，欢迎再次使用。
    public static  HashMap ylgjCompleteMessage(String tel, String startAddress, String arriveAddress, String bidId) {
        String msg = "【沙师弟货运云商】您从"+startAddress+"发往"+arriveAddress+"(订单号为"+bidId+
                ")的货物已经完成运输，欢迎再次使用。";
        String result = CloudCommMsg(tel, msg);
        return parseResult(msg, result);
    }


    // 货源推荐 【沙师弟货运云商】车主您好，现有{1}/{2}，需{3}米{4}，从{5}至{6}，装货时间{7}，运费：{8}。请打开沙师弟货车导航APP立即查看，回复td退订
    public static HashMap<String, Object> hyMessageBatch(String tel, String names, String types, String length, String cartype,
                                                         String start, String end, String date, String money){
        String msg = "【沙师弟货运云商】车主您好，现有"+names+"/"+types+"，需"+length+cartype+"，从"+start+"至"+end+
                "，装货时间"+date+"，运费："+money+"。请打开沙师弟货车导航APP立即查看！";
        String result = CloudCommMsg(tel, msg);
        return parseResult(msg, result);
    }

}
