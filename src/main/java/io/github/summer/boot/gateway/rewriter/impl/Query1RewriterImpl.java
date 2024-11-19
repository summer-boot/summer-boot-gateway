package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.QueryRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class Query1RewriterImpl implements QueryRewriter {

    @Override
    public String rewrite(ServerWebExchange exchange, String value) {
        return "a=1";
    }

    @Override
    public String name() {
        return "query_1";
    }

}
