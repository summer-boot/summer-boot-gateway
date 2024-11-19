package io.github.summer.boot.gateway.scheduler;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 中断
 *
 * @author changebooks@qq.com
 */
@Configuration
public class InterruptionListener {

    @Resource
    private CompletionListener completionListener;

    /**
     * Interrupt Filter Chain
     *
     * @param exchange     Web Exchange
     * @param status       Status Code
     * @param responseBody Data Buffer
     * @return Response
     */
    public Mono<Void> interrupt(ServerWebExchange exchange, HttpStatusCode status, DataBuffer responseBody) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(status);
        completionListener.afterComplete(exchange);

        return response.writeWith(Mono.fromSupplier(() -> responseBody));
    }

}
