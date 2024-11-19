package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.SchemeRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class Scheme1RewriterImpl implements SchemeRewriter {

    @Override
    public String rewrite(ServerWebExchange exchange, String value) {
        return "https";
    }

    @Override
    public String name() {
        return "scheme_1";
    }

}
