package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.service.UriRewriterService;
import io.github.summer.boot.gateway.util.FilterOrder;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 重写uri，协议、域名、端口、路径、请求参数
 *
 * @author changebooks@qq.com
 */
@Service
public class UriRewriterFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(UriRewriterFilter.class);

    /**
     * Uri Format
     */
    private static final String URI_FORMAT = "%s://%s:%d/%s";

    /**
     * Uri Path Separator
     */
    private static final String PATH_SEPARATOR = "/";

    /**
     * Uri Query Separator
     */
    private static final String QUERY_SEPARATOR = "?";

    @Resource
    private UriRewriterService uriRewriterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = null;

        try {
            String rewrittenUri = rewriteUri(exchange);
            uri = URI.create(rewrittenUri);
        } catch (Throwable ex) {
            LOGGER.error("rewriteUri failed, throwable: ", ex);
        }

        if (uri == null) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest().mutate().uri(uri).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 重写uri
     *
     * @param exchange Web Exchange
     * @return Rewritten Uri
     */
    private String rewriteUri(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        URI rawUri = request.getURI();

        String scheme = rewriteScheme(exchange, rawUri);
        String host = rewriteHost(exchange, rawUri);
        int port = rewritePort(exchange, rawUri);
        String path = rewritePath(exchange, rawUri);
        String query = rewriteQuery(exchange, rawUri);

        String result = String.format(URI_FORMAT, scheme, host, port, path);
        if (query == null || query.isEmpty()) {
            return result;
        } else {
            return result + QUERY_SEPARATOR + query;
        }
    }

    /**
     * 重写协议
     *
     * @param exchange Web Exchange
     * @param uri      Raw URI
     * @return Rewritten Scheme
     */
    private String rewriteScheme(ServerWebExchange exchange, URI uri) {
        String rawScheme = uri.getScheme();
        return uriRewriterService.rewriteScheme(exchange, rawScheme);
    }

    /**
     * 重写域名
     *
     * @param exchange Web Exchange
     * @param uri      Raw URI
     * @return Rewritten Host
     */
    private String rewriteHost(ServerWebExchange exchange, URI uri) {
        String rawHost = uri.getHost();
        return uriRewriterService.rewriteHost(exchange, rawHost);
    }

    /**
     * 重写端口
     *
     * @param exchange Web Exchange
     * @param uri      Raw URI
     * @return Rewritten Port
     */
    private int rewritePort(ServerWebExchange exchange, URI uri) {
        int rawPort = uri.getPort();
        return uriRewriterService.rewritePort(exchange, rawPort);
    }

    /**
     * 重写路径
     *
     * @param exchange Web Exchange
     * @param uri      Raw URI
     * @return Rewritten Path
     */
    private String rewritePath(ServerWebExchange exchange, URI uri) {
        String rawPath = uri.getRawPath();
        String path = uriRewriterService.replacePath(exchange, rawPath);
        String result = uriRewriterService.rewritePath(exchange, path);
        if (result == null || result.isEmpty()) {
            return "";
        }

        if (result.startsWith(PATH_SEPARATOR)) {
            return result.substring(1);
        } else {
            return result;
        }
    }

    /**
     * 重写请求参数
     *
     * @param exchange Web Exchange
     * @param uri      Raw URI
     * @return Rewritten Query
     */
    private String rewriteQuery(ServerWebExchange exchange, URI uri) {
        String rawQuery = uri.getRawQuery();
        return uriRewriterService.rewriteQuery(exchange, rawQuery);
    }

    @Override
    public int getOrder() {
        return FilterOrder.URI_REWRITER;
    }

}
