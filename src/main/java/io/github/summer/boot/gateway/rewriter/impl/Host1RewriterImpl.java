package io.github.summer.boot.gateway.rewriter.impl;

import io.github.summer.boot.gateway.rewriter.HostRewriter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class Host1RewriterImpl implements HostRewriter {

    @Override
    public String rewrite(ServerWebExchange exchange, String value) {
        return "news.sina.com.cn";
    }

    @Override
    public String name() {
        return "host_1";
    }

}
