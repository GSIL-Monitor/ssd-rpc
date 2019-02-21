package com.shabro.comm.util;

import cn.jpush.api.JPushClient;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import com.shabro.rpc.util.G;
import com.shabro.rpc.util.URL;

public class JPush {
    protected static final Logger LOG = LoggerFactory.getLogger(JPush.class);
    /**
     * App的Key (在极光推送控制台创建应用页面上获取)
     */
    //private static final String appKey = "f330213168013f1297e17f76"; // 必填，每个应用都对应一个appKey
    /**
     * App的MasterSecret (在极光推送控制台创建应用页面上获取)
     */
    //private static final String masterSecret = "56d7944dc41782e60b039b65";// 必填，每个应用都对应一个masterSecret

    /**
     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。
     */
    private static final int timeToLive = 60 * 60 * 24 * 5;

    private static JPushClient jpushClient = null;

    //public static final String TITLE = "Test from API example";
    //public static final String ALERT = "Test from API Example - alert";
    //public static final String MSG_CONTENT = "Test from API Example - msgContent";
    //public static final String REGISTRATION_ID = "0900e8d85ef";
    //public static final String TAG = "tag_api";

//    public static final String TITLE = "Test from API example";
//    public static final String ALERT = "Test from API Example - alert";
//    public static final String MSG_CONTENT = "Test from API Example - msgContent";
//    public static final String REGISTRATION_ID = "0900e8d85ef";
//    public static final String TAG = "tag_api";

    /**
     * 货车导航手机版
     * @param tag
     *            ：接受者标签
     * @param alias
     *            ：接收者别名
     * @param title
     *            ：通知栏标题
     * @param alertContent
     *            ：通知栏内容
     * @param id
     *            ：id号
     * @param type
     *            ：通知类型
     */
    public static void hcdhPush(String tag, String alias, String title,
                                String alertContent, String id, String type) {
        PushPayload payload = buildPushObject_all_alias_alert(tag, alias,
                title, alertContent, id, type);
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");
        // Example1: 初始化,默认发送给android和ios，同时设置离线消息存活时间
        jpushClient = new JPushClient(G.HCDH_PHONE_MASTERSECRET, G.HCDH_PHONE_APPKEY, timeToLive);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    /**
     * 运力管家
     * @param tag
     *            ：接受者标签
     * @param alias
     *            ：接收者别名
     * @param title
     *            ：通知栏标题
     * @param alertContent
     *            ：通知栏内容
     * @param id
     *            ：id号
     * @param type
     *            ：通知类型
     */
    public static void ylgjPush(String tag, String alias, String title,
                                String alertContent, String id, String type) {
        PushPayload payload = buildPushObject_all_alias_alert(tag, alias,
                title, alertContent, id, type);
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");
        // Example1: 初始化,默认发送给android和ios，同时设置离线消息存活时间
        jpushClient = new JPushClient(G.YLGJ_MASTERSECRET, G.YLGJ_APPKEY, timeToLive,
                null, config);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    public static PushPayload buildPushObject_all_alias_alert(String tag, String alias, String title,
                                                              String alertContent, String id, String type) {
        JSONObject json = new JSONObject();
        json.put("type",type);
        json.put("id", id);
        List<String> list = new ArrayList<String>();
        list.add(alias);

        return PushPayload
                .newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(list))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()		//ios
                                .setAlert(alertContent)
                                .addExtra("jmsg", json.toString()) 					// 包含 type和id，为了兼容
                                .addExtra("id", id)
                                .addExtra("type", type)
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()	//android
                                .setAlert(alertContent)
                                .addExtra("id", id)
                                .addExtra("type", type)
                                .addExtra("content", alertContent)
                                .build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(!URL.ISDEV).build())	// 现在为false 上线后为true 生产环境
                .build();
    }

    public static PushPayload buildPushObjectVoice(String tag, String alias, String title,
                                                   String alertContent, String id, String type, String voiceContent) {
        JSONObject json = new JSONObject();
        json.put("type",type);
        json.put("id", id);
        json.put("voiceContent", voiceContent);
        List<String> list = new ArrayList<String>();
        list.add(alias);

        return PushPayload
                .newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(list))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()		//ios
                                .setAlert(alertContent)
                                .addExtra("jmsg", json.toString()) 			        // 包含 type和id，为了兼容
                                .addExtra("id", id)
                                .addExtra("type", type)
                                .addExtra("voiceContent", voiceContent)
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()	//android
                                .setAlert(alertContent)
                                .addExtra("id", id)
                                .addExtra("type", type)
                                .addExtra("content", alertContent)
                                .addExtra("voiceContent", voiceContent)
                                .build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(!URL.ISDEV).build())	// 现在为false 上线后为true 生产环境
                .build();
    }

    /**
     * 货车导航语音播报 2018-02-02
     * @param tag
     * @param alias
     * @param title
     * @param alertContent
     * @param id
     * @param type
     * @param voiceContent 要播报的内容
     */
    public static void hcdhPushVoice(String tag, String alias, String title,
                                     String alertContent, String id, String type, String voiceContent) {
        PushPayload payload = buildPushObjectVoice(tag, alias, title, alertContent, id, type, voiceContent);
        jpushClient = new JPushClient(G.HCDH_PHONE_MASTERSECRET, G.HCDH_PHONE_APPKEY, null, ClientConfig.getInstance());
        try {
            PushResult result = jpushClient.sendPush(payload);
            //LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    /**
     * 运力管家语音播报 2018-02-02
     * @param tag
     * @param alias
     * @param title
     * @param alertContent
     * @param id
     * @param type
     * @param voiceContent 要播报的内容
     */
    public static void ylgjPushVoice(String tag, String alias, String title, String alertContent, String id, String type, String voiceContent) {
        PushPayload payload = buildPushObjectVoice(tag, alias, title, alertContent, id, type, voiceContent);
        jpushClient = new JPushClient(G.YLGJ_MASTERSECRET, G.YLGJ_APPKEY, null, ClientConfig.getInstance());
        try {
            PushResult result = jpushClient.sendPush(payload);
            //LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }
}
