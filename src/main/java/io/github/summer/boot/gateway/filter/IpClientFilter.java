package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.FilterOrder;
import io.github.summer.boot.gateway.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 客户端ip
 *
 * @author changebooks@qq.com
 */
@Service
public class IpClientFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpClientFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            holdIp(exchange);
        } catch (Throwable ex) {
            LOGGER.error("holdIp failed, throwable: ", ex);
        }

        return chain.filter(exchange);
    }

    /**
     * hold ip
     *
     * @param exchange Web Exchange
     */
    private void holdIp(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        if (holdIp(exchange, headers, IpUtils.X_FORWARDED_FOR)) {
            return;
        }

        if (holdIp(exchange, headers, IpUtils.X_REAL_IP)) {
            return;
        }

        if (holdIp(exchange, headers, IpUtils.PROXY_CLIENT_IP)) {
            return;
        }

        if (holdIp(exchange, headers, IpUtils.WL_PROXY_CLIENT_IP)) {
            return;
        }

        if (holdIp(exchange, headers, IpUtils.HTTP_CLIENT_IP)) {
            return;
        }

        if (holdIp(exchange, headers, IpUtils.HTTP_X_FORWARDED_FOR)) {
            return;
        }

        if (holdIp(exchange, request)) {
            return;
        }

        AttributeHolder.setIpClient(exchange, IpUtils.DEFAULT_IP);
    }

    /**
     * hold ip
     *
     * @param exchange   Web Exchange
     * @param headers    Http Headers
     * @param headerName Header Name
     * @return success ?
     */
    private boolean holdIp(ServerWebExchange exchange, HttpHeaders headers, String headerName) {
        String forwardedIp = IpUtils.clientIp(headers, headerName);
        if (forwardedIp == null) {
            return false;
        }

        String clientIp = IpUtils.parseIp(forwardedIp);
        if (clientIp == null) {
            return false;
        }

        AttributeHolder.setIpForwarded(exchange, forwardedIp);
        AttributeHolder.setIpClient(exchange, clientIp);
        return true;
    }

    /**
     * hold ip
     *
     * @param exchange Web Exchange
     * @param request  Http Request
     * @return success ?
     */
    private boolean holdIp(ServerWebExchange exchange, ServerHttpRequest request) {
        String clientIp = IpUtils.clientIp(request);
        if (clientIp != null) {
            AttributeHolder.setIpClient(exchange, clientIp);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getOrder() {
        return FilterOrder.IP_CLIENT;
    }

}
