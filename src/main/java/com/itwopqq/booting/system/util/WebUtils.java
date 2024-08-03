package com.itwopqq.booting.system.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class WebUtils {
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int READ_DATA_TIMEOUT = 30000;

    public WebUtils() {
    }

    private static boolean isHttps(String url) {
        return "https".equalsIgnoreCase(url.substring(0, 5));
    }

    private static String postHttpStream(String url, String content, String charset) {
        HttpURLConnection connection = null;

        String var5;
        try {
            connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("content-type", "application/octet-stream");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.getOutputStream().write(content.getBytes(charset));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            int code = connection.getResponseCode();
            if (code != 200) {
                var5 = readInputStream(connection.getErrorStream());
                return var5;
            }

            var5 = readInputStream(connection.getInputStream());
        } catch (Exception var9) {
            var9.printStackTrace();
            var5 = null;
            return var5;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

        }

        return var5;
    }

    private static String getHttpRequest(String url) throws Exception {
        HttpURLConnection connection = null;

        String var4;
        try {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection)httpUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("accept", "*/*");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("User-Agent", "Icinfo framework");
            int code = connection.getResponseCode();
            if (code != 200) {
                var4 = readInputStream(connection.getErrorStream());
                return var4;
            }

            var4 = readInputStream(connection.getInputStream());
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception var12) {
                }
            }

        }

        return var4;
    }

    private static String postHttpRequest(String url, Map<String, String> params, Map<String, String> cookies) throws Exception {
        HttpURLConnection connection = null;

        String var6;
        try {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection)httpUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            writeCookie(connection, cookies);
            connection.setRequestProperty("User-Agent", "Icinfo framework");
            writeRequestParams(connection, params);
            int code = connection.getResponseCode();
            if (code != 200) {
                var6 = readInputStream(connection.getErrorStream());
                return var6;
            }

            var6 = readInputStream(connection.getInputStream());
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception var14) {
                }
            }

        }

        return var6;
    }

    private static void writeRequestParams(URLConnection connection, Map<String, String> params) throws IOException {
        StringBuffer buffer = new StringBuffer();
        if (params != null && params.size() > 0) {
            Iterator var3 = params.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, String> entry = (Entry)var3.next();
                if (!StringUtils.isEmpty((CharSequence)entry.getValue())) {
                    if (buffer.length() > 0) {
                        buffer.append("&");
                    }

                    buffer.append(URLEncoder.encode((String)entry.getKey(), "UTF-8"));
                    buffer.append("=");
                    buffer.append(URLEncoder.encode((String)entry.getValue(), "UTF-8"));
                }
            }
        }

        connection.setRequestProperty("Content-Length", String.valueOf(buffer.length()));
        connection.connect();
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        osw.write(buffer.toString());
        osw.flush();
        osw.close();
    }

    private static void writeCookie(URLConnection connection, Map<String, String> cookies) throws UnsupportedEncodingException {
        if (cookies != null && cookies.size() > 0) {
            StringBuffer cookieBuffer = new StringBuffer();
            Iterator var3 = cookies.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, String> cookie = (Entry)var3.next();
                if (!StringUtils.isEmpty((CharSequence)cookie.getValue())) {
                    if (cookieBuffer.length() > 0) {
                        cookieBuffer.append(";");
                    }

                    cookieBuffer.append(URLEncoder.encode((String)cookie.getKey(), "UTF-8"));
                    cookieBuffer.append("=");
                    cookieBuffer.append(URLEncoder.encode((String)cookie.getValue(), "UTF-8"));
                }
            }

            connection.setRequestProperty("Cookie", cookieBuffer.toString());
        }

    }

    private static String readInputStream(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();

        String line;
        while((line = in.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "";
        } else {
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("X-Real-IP");
            }

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }

            return ipAddress;
        }
    }

    public static boolean isAjax(HttpServletRequest request) {
        return request.getHeader("x-requested-with") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static void writeText(HttpServletResponse response, String content) {
        write(response, content, 200, "text/plain; charset=utf-8");
    }

    public static void writeText(HttpServletResponse response, String content, int httpStatus) {
        write(response, content, httpStatus, "text/plain; charset=utf-8");
    }

    public static void writeHtml(HttpServletResponse response, String content) {
        write(response, content, 200, "text/html; charset=utf-8");
    }

    public static void writeHtml(HttpServletResponse response, String content, int httpStatus) {
        write(response, content, httpStatus, "text/html; charset=utf-8");
    }

    /** @deprecated */
    @Deprecated
    public static void write(HttpServletResponse response, String content, int httpStatus) {
        write(response, content, httpStatus, "text/html; charset=utf-8");
    }

    public static void write(HttpServletResponse response, String content, int httpStatus, String contentType) {
        PrintWriter writer = null;

        try {
            response.setContentType(contentType);
            response.setStatus(httpStatus);
            writer = response.getWriter();
            writer.write(content);
            writer.flush();
        } catch (IOException var9) {
            var9.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }

        }

    }

    public static void writeJson(HttpServletResponse response, String content) {
        write(response, content, 200, "application/json;charset=utf-8");
    }

    public static void writeJson(HttpServletResponse response, String content, int httpStatus) {
        write(response, content, httpStatus, "application/json;charset=utf-8");
    }
}
