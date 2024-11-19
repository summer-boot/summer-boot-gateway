package io.github.summer.boot.gateway.rewriter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * 重写请求头
 *
 * @author changebooks@qq.com
 */
public interface RequestHeaderRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param builder  Request Builder
     */
    void rewrite(ServerWebExchange exchange, ServerHttpRequest.Builder builder);

}
