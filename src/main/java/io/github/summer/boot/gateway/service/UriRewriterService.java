package io.github.summer.boot.gateway.service;

import org.springframework.web.server.ServerWebExchange;

/**
 * 重写，协议、域名、端口、路径、请求参数
 *
 * @author changebooks@qq.com
 */
public interface UriRewriterService {
    /**
     * 重写协议
     *
     * @param exchange  Web Exchange
     * @param rawScheme Raw Scheme
     * @return Rewritten Scheme
     */
    String rewriteScheme(ServerWebExchange exchange, String rawScheme);

    /**
     * 重写域名
     *
     * @param exchange Web Exchange
     * @param rawHost  Raw Host
     * @return Rewritten Host
     */
    String rewriteHost(ServerWebExchange exchange, String rawHost);

    /**
     * 重写端口
     *
     * @param exchange Web Exchange
     * @param rawPort  Raw Port
     * @return Rewritten Port
     */
    int rewritePort(ServerWebExchange exchange, int rawPort);

    /**
     * 路径替换
     *
     * @param exchange Web Exchange
     * @param rawPath  Raw Path
     * @return Replaced Path
     */
    String replacePath(ServerWebExchange exchange, String rawPath);

    /**
     * 重写路径
     *
     * @param exchange Web Exchange
     * @param rawPath  Raw Path
     * @return Rewritten Path
     */
    String rewritePath(ServerWebExchange exchange, String rawPath);

    /**
     * 重写请求参数
     *
     * @param exchange Web Exchange
     * @param rawQuery Raw Query
     * @return Rewritten Query
     */
    String rewriteQuery(ServerWebExchange exchange, String rawQuery);

}
