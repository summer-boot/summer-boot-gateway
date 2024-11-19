package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写响应体
 *
 * @author changebooks@qq.com
 */
public interface ResponseBodyRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Response body
     * @return Rewritten Response body
     */
    String rewrite(ServerWebExchange exchange, String value);

}
