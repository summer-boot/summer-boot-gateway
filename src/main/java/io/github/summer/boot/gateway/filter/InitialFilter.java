package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.FilterOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 初始化
 *
 * @author changebooks@qq.com
 */
@Service
public class InitialFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitialFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        AttributeHolder.setStartTime(exchange, System.currentTimeMillis());

        try {
            holdRouteId(exchange);
        } catch (Throwable ex) {
            LOGGER.error("holdRouteId failed, throwable: ", ex);
        }

        return chain.filter(exchange);
    }

    /**
     * hold route id
     *
     * @param exchange Web Exchange
     */
    private void holdRouteId(ServerWebExchange exchange) {
        Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return;
        }

        String rawId = route.getId();
        if (rawId == null) {
            return;
        }

        String id = rawId.trim();
        if (id.isEmpty()) {
            return;
        }

        AttributeHolder.setRouteId(exchange, id);
    }

    @Override
    public int getOrder() {
        return FilterOrder.INITIAL;
    }

}
