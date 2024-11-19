package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写请求参数
 *
 * @author changebooks@qq.com
 */
public interface QueryRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Raw Query
     * @return Rewritten Query
     */
    String rewrite(ServerWebExchange exchange, String value);

}
