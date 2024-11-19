package io.github.summer.boot.gateway.filter;

import io.github.summer.boot.gateway.scheduler.InterruptionListener;
import io.github.summer.boot.gateway.service.IpBlacklistService;
import io.github.summer.boot.gateway.util.AttributeHolder;
import io.github.summer.boot.gateway.util.FilterOrder;
import io.github.summer.boot.gateway.util.IpUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ip黑名单
 *
 * @author changebooks@qq.com
 */
@Service
public class IpBlacklistFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpBlacklistFilter.class);

    /**
     * Status Code
     */
    private static final HttpStatus STATUS_CODE = HttpStatus.FORBIDDEN;

    /**
     * Error Message
     */
    private static final String ERROR_MESSAGE = "IP DENY";

    @Resource
    private IpBlacklistService ipBlacklistService;

    @Resource
    private InterruptionListener interruptionListener;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            boolean deny = isDeny(exchange);
            if (deny) {
                AttributeHolder.setMessage(exchange, ERROR_MESSAGE);
                return interruptionListener.interrupt(exchange, STATUS_CODE, null);
            }
        } catch (Throwable ex) {
            LOGGER.error("isDeny failed, throwable: ", ex);
        }

        return chain.filter(exchange);
    }

    /**
     * 检查ip地址
     *
     * @param exchange Web Exchange
     * @return 拒绝访问？
     */
    private boolean isDeny(ServerWebExchange exchange) {
        String ipAddr = AttributeHolder.getIpClient(exchange);
        if (IpUtils.isEmpty(ipAddr)) {
            return false;
        }

        String routeId = AttributeHolder.getRouteId(exchange);
        return ipBlacklistService.isDeny(routeId, ipAddr);
    }

    @Override
    public int getOrder() {
        return FilterOrder.IP_BLACKLIST;
    }

}
