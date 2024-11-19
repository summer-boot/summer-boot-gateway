package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.RequestHeaderRewriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class RequestHeader1RewriterImpl implements RequestHeaderRewriter {

    @Override
    public void rewrite(ServerWebExchange exchange, ServerHttpRequest.Builder builder) {
        builder.header("request_header_1", "header-abc123");
    }

    @Override
    public String name() {
        return "request_header_1";
    }

}
