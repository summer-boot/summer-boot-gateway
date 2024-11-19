package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.ResponseBodyRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class ResponseBody1RewriterImpl implements ResponseBodyRewriter {

    @Override
    public String rewrite(ServerWebExchange exchange, String value) {
        return "response_body_1";
    }

    @Override
    public String name() {
        return "response_body_1";
    }

}
