package io.github.summer.boot.gateway.util;

/**
 * 重写类型
 *
 * @author changebooks@qq.com
 */
public interface RewriteType {
    /**
     * 未知
     */
    int NULL = 0;

    /**
     * 协议
     */
    int SCHEME = 1;

    /**
     * 域名
     */
    int HOST = 2;

    /**
     * 端口
     */
    int PORT = 3;

    /**
     * 路径
     */
    int PATH = 4;

    /**
     * 请求参数
     */
    int QUERY = 5;

    /**
     * 请求头
     */
    int REQUEST_HEADER = 6;

    /**
     * 请求体
     */
    int REQUEST_BODY = 7;

    /**
     * 响应体
     */
    int RESPONSE_BODY = 8;

}
