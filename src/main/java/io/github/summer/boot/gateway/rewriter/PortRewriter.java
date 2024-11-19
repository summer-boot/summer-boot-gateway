package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写端口
 *
 * @author changebooks@qq.com
 */
public interface PortRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Raw Port
     * @return Rewritten Port
     */
    Integer rewrite(ServerWebExchange exchange, Integer value);

}
