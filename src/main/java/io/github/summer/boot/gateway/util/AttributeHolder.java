package io.github.summer.boot.gateway.util;

import org.springframework.web.server.ServerWebExchange;

/**
 * 属性
 *
 * @author changebooks@qq.com
 */
public final class AttributeHolder {
    /**
     * 前缀
     */
    private static final String KEY_PREFIX = AttributeHolder.class.getName();

    /**
     * 路由id
     */
    private static final String KEY_ROUTE_ID = STR."\{KEY_PREFIX}.ROUTE_ID";

    /**
     * 开始时间
     */
    private static final String KEY_START_TIME = STR."\{KEY_PREFIX}.START_TIME";

    /**
     * 请求id
     */
    private static final String KEY_REQUEST_ID = STR."\{KEY_PREFIX}.REQUEST_ID";

    /**
     * 客户端ip
     */
    private static final String KEY_IP_CLIENT = STR."\{KEY_PREFIX}.IP_CLIENT";

    /**
     * 转发ip
     */
    private static final String KEY_IP_FORWARDED = STR."\{KEY_PREFIX}.IP_FORWARDED";

    /**
     * 原始协议
     */
    private static final String KEY_RAW_SCHEME = STR."\{KEY_PREFIX}.RAW_SCHEME";

    /**
     * 原始域名
     */
    private static final String KEY_RAW_HOST = STR."\{KEY_PREFIX}.RAW_HOST";

    /**
     * 原始端口
     */
    private static final String KEY_RAW_PORT = STR."\{KEY_PREFIX}.RAW_PORT";

    /**
     * 原始路径
     */
    private static final String KEY_RAW_PATH = STR."\{KEY_PREFIX}.RAW_PATH";

    /**
     * 原始请求参数
     */
    private static final String KEY_RAW_QUERY = STR."\{KEY_PREFIX}.RAW_QUERY";

    /**
     * 原始请求体
     */
    private static final String KEY_RAW_REQUEST_BODY = STR."\{KEY_PREFIX}.RAW_REQUEST_BODY";

    /**
     * 改后请求体
     */
    private static final String KEY_REQUEST_BODY = STR."\{KEY_PREFIX}.REQUEST_BODY";

    /**
     * 原始响应体
     */
    private static final String KEY_RAW_RESPONSE_BODY = STR."\{KEY_PREFIX}.RAW_RESPONSE_BODY";

    /**
     * 改后响应体
     */
    private static final String KEY_RESPONSE_BODY = STR."\{KEY_PREFIX}.RESPONSE_BODY";

    /**
     * 消息
     */
    private static final String KEY_MESSAGE = STR."\{KEY_PREFIX}.MESSAGE";

    /**
     * 异常
     */
    private static final String KEY_THROWABLE = STR."\{KEY_PREFIX}.THROWABLE";

    private AttributeHolder() {
    }

    public static String getRouteId(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_ROUTE_ID);
    }

    public static void setRouteId(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_ROUTE_ID, value);
        } else {
            exchange.getAttributes().remove(KEY_ROUTE_ID);
        }
    }

    public static boolean containsRouteId(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_ROUTE_ID);
    }

    public static long getStartTime(ServerWebExchange exchange) {
        Long startTime = (Long) exchange.getAttributes().get(KEY_START_TIME);
        return startTime != null ? startTime : 0L;
    }

    public static void setStartTime(ServerWebExchange exchange, Long value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_START_TIME, value);
        } else {
            exchange.getAttributes().remove(KEY_START_TIME);
        }
    }

    public static boolean containsStartTime(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_START_TIME);
    }

    public static String getRequestId(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_REQUEST_ID);
    }

    public static void setRequestId(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_REQUEST_ID, value);
        } else {
            exchange.getAttributes().remove(KEY_REQUEST_ID);
        }
    }

    public static boolean containsRequestId(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_REQUEST_ID);
    }

    public static String getIpClient(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_IP_CLIENT);
    }

    public static void setIpClient(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_IP_CLIENT, value);
        } else {
            exchange.getAttributes().remove(KEY_IP_CLIENT);
        }
    }

    public static boolean containsIpClient(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_IP_CLIENT);
    }

    public static String getIpForwarded(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_IP_FORWARDED);
    }

    public static void setIpForwarded(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_IP_FORWARDED, value);
        } else {
            exchange.getAttributes().remove(KEY_IP_FORWARDED);
        }
    }

    public static boolean containsIpForwarded(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_IP_FORWARDED);
    }

    public static String getRawScheme(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RAW_SCHEME);
    }

    public static void setRawScheme(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_SCHEME, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_SCHEME);
        }
    }

    public static boolean containsRawScheme(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_SCHEME);
    }

    public static String getRawHost(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RAW_HOST);
    }

    public static void setRawHost(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_HOST, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_HOST);
        }
    }

    public static boolean containsRawHost(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_HOST);
    }

    public static int getRawPort(ServerWebExchange exchange) {
        Integer rawPort = (Integer) exchange.getAttributes().get(KEY_RAW_PORT);
        return rawPort != null ? rawPort : 0;
    }

    public static void setRawPort(ServerWebExchange exchange, Integer value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_PORT, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_PORT);
        }
    }

    public static boolean containsRawPort(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_PORT);
    }

    public static String getRawPath(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RAW_PATH);
    }

    public static void setRawPath(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_PATH, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_PATH);
        }
    }

    public static boolean containsRawPath(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_PATH);
    }

    public static String getRawQuery(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RAW_QUERY);
    }

    public static void setRawQuery(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_QUERY, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_QUERY);
        }
    }

    public static boolean containsRawQuery(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_QUERY);
    }

    public static String getRawRequestBody(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RAW_REQUEST_BODY);
    }

    public static void setRawRequestBody(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_REQUEST_BODY, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_REQUEST_BODY);
        }
    }

    public static boolean containsRawRequestBody(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_REQUEST_BODY);
    }

    public static String getRequestBody(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_REQUEST_BODY);
    }

    public static void setRequestBody(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_REQUEST_BODY, value);
        } else {
            exchange.getAttributes().remove(KEY_REQUEST_BODY);
        }
    }

    public static boolean containsRequestBody(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_REQUEST_BODY);
    }

    public static String getRawResponseBody(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RAW_RESPONSE_BODY);
    }

    public static void setRawResponseBody(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RAW_RESPONSE_BODY, value);
        } else {
            exchange.getAttributes().remove(KEY_RAW_RESPONSE_BODY);
        }
    }

    public static boolean containsRawResponseBody(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RAW_RESPONSE_BODY);
    }

    public static String getResponseBody(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_RESPONSE_BODY);
    }

    public static void setResponseBody(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_RESPONSE_BODY, value);
        } else {
            exchange.getAttributes().remove(KEY_RESPONSE_BODY);
        }
    }

    public static boolean containsResponseBody(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_RESPONSE_BODY);
    }

    public static String getMessage(ServerWebExchange exchange) {
        return (String) exchange.getAttributes().get(KEY_MESSAGE);
    }

    public static void setMessage(ServerWebExchange exchange, String value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_MESSAGE, value);
        } else {
            exchange.getAttributes().remove(KEY_MESSAGE);
        }
    }

    public static boolean containsMessage(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_MESSAGE);
    }

    public static Throwable getThrowable(ServerWebExchange exchange) {
        return (Throwable) exchange.getAttributes().get(KEY_THROWABLE);
    }

    public static void setThrowable(ServerWebExchange exchange, Throwable value) {
        if (value != null) {
            exchange.getAttributes().put(KEY_THROWABLE, value);
        } else {
            exchange.getAttributes().remove(KEY_THROWABLE);
        }
    }

    public static boolean containsThrowable(ServerWebExchange exchange) {
        return exchange.getAttributes().containsKey(KEY_THROWABLE);
    }

}
