package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.PortRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class Port1RewriterImpl implements PortRewriter {

    @Override
    public Integer rewrite(ServerWebExchange exchange, Integer value) {
        return 8081;
    }

    @Override
    public String name() {
        return "port_1";
    }

}
