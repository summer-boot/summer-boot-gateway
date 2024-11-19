package io.github.summer.boot.gateway.util;

import org.springframework.core.Ordered;

/**
 * 过滤器排序
 *
 * @author changebooks@qq.com
 */
public interface FilterOrder {
    /**
     * 未知
     */
    int NULL = Ordered.LOWEST_PRECEDENCE;

    /**
     * 初始化
     */
    int INITIAL = Ordered.HIGHEST_PRECEDENCE;

    /**
     * 请求id
     */
    int REQUEST_ID = INITIAL + 10000;

    /**
     * 客户端ip
     */
    int IP_CLIENT = REQUEST_ID + 10000;

    /**
     * ip黑名单
     */
    int IP_BLACKLIST = IP_CLIENT + 10000;

    /**
     * ip限流
     */
    int IP_LIMITER = IP_BLACKLIST + 10000;

    /**
     * 路径限流
     */
    int PATH_LIMITER = IP_LIMITER + 10000;

    /**
     * 重写请求头
     */
    int REQUEST_HEADER_REWRITER = PATH_LIMITER + 10000;

    /**
     * 重写uri，协议、域名、端口、路径、请求参数
     */
    int URI_REWRITER = REQUEST_HEADER_REWRITER + 10000;

    /**
     * 重写请求体和响应体
     */
    int BODY_REWRITER = URI_REWRITER + 10000;

}
