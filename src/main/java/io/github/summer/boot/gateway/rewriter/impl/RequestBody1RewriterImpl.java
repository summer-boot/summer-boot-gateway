package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.RequestBodyRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class RequestBody1RewriterImpl implements RequestBodyRewriter {

    @Override
    public String rewrite(ServerWebExchange exchange, String value) {
        return "request_body_1";
    }

    @Override
    public String name() {
        return "request_body_1";
    }

}
