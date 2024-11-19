package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.rewriter.RequestHeaderRewriter;
import io.github.summer.boot.gateway.service.RequestHeaderRewriterService;
import io.github.summer.boot.gateway.service.RewriterService;
import io.github.summer.boot.gateway.util.AttributeHolder;
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

import java.util.List;

/**
 * 重写请求头
 *
 * @author changebooks@qq.com
 */
@Service
public class RequestHeaderRewriterFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHeaderRewriterFilter.class);

    @Resource
    private RequestHeaderRewriterService requestHeaderRewriterService;

    @Resource
    private RewriterService rewriterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String routeId = AttributeHolder.getRouteId(exchange);
            List<RequestHeaderRewriter> rewriterList = requestHeaderRewriterService.selectList(routeId);
            if (rewriterList == null || rewriterList.isEmpty()) {
                return chain.filter(exchange);
            }

            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            rewriterService.rewriteRequestHeader(exchange, rewriterList, builder);

            ServerHttpRequest request = builder.build();
            return chain.filter(exchange.mutate().request(request).build());
        } catch (Throwable ex) {
            LOGGER.error("rewriteRequestHeader failed, throwable: ", ex);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.REQUEST_HEADER_REWRITER;
    }

}
