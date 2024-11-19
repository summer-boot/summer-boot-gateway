package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.*;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * 重写，协议、域名、端口、路径、请求参数、请求头、请求体、响应体
 *
 * @author changebooks@qq.com
 */
public interface RewriterService {
    /**
     * 重写协议
     *
     * @param exchange Web Exchange
     * @param value    Raw Scheme
     * @return Rewritten Scheme
     */
    String rewriteScheme(ServerWebExchange exchange, String value);

    /**
     * 重写协议
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ SchemeRewriter ]
     * @param rawScheme    Raw Scheme
     * @return Rewritten Scheme
     */
    String rewriteScheme(ServerWebExchange exchange, List<SchemeRewriter> rewriterList, String rawScheme);

    /**
     * 重写域名
     *
     * @param exchange Web Exchange
     * @param value    Raw Host
     * @return Rewritten Host
     */
    String rewriteHost(ServerWebExchange exchange, String value);

    /**
     * 重写域名
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ HostRewriter ]
     * @param rawHost      Raw Host
     * @return Rewritten Host
     */
    String rewriteHost(ServerWebExchange exchange, List<HostRewriter> rewriterList, String rawHost);

    /**
     * 重写端口
     *
     * @param exchange Web Exchange
     * @param value    Raw Port
     * @return Rewritten Port
     */
    Integer rewritePort(ServerWebExchange exchange, Integer value);

    /**
     * 重写端口
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ PortRewriter ]
     * @param rawPort      Raw Port
     * @return Rewritten Port
     */
    Integer rewritePort(ServerWebExchange exchange, List<PortRewriter> rewriterList, Integer rawPort);

    /**
     * 路径替换
     *
     * @param exchange Web Exchange
     * @param value    Raw Path
     * @return Replaced Path
     */
    String replacePath(ServerWebExchange exchange, String value);

    /**
     * 重写路径
     *
     * @param exchange Web Exchange
     * @param value    Raw Path
     * @return Rewritten Path
     */
    String rewritePath(ServerWebExchange exchange, String value);

    /**
     * 重写路径
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ PathRewriter ]
     * @param rawPath      Raw Path
     * @return Rewritten Path
     */
    String rewritePath(ServerWebExchange exchange, List<PathRewriter> rewriterList, String rawPath);

    /**
     * 重写请求参数
     *
     * @param exchange Web Exchange
     * @param value    Raw Query
     * @return Rewritten Query
     */
    String rewriteQuery(ServerWebExchange exchange, String value);

    /**
     * 重写请求参数
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ QueryRewriter ]
     * @param rawQuery     Raw Query
     * @return Rewritten Query
     */
    String rewriteQuery(ServerWebExchange exchange, List<QueryRewriter> rewriterList, String rawQuery);

    /**
     * 重写请求头
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ RequestHeaderRewriter ]
     * @param builder      Request Builder
     */
    void rewriteRequestHeader(ServerWebExchange exchange, List<RequestHeaderRewriter> rewriterList, ServerHttpRequest.Builder builder);

    /**
     * 重写请求体
     *
     * @param exchange Web Exchange
     * @param value    Request body
     * @return Rewritten Request body
     */
    String rewriteRequestBody(ServerWebExchange exchange, String value);

    /**
     * 重写请求体
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ RequestBodyRewriter ]
     * @param rawBody      Request body
     * @return Rewritten Request body
     */
    String rewriteRequestBody(ServerWebExchange exchange, List<RequestBodyRewriter> rewriterList, String rawBody);

    /**
     * 重写响应体
     *
     * @param exchange Web Exchange
     * @param value    Response body
     * @return Rewritten Response body
     */
    DataBuffer rewriteResponseBody(ServerWebExchange exchange, DataBuffer value);

    /**
     * 重写响应体
     *
     * @param exchange     Web Exchange
     * @param rewriterList [ ResponseBodyRewriter ]
     * @param rawBody      Response body
     * @return Rewritten Response body
     */
    String rewriteResponseBody(ServerWebExchange exchange, List<ResponseBodyRewriter> rewriterList, String rawBody);

}
