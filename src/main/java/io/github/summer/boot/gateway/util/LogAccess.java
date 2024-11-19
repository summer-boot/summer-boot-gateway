package io.github.summer.boot.gateway.util;

import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;

/**
 * 请求日志
 *
 * @author changebooks@qq.com
 */
public final class LogAccess {
    /**
     * 日志名称
     */
    private static final String LOG_NAME = "access";

    /**
     * 日志服务
     */
    private static final Logger LOG_SERVICE = LoggerFactory.getLogger(LOG_NAME);

    private LogAccess() {
    }

    /**
     * debug log
     *
     * @param exchange Web Exchange
     */
    public static void debug(ServerWebExchange exchange) {
        String message = LogSchema.newInstance(exchange, Level.DEBUG).toString();
        LOG_SERVICE.debug(message);
    }

    /**
     * trace log
     *
     * @param exchange Web Exchange
     */
    public static void trace(ServerWebExchange exchange) {
        String message = LogSchema.newInstance(exchange, Level.TRACE).toString();
        LOG_SERVICE.trace(message);
    }

    /**
     * info log
     *
     * @param exchange Web Exchange
     */
    public static void info(ServerWebExchange exchange) {
        String message = LogSchema.newInstance(exchange, Level.INFO).toString();
        LOG_SERVICE.info(message);
    }

    /**
     * warn log
     *
     * @param exchange Web Exchange
     */
    public static void warn(ServerWebExchange exchange) {
        String message = LogSchema.newInstance(exchange, Level.WARN).toString();
        LOG_SERVICE.warn(message);
    }

    /**
     * error log
     *
     * @param exchange Web Exchange
     */
    public static void error(ServerWebExchange exchange) {
        String message = LogSchema.newInstance(exchange, Level.ERROR).toString();
        LOG_SERVICE.error(message);
    }

}
