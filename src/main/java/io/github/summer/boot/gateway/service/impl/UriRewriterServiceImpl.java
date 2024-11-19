package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.service.RewriterService;
import io.github.summer.boot.gateway.service.UriRewriterService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author changebooks@qq.com
 */
@Service
public class UriRewriterServiceImpl implements UriRewriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UriRewriterServiceImpl.class);

    @Resource
    private RewriterService rewriterService;

    @Override
    public String rewriteScheme(ServerWebExchange exchange, String rawScheme) {
        try {
            String scheme = rewriterService.rewriteScheme(exchange, rawScheme);
            return scheme != null ? scheme : rawScheme;
        } catch (Throwable ex) {
            LOGGER.error("rewriteScheme failed, rawScheme: {}, throwable: ", rawScheme, ex);
            return rawScheme;
        }
    }

    @Override
    public String rewriteHost(ServerWebExchange exchange, String rawHost) {
        try {
            String host = rewriterService.rewriteHost(exchange, rawHost);
            return host != null ? host : rawHost;
        } catch (Throwable ex) {
            LOGGER.error("rewriteHost failed, rawHost: {}, throwable: ", rawHost, ex);
            return rawHost;
        }
    }

    @Override
    public int rewritePort(ServerWebExchange exchange, int rawPort) {
        try {
            Integer port = rewriterService.rewritePort(exchange, rawPort);
            return port != null ? port : rawPort;
        } catch (Throwable ex) {
            LOGGER.error("rewritePort failed, rawPort: {}, throwable: ", rawPort, ex);
            return rawPort;
        }
    }

    @Override
    public String replacePath(ServerWebExchange exchange, String rawPath) {
        try {
            String path = rewriterService.replacePath(exchange, rawPath);
            return path != null ? path : rawPath;
        } catch (Throwable ex) {
            LOGGER.error("replacePath failed, rawPath: {}, throwable: ", rawPath, ex);
            return rawPath;
        }
    }

    @Override
    public String rewritePath(ServerWebExchange exchange, String rawPath) {
        try {
            String path = rewriterService.rewritePath(exchange, rawPath);
            return path != null ? path : rawPath;
        } catch (Throwable ex) {
            LOGGER.error("rewritePath failed, rawPath: {}, throwable: ", rawPath, ex);
            return rawPath;
        }
    }

    @Override
    public String rewriteQuery(ServerWebExchange exchange, String rawQuery) {
        try {
            String query = rewriterService.rewriteQuery(exchange, rawQuery);
            return query != null ? query : rawQuery;
        } catch (Throwable ex) {
            LOGGER.error("rewriteQuery failed, rawQuery: {}, throwable: ", rawQuery, ex);
            return rawQuery;
        }
    }

}
