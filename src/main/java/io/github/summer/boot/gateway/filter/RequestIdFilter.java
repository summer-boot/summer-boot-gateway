package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.FilterOrder;
import io.github.summer.boot.gateway.util.RequestId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求id
 *
 * @author changebooks@qq.com
 */
@Service
public class RequestIdFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            holdRequestId(exchange);
        } catch (Throwable ex) {
            LOGGER.error("holdRequestId failed, throwable: ", ex);
        }

        return chain.filter(exchange);
    }

    /**
     * hold request id
     *
     * @param exchange Web Exchange
     */
    private void holdRequestId(ServerWebExchange exchange) {
        String id = RequestId.nextId();
        AttributeHolder.setRequestId(exchange, id);
    }

    @Override
    public int getOrder() {
        return FilterOrder.REQUEST_ID;
    }

}
