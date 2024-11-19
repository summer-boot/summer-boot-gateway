package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.rewriter.*;
import io.github.summer.boot.gateway.service.*;
import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.ResponseUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
@Service
public class RewriterServiceImpl implements RewriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewriterServiceImpl.class);

    @Resource
    private SchemeRewriterService schemeRewriterService;

    @Resource
    private HostRewriterService hostRewriterService;

    @Resource
    private PortRewriterService portRewriterService;

    @Resource
    private PathReplaceService pathReplaceService;

    @Resource
    private PathRewriterService pathRewriterService;

    @Resource
    private QueryRewriterService queryRewriterService;

    @Resource
    private RequestBodyRewriterService requestBodyRewriterService;

    @Resource
    private ResponseBodyRewriterService responseBodyRewriterService;

    @Override
    public String rewriteScheme(ServerWebExchange exchange, String value) {
        if (!AttributeHolder.containsRawScheme(exchange)) {
            AttributeHolder.setRawScheme(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        List<SchemeRewriter> rewriterList = schemeRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        } else {
            return rewriteScheme(exchange, rewriterList, value);
        }
    }

    @Override
    public String rewriteScheme(ServerWebExchange exchange, List<SchemeRewriter> rewriterList, String rawScheme) {
        String scheme = rawScheme;

        for (SchemeRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewriteScheme warning, rewriter must not be null, routeId: {}, rawScheme: {}, scheme: {}",
                        routeId, rawScheme, scheme);
                continue;
            }

            try {
                scheme = rewriter.rewrite(exchange, scheme);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewriteScheme failed, routeId: {}, rawScheme: {}, scheme: {}, throwable: ",
                        routeId, rawScheme, scheme, ex);
            }
        }

        return scheme;
    }

    @Override
    public String rewriteHost(ServerWebExchange exchange, String value) {
        if (!AttributeHolder.containsRawHost(exchange)) {
            AttributeHolder.setRawHost(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        List<HostRewriter> rewriterList = hostRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        } else {
            return rewriteHost(exchange, rewriterList, value);
        }
    }

    @Override
    public String rewriteHost(ServerWebExchange exchange, List<HostRewriter> rewriterList, String rawHost) {
        String host = rawHost;

        for (HostRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewriteHost warning, rewriter must not be null, routeId: {}, rawHost: {}, host: {}",
                        routeId, rawHost, host);
                continue;
            }

            try {
                host = rewriter.rewrite(exchange, host);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewriteHost failed, routeId: {}, rawHost: {}, host: {}, throwable: ",
                        routeId, rawHost, host, ex);
            }
        }

        return host;
    }

    @Override
    public Integer rewritePort(ServerWebExchange exchange, Integer value) {
        if (!AttributeHolder.containsRawPort(exchange)) {
            AttributeHolder.setRawPort(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        List<PortRewriter> rewriterList = portRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        } else {
            return rewritePort(exchange, rewriterList, value);
        }
    }

    @Override
    public Integer rewritePort(ServerWebExchange exchange, List<PortRewriter> rewriterList, Integer rawPort) {
        Integer port = rawPort;

        for (PortRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewritePort warning, rewriter must not be null, routeId: {}, rawPort: {}, port: {}",
                        routeId, rawPort, port);
                continue;
            }

            try {
                port = rewriter.rewrite(exchange, port);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewritePort failed, routeId: {}, rawPort: {}, port: {}, throwable: ",
                        routeId, rawPort, port, ex);
            }
        }

        return port;
    }

    @Override
    public String replacePath(ServerWebExchange exchange, String value) {
        if (!AttributeHolder.containsRawPath(exchange)) {
            AttributeHolder.setRawPath(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        return pathReplaceService.selectOne(routeId, value);
    }

    @Override
    public String rewritePath(ServerWebExchange exchange, String value) {
        if (!AttributeHolder.containsRawPath(exchange)) {
            AttributeHolder.setRawPath(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        List<PathRewriter> rewriterList = pathRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        } else {
            return rewritePath(exchange, rewriterList, value);
        }
    }

    @Override
    public String rewritePath(ServerWebExchange exchange, List<PathRewriter> rewriterList, String rawPath) {
        String path = rawPath;

        for (PathRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewritePath warning, rewriter must not be null, routeId: {}, rawPath: {}, path: {}",
                        routeId, rawPath, path);
                continue;
            }

            try {
                path = rewriter.rewrite(exchange, path);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewritePath failed, routeId: {}, rawPath: {}, path: {}, throwable: ",
                        routeId, rawPath, path, ex);
            }
        }

        return path;
    }

    @Override
    public String rewriteQuery(ServerWebExchange exchange, String value) {
        if (!AttributeHolder.containsRawQuery(exchange)) {
            AttributeHolder.setRawQuery(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        List<QueryRewriter> rewriterList = queryRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        } else {
            return rewriteQuery(exchange, rewriterList, value);
        }
    }

    @Override
    public String rewriteQuery(ServerWebExchange exchange, List<QueryRewriter> rewriterList, String rawQuery) {
        String query = rawQuery;

        for (QueryRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewriteQuery warning, rewriter must not be null, routeId: {}, rawQuery: {}, query: {}",
                        routeId, rawQuery, query);
                continue;
            }

            try {
                query = rewriter.rewrite(exchange, query);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewriteQuery failed, routeId: {}, rawQuery: {}, query: {}, throwable: ",
                        routeId, rawQuery, query, ex);
            }
        }

        return query;
    }

    @Override
    public void rewriteRequestHeader(ServerWebExchange exchange, List<RequestHeaderRewriter> rewriterList, ServerHttpRequest.Builder builder) {
        for (RequestHeaderRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewriteRequestHeader warning, rewriter must not be null, routeId: {}", routeId);
                continue;
            }

            try {
                rewriter.rewrite(exchange, builder);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewriteRequestHeader failed, routeId: {}, throwable: ", routeId, ex);
            }
        }
    }

    @Override
    public String rewriteRequestBody(ServerWebExchange exchange, String value) {
        if (!AttributeHolder.containsRawRequestBody(exchange)) {
            AttributeHolder.setRawRequestBody(exchange, value);
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        List<RequestBodyRewriter> rewriterList = requestBodyRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        }

        String body = rewriteRequestBody(exchange, rewriterList, value);
        AttributeHolder.setRequestBody(exchange, body);

        return body;
    }

    @Override
    public String rewriteRequestBody(ServerWebExchange exchange, List<RequestBodyRewriter> rewriterList, String rawBody) {
        String body = rawBody;

        for (RequestBodyRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewriteRequestBody warning, rewriter must not be null, routeId: {}, rawBody: {}, body: {}",
                        routeId, rawBody, body);
                continue;
            }

            try {
                body = rewriter.rewrite(exchange, body);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewriteRequestBody failed, routeId: {}, rawBody: {}, body: {}, throwable: ",
                        routeId, rawBody, body, ex);
            }
        }

        return body;
    }

    @Override
    public DataBuffer rewriteResponseBody(ServerWebExchange exchange, DataBuffer value) {
        String routeId = AttributeHolder.getRouteId(exchange);
        List<ResponseBodyRewriter> rewriterList = responseBodyRewriterService.selectList(routeId);
        if (rewriterList == null || rewriterList.isEmpty()) {
            return value;
        }

        String rawBody = ResponseUtils.readBody(exchange, value);
        if (!AttributeHolder.containsRawResponseBody(exchange)) {
            AttributeHolder.setRawResponseBody(exchange, rawBody);
        }

        String body = rewriteResponseBody(exchange, rewriterList, rawBody);
        AttributeHolder.setResponseBody(exchange, body);

        ServerHttpResponse response = exchange.getResponse();
        return ResponseUtils.writeBody(response, body);
    }

    @Override
    public String rewriteResponseBody(ServerWebExchange exchange, List<ResponseBodyRewriter> rewriterList, String rawBody) {
        String body = rawBody;

        for (ResponseBodyRewriter rewriter : rewriterList) {
            if (rewriter == null) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.warn("rewriteResponseBody warning, rewriter must not be null, routeId: {}, rawBody: {}, body: {}",
                        routeId, rawBody, body);
                continue;
            }

            try {
                body = rewriter.rewrite(exchange, body);
            } catch (Throwable ex) {
                String routeId = AttributeHolder.getRouteId(exchange);
                LOGGER.error("rewriteResponseBody failed, routeId: {}, rawBody: {}, body: {}, throwable: ",
                        routeId, rawBody, body, ex);
            }
        }

        return body;
    }

}
