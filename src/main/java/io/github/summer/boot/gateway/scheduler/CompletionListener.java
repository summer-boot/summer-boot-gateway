package io.github.summer.boot.gateway.scheduler;

import io.github.summer.boot.gateway.util.LogAccess;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

/**
 * 完成
 *
 * @author changebooks@qq.com
 */
@Configuration
public class CompletionListener {
    /**
     * After Filter Chain Complete
     *
     * @param exchange Web Exchange
     */
    public void afterComplete(ServerWebExchange exchange) {
        LogAccess.info(exchange);
    }

}
