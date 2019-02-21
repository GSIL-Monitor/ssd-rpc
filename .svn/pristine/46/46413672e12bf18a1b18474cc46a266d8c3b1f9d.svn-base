package com.shabro.comm.util.httpclient;


import javax.net.ssl.SSLContext;
import javax.annotation.PostConstruct;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;


/**
 * Created by songxinlai on 2016/11/25.
 */

@Component
public class HttpClientPool {
    private static Logger log = Logger.getLogger("locus");

    public final static int MAX_TOTAL_CONNECTIONS = 200;
    public final static int MAX_ROUTE_CONNECTIONS = 20;

    PoolingHttpClientConnectionManager cm = null;

    @PostConstruct
    public void init()
    {
        try {
            ConnectionSocketFactory plain_sf  = PlainConnectionSocketFactory.getSocketFactory();
            SSLConnectionSocketFactory ssl_sf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plain_sf)
                    .register("https", ssl_sf)
                    .build();

            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // Increase max total connection
            cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            // Increase default max connection per route
            cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
            // soTimeout指的是连接上一个url，获取response的返回等待时间
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(3000).build();
            cm.setDefaultSocketConfig(socketConfig);
        } catch (Exception e) {
            log.error("InterfacePhpUtilManager init Exception"+e.toString());
        }
    }

    public CloseableHttpClient getConnection()
    {
        // httpclient 4.3以上版本超时设置，单位:毫秒
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(3000)
                .setSocketTimeout(5000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .build();
        if(cm != null && cm.getTotalStats() != null)
        {
            log.info("now client pool "+cm.getTotalStats().toString());
        }
        return httpClient;
    }
}
