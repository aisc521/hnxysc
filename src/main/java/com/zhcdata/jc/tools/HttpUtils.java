package com.zhcdata.jc.tools;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /* HTTP访问连接超时时间（毫秒） */
    private static final int CONNECT_TIMEOUT = 60000;

    /* HTTP访问读取超时时间（毫秒） */
    private static final int READ_TIMEOUT = 60000;

    /* 缓存大小 */
    private static final int BUFFER_SIZE = 4096;

    /**
     * HTTP GET
     *
     * @param url
     * @param charset URLEncode编码方式
     * @return resultBytes 返回页面字节码
     * @author lixiaoguang
     */
    public static String httpGet(String url, String charset) throws Exception {

        byte[] resultBytes = null;
        URLConnection connection = null;
        InputStream input = null;
        ByteArrayOutputStream output = null;

        try {
            // 创建实例
            connection = new URL(url).openConnection();

            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; rv:36.0) Gecko/20100101 Firefox/36.0");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "keep-alive");

            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //log.debug(key + ": " + map.get(key));
            }

            input = connection.getInputStream();
            output = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesCount = 0;
            while ((bytesCount = input.read(buffer, 0, buffer.length)) > 0) {
                output.write(buffer, 0, bytesCount);
            }

            resultBytes = output.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("HTTP GET 执行失败");
        } finally {
            // 关闭连接
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }

        return new String(resultBytes);
    }

    public static String httpPost(String url, Map<String, Object> paramsMap, String transactionType, String charset, String src, String md5, String sysType) throws Exception {

        byte[] resultBytes = null;
        URLConnection connection = null;
        OutputStream out = null;
        InputStream input = null;
        ByteArrayOutputStream output = null;

        String formParams = "";
        StringBuilder sb = new StringBuilder();
        String digest = "";
        if (!Strings.isNullOrEmpty(md5)) {
            digest = md5;
        }
        LOGGER.error("请求参数为：" + JsonMapper.defaultMapper().toJson(paramsMap));
        String timeStamp = DateFormatUtil.formatDate("yyyyMMddHHmmssSSS", ClockUtil.currentDate());
        sb.append("transMessage={\"message\":{\"head\":{\"transactionType\":\"" + transactionType + "\",\"src\":\"" + src + "\",\"messageID\":\"9999\",\"messengerID\":\"9999\",\"timeStamp\":\"" + timeStamp + "\",\"digest\":\"" + digest + "\",\"sysType\": \"" + sysType + "\"},\"body\":{");
        for (String key : paramsMap.keySet()) {
            if (paramsMap.get(key) != null) {
                sb.append("\"" + URLEncoder.encode(key, charset) + "\":" + "\"" + URLEncoder.encode(paramsMap.get(key).toString(), charset) + "\"");
                sb.append(",");
            }
        }
        // 去掉最后的&
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}}}");
        formParams = sb.toString();
        LOGGER.error("系统 {} 请求={}", sysType, formParams);
        long start = System.currentTimeMillis();
        try {
            // 创建实例
            connection = new URL(url).openConnection();

            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; rv:36.0) Gecko/20100101 Firefox/36.0");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=" + charset.toUpperCase());

            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 建立实际的连接
            connection.connect();

            // 获取URLConnection对象对应的输出流
            out = connection.getOutputStream();
            // 发送请求参数
            out.write(formParams.getBytes());
            // flush输出流的缓冲
            out.flush();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                LOGGER.debug(key + ": " + map.get(key));
            }

            input = connection.getInputStream();
            output = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesCount = 0;
            while ((bytesCount = input.read(buffer, 0, buffer.length)) > 0) {
                output.write(buffer, 0, bytesCount);
            }

            resultBytes = output.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("HTTP POST 执行失败");
        } finally {
            // 关闭连接
            if (out != null) {
                out.close();
            }
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
        String returnStr = new String(resultBytes, "utf-8");
        long end = System.currentTimeMillis();
        LOGGER.error("系统 {} 响应={}", sysType, returnStr);
        LOGGER.error("系统 {} 交易时间:{}", sysType,(end - start));
        return returnStr;

    }


    /**
     * HTTP GET
     *
     * @param url
     * @param param 参数map
     * @param charset   URLEncode编码方式
     * @return resultBytes 返回页面字节码
     * @author lixiaoguang
     */
    public static String httpGetSingleSendPush(String url, String param, String charset) throws Exception {

        byte[] resultBytes = null;
        URLConnection connection = null;
        InputStream input = null;
        ByteArrayOutputStream output = null;

        StringBuilder sb = new StringBuilder(url).append("?");

        sb.append(param);

        url = sb.toString();
        LOGGER.debug(url);
        System.out.println(url);

        try {
            // 创建实例
            connection = new URL(url).openConnection();

            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; rv:36.0) Gecko/20100101 Firefox/36.0");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=" + charset.toUpperCase());

            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                LOGGER.debug(key + ": " + map.get(key));
            }

            input = connection.getInputStream();
            output = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesCount = 0;
            while ((bytesCount = input.read(buffer, 0, buffer.length)) > 0) {
                output.write(buffer, 0, bytesCount);
            }

            resultBytes = output.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("HTTP GET 执行失败");
        } finally {
            // 关闭连接
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }

        return new String(resultBytes);
    }

}
