package com.open.custom.api.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;


public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final Gson gson = new Gson();


    public static void main(String[] args) {
        String url = "https://cn.bing.com/HPImageArchive.aspx/";
        url += "?format=js&pid=hp&video=0&idx=0&n=1&nc=" + new Date().getTime();

        Map<String, Object> jRes = HttpClientUtil.getJsonMap(url);
        System.out.println(jRes);

    }

    public static Map<String, Object> getJsonMap(String url) {
        String res = get(url);
        if (!StringUtils.isEmpty(res)) {
            return gson.fromJson(res, Map.class);
        }
        return null;
    }

    public static String get(String url) {
        long start = System.currentTimeMillis();
        log.info("Enter method get url = {}", url);
        String res = "";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();// 根据URL生成HttpURLConnection
            connection.setRequestMethod("GET");// 默认GET请求
            connection.connect();// 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                    StringBuilder result = new StringBuilder();
                    String line = null;
                    // 循环读取流
                    while ((line = reader.readLine()) != null) {
                        result.append(line).append(System.getProperty("line.separator")); // "\n"
                    }
                    res = result.toString();
                }
            }
        } catch (Exception e) {
            log.error("getJson {}", e);
        } finally {
            connection.disconnect();
            long end = System.currentTimeMillis();
            long duration = end - start;
            log.info("Exit method get, duration = " + duration / 1000 + "s; response = " + res);
        }
        return res;
    }


//    private static void httpURLPOSTCase() {
//        String methodUrl = "http://xxx.xxx.xx.xx:8280/xx/adviserxx/1.0 ";
//        HttpURLConnection connection = null;
//        OutputStream dataout = null;
//        BufferedReader reader = null;
//        String line = null;
//        try {
//            URL url = new URL(methodUrl);
//            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
//            connection.setDoOutput(true);// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false
//            connection.setDoInput(true); // 设置是否从connection读入，默认情况下是true;
//            connection.setRequestMethod("POST");// 设置请求方式为post,默认GET请求
//            connection.setUseCaches(false);// post请求不能使用缓存设为false
//            connection.setConnectTimeout(3000);// 连接主机的超时时间
//            connection.setReadTimeout(3000);// 从主机读取数据的超时时间
//            connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
//            connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
//            connection.setRequestProperty("charset", "utf-8");
//
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Authorization", "Bearer 66cb225f1c3ff0ddfdae31rae2b57488aadfb8b5e7");
//            connection.connect();// 建立TCP连接,getOutputStream会隐含的进行connect,所以此处可以不要
//
//            dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数
//            String body = "[{\"orderNo\":\"44921902\",\"adviser\":\"张怡筠\"}]";
//            dataout.write(body.getBytes());
//            dataout.flush();
//            dataout.close();
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
//                StringBuilder result = new StringBuilder();
//                // 循环读取流
//                while ((line = reader.readLine()) != null) {
//                    result.append(line).append(System.getProperty("line.separator"));//
//                }
//                System.out.println(result.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            connection.disconnect();
//        }
//    }
}
