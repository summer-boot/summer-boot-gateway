package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写域名
 *
 * @author changebooks@qq.com
 */
public interface HostRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Raw Host
     * @return Rewritten Host
     */
    String rewrite(ServerWebExchange exchange, String value);

}
