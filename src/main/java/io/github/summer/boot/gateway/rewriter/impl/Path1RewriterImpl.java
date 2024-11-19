package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.PathRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class Path1RewriterImpl implements PathRewriter {

    @Override
    public String rewrite(ServerWebExchange exchange, String value) {
        return "/china";
    }

    @Override
    public String name() {
        return "path_1";
    }

}
