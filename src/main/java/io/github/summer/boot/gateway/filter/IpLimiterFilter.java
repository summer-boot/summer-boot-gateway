package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.scheduler.InterruptionListener;
import io.github.summer.boot.gateway.service.IpLimiterService;
import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.FilterOrder;
import io.github.summer.boot.gateway.util.IpUtils;
import io.github.summer.boot.gateway.util.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * ip限流
 *
 * @author changebooks@qq.com
 */
@Service
public class IpLimiterFilter implements GlobalFilter, Ordered {
    /**
     * Status Code
     */
    private static final HttpStatus STATUS_CODE = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;

    /**
     * Error Message
     */
    private static final String ERROR_MESSAGE = "IP LIMITER";

    @Resource
    private RateLimiter rateLimiter;

    @Resource
    private IpLimiterService ipLimiterService;

    @Resource
    private InterruptionListener interruptionListener;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        RateLimiter.Parameter parameter = getParameter(exchange);
        if (parameter == null) {
            return chain.filter(exchange);
        }

        return rateLimiter.acquire(parameter)
                .flatMap((Function<Boolean, Mono<Void>>) permitted -> {
                    if (permitted != null && permitted) {
                        return chain.filter(exchange);
                    }

                    AttributeHolder.setMessage(exchange, ERROR_MESSAGE);
                    return interruptionListener.interrupt(exchange, STATUS_CODE, null);
                });
    }

    /**
     * 获取参数
     *
     * @param exchange Web Exchange
     * @return RateLimiter.Parameter
     */
    private RateLimiter.Parameter getParameter(ServerWebExchange exchange) {
        String ipAddr = AttributeHolder.getIpClient(exchange);
        if (IpUtils.isEmpty(ipAddr)) {
            return null;
        } else {
            String routeId = AttributeHolder.getRouteId(exchange);
            return ipLimiterService.selectOne(routeId, ipAddr);
        }
    }

    @Override
    public int getOrder() {
        return FilterOrder.IP_LIMITER;
    }

}
