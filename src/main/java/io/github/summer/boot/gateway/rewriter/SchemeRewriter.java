package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写协议
 *
 * @author changebooks@qq.com
 */
public interface SchemeRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Raw Scheme
     * @return Rewritten Scheme
     */
    String rewrite(ServerWebExchange exchange, String value);

}
