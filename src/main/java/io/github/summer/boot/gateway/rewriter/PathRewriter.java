package io.github.summer.boot.gateway.rewriter;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写路径
 *
 * @author changebooks@qq.com
 */
public interface PathRewriter extends IRewriter {
    /**
     * 执行重写
     *
     * @param exchange Web Exchange
     * @param value    Raw Path
     * @return Rewritten Path
     */
    String rewrite(ServerWebExchange exchange, String value);

}
