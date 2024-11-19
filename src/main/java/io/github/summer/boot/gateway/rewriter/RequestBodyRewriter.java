package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写请求体
 *
 * @author changebooks@qq.com
 */
public interface RequestBodyRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Request body
     * @return Rewritten Request body
     */
    String rewrite(ServerWebExchange exchange, String value);

}
