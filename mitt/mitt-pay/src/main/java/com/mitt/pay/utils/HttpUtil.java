package com.mitt.pay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mitt.common.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
        <!-- http clint 相关-->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
        </dependency>
        <!-- JSONObject 依赖 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>
*/

/**
 * @ClassName HttpUtil
 * @Descriotion http请求工具类 知道自己在重复造轮子 但是为了熟悉
 * @Author Demon
 * @Date 2021/2/2 22:59
 **/
@Slf4j
public class HttpUtil {
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * get请求
     *
     * @param url url
     * @return {@link String}
     */
    public static String get(String url) {
        return get(url, null, null, null, null);
    }

    /**
     * get请求
     *
     * @param url    url
     * @param params 参数
     * @return {@link String}
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, params, null, null, null);
    }

    /**
     * get请求
     *
     * @param url          url
     * @param params       参数
     * @param paramsHeader Header参数
     * @return {@link String}
     */
    public static String get(String url, Map<String, String> params, Map<String, String> paramsHeader) {
        return get(url, params, paramsHeader, null, null);
    }

    /**
     * get请求
     *
     * @param url          url
     * @param params       参数
     * @param paramsHeader Header参数
     * @param charset      字符集
     * @return {@link String}
     */
    public static String get(String url, Map<String, String> params, Map<String, String> paramsHeader, String charset) {
        return get(url, params, paramsHeader, charset, null);
    }

    /**
     * get请求
     *
     * @param url          url
     * @param params       参数
     * @param paramsHeader Header参数
     * @param charset      字符集
     * @param contentType  内容类型
     * @return {@link String}
     */
    public static String get(String url, Map<String, String> params, Map<String, String> paramsHeader, String charset, String contentType) {
        if (url == null || CheckUtil.isEmpty(url)) {
            return null;
        }
        List paramsList = getParamsList(params);
        // 设置参数
        if (paramsList != null && paramsList.size() > 0) {
            charset = (charset == null ? CHARSET_UTF8 : charset);
            String formatParams = URLEncodedUtils.format(paramsList, charset);
            url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
                    .substring(0, url.indexOf("?") + 1) + formatParams);
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // HTTP Get请求
        HttpGet httpGet = new HttpGet(url);
        // 默认模拟浏览器
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        // 设置 contentType
        if (contentType != null && !CheckUtil.isEmpty(url)) {
            httpGet.setHeader("Content-Type", contentType);
        }
        // 设置头部
        if (null != paramsHeader && paramsHeader.size() > 0) {
            for (Map.Entry<String, String> entry : paramsHeader.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        String res = "";
        try {
            // 执行请求
            log.info("执行get请求地址:{}", httpGet.getURI());
            long start = System.currentTimeMillis();
            HttpResponse getAddrResp = httpClient.execute(httpGet);
            HttpEntity entity = getAddrResp.getEntity();
            if (entity != null) {
                if (url == charset || CheckUtil.isEmpty(charset)) {
                    res = EntityUtils.toString(entity);
                } else {
                    res = new String(EntityUtils.toByteArray(entity), charset);
                }
            }
            log.info("响应" + getAddrResp.getStatusLine());
            log.info("请求时间:{}ms", (System.currentTimeMillis() - start));
        } catch (ClientProtocolException e) {
            log.error("客户端连接协议错误", e);
        } catch (IOException e) {
            log.error("IO操作异常", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.debug("Get请求地址:{}", url);
            log.debug("参数:{}", params);
            log.debug("头部参数:{}", paramsHeader);
            log.debug("http请求结果:{}", res);
            // 释放资源
            abortConnection(httpGet, httpClient);
        }

        return res;
    }

    /**
     * 发送xml请求
     *
     * @param url url
     * @param xml xml
     * @return {@link String}
     */
    public static String postXML(String url,String xml){
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
            client = HttpClients.createDefault();
            StringEntity entityParams = new StringEntity(xml,"utf-8");
            httpPost.setEntity(entityParams);
            client = HttpClients.createDefault();
            resp = client.execute(httpPost);
            String resultMsg = EntityUtils.toString(resp.getEntity(),"utf-8");
            return resultMsg;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(client!=null){
                    client.close();
                }
                if(resp != null){
                    resp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url url
     * @return {@link String}
     */
    public static String post(String url) {
        return post(url, null, null, null, null);
    }

    /**
     * post请求
     *
     * @param url    url
     * @param params 参数
     * @return {@link String}
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, params, null, null, null);
    }

    /**
     * post请求
     *
     * @param url          url
     * @param params       参数
     * @param paramsHeader Header参数
     * @return {@link String}
     */
    public static String post(String url, Map<String, String> params, Map<String, String> paramsHeader) {
        return post(url, params, paramsHeader, null, null);
    }

    /**
     * post请求
     *
     * @param url          url
     * @param params       参数
     * @param paramsHeader Header参数
     * @param charset      字符集
     * @return {@link String}
     */
    public static String post(String url, Map<String, String> params, Map<String, String> paramsHeader, String charset) {
        return post(url, params, paramsHeader, charset, null);
    }

    /**
     * post请求
     *
     * @param url          url
     * @param params       参数
     * @param paramsHeader Header参数
     * @param charset      字符集
     * @param contentType  内容类型
     * @return {@link String}
     */
    public static String post(String url, Map<String, String> params, Map<String, String> paramsHeader, String charset, String contentType) {
        if (url == null || CheckUtil.isEmpty(url)) {
            return null;
        }
        // 设置参数
        List paramsList = getParamsList(params);
        UrlEncodedFormEntity formEntity = null;
        try {
            if (charset == null || CheckUtil.isEmpty(charset)) {
                formEntity = new UrlEncodedFormEntity(paramsList);
            } else {
                formEntity = new UrlEncodedFormEntity(paramsList, charset);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码集");
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // HTTP Get请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(formEntity);
        // 默认模拟浏览器
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        // 设置 contentType
        if (contentType != null && !CheckUtil.isEmpty(url)) {
            httpPost.setHeader("Content-Type", contentType);
        }
        // 设置头部
        if (null != paramsHeader && paramsHeader.size() > 0) {
            for (Map.Entry<String, String> entry : paramsHeader.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        String res = "";
        try {
            // 执行请求
            log.info("执行post请求地址:{}", httpPost.getURI());
            long start = System.currentTimeMillis();
            HttpResponse getAddrResp = httpClient.execute(httpPost);
            HttpEntity entity = getAddrResp.getEntity();
            if (entity != null) {
                if (url == charset || CheckUtil.isEmpty(charset)) {
                    res = EntityUtils.toString(entity);
                } else {
                    res = new String(EntityUtils.toByteArray(entity), charset);
                }
            }
            log.info("响应" + getAddrResp.getStatusLine());
            log.info("请求时间:{}ms", (System.currentTimeMillis() - start));
        } catch (ClientProtocolException e) {
            log.error("客户端连接协议错误", e);
        } catch (IOException e) {
            log.error("IO操作异常", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.debug("Post请求地址:{}", url);
            log.debug("参数:{}", params);
            log.debug("头部参数:{}", paramsHeader);
            log.debug("http请求结果:{}", res);
            // 释放资源
            abortConnection(httpPost, httpClient);
        }

        return res;
    }


    /**
     * 将传入的键/值对参数转换为NameValuePair参数集
     *
     * @param paramsMap 参数映射
     * @return {@link List}
     */
    public static List getParamsList(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }
        List params = new ArrayList();
        for (Map.Entry<String, String> map : paramsMap.entrySet()) {
            params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
        }
        return params;
    }

    /**
     * 释放HttpClient连接
     *
     * @param hrb        请求对象
     * @param httpClient client对象
     */
    private static void abortConnection(final HttpRequestBase hrb, final CloseableHttpClient httpClient) {
        try {
            if (hrb != null) {
                hrb.abort();
            }
            httpClient.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        Map jsonObject = new JSONObject();
        jsonObject.put("token", "testtoken");
        Map map = new HashMap();
        map.put("key", "val1123ue");
        String string = JSON.toJSONString(map);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String s = get("http://getapidata.612ch.com/api.php", map, jsonObject, null, null);
            list.add(s);
        }
        list.forEach(s -> {
            System.out.println(s);
        });
    }

}
