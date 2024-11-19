package io.github.summer.boot.gateway.util;

import org.apache.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 请求属性
 *
 * @author changebooks@qq.com
 */
public final class RequestUtils {
    /**
     * 默认字符编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private RequestUtils() {
    }

    /**
     * 获取方法
     *
     * @param request Http Request
     * @return 方法
     */
    public static HttpMethod getMethod(ServerHttpRequest request) {
        return request.getMethod();
    }

    /**
     * 获取数据类型
     *
     * @param request Http Request
     * @return 数据类型
     */
    public static MediaType getContentType(ServerHttpRequest request) {
        return request.getHeaders().getContentType();
    }

    /**
     * 获取字符编码
     *
     * @param request Http Request
     * @return 字符编码
     */
    public static Charset getCharset(ServerHttpRequest request) {
        return getCharset(request, DEFAULT_CHARSET);
    }

    /**
     * 获取字符编码
     *
     * @param request        Http Request
     * @param defaultCharset 默认字符编码
     * @return 字符编码
     */
    public static Charset getCharset(ServerHttpRequest request, Charset defaultCharset) {
        MediaType contentType = request.getHeaders().getContentType();
        if (contentType != null) {
            Charset charset = contentType.getCharset();
            if (charset != null) {
                return charset;
            }
        }

        return defaultCharset;
    }

    /**
     * 获取数据编码
     *
     * @param request Http Request
     * @return 数据编码列表
     */
    public static List<String> getContentEncodings(ServerHttpRequest request) {
        return request.getHeaders().get(HttpHeaders.CONTENT_ENCODING);
    }

    /**
     * 获取协议
     *
     * @param request Http Request
     * @return 协议
     */
    public static String getScheme(ServerHttpRequest request) {
        return request.getURI().getScheme();
    }

    /**
     * 获取域名
     *
     * @param request Http Request
     * @return 域名
     */
    public static String getHost(ServerHttpRequest request) {
        return request.getURI().getHost();
    }

    /**
     * 获取端口
     *
     * @param request Http Request
     * @return 端口
     */
    public static int getPort(ServerHttpRequest request) {
        return request.getURI().getPort();
    }

    /**
     * 获取路径
     *
     * @param request Http Request
     * @return 路径
     */
    public static String getRawPath(ServerHttpRequest request) {
        return request.getURI().getRawPath();
    }

    /**
     * 获取解码路径
     *
     * @param request Http Request
     * @return 已解码的路径
     */
    public static String getPath(ServerHttpRequest request) {
        return request.getURI().getPath();
    }

    /**
     * 获取参数
     *
     * @param request Http Request
     * @return 参数
     */
    public static String getRawQuery(ServerHttpRequest request) {
        return request.getURI().getRawQuery();
    }

    /**
     * 获取解码参数
     *
     * @param request Http Request
     * @return 已解码的参数
     */
    public static String getQuery(ServerHttpRequest request) {
        return request.getURI().getQuery();
    }

    /**
     * 获取用户代理
     *
     * @param request Http Request
     * @return 用户代理
     */
    public static String getUserAgent(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.USER_AGENT);
    }

    /**
     * 获取用户联系方式
     *
     * @param request Http Request
     * @return 用户联系方式
     */
    public static String getFrom(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.FROM);
    }

    /**
     * 获取请求源服务器
     *
     * @param request Http Request
     * @return 请求源服务器
     */
    public static String getServer(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.SERVER);
    }

    /**
     * 获取请求源主机
     *
     * @param request Http Request
     * @return 请求源主机
     */
    public static String getOrigin(ServerHttpRequest request) {
        return request.getHeaders().getOrigin();
    }

    /**
     * 获取请求源路径
     *
     * @param request Http Request
     * @return 请求源路径
     */
    public static String getReferer(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.REFERER);
    }

}
