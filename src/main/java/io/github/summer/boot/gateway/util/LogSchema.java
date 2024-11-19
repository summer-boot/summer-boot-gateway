package io.github.summer.boot.gateway.util;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import java.io.Serializable;

/**
 * 日志概要
 *
 * @author changebooks@qq.com
 */
public final class LogSchema implements Serializable {
    /**
     * 日志级别
     */
    private String levelName;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 耗时
     */
    private Long elapsed;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 客户端ip
     */
    private String ipClient;

    /**
     * 转发ip
     */
    private String ipForwarded;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 用户联系方式
     */
    private String from;

    /**
     * 请求源服务器
     */
    private String server;

    /**
     * 请求源主机
     */
    private String origin;

    /**
     * 请求源路径
     */
    private String referer;

    /**
     * 协议
     */
    private String scheme;

    /**
     * 原始域名
     */
    private String rawHost;

    /**
     * 改后域名
     */
    private String host;

    /**
     * 原始端口
     */
    private Integer rawPort;

    /**
     * 改后端口
     */
    private Integer port;

    /**
     * 原始路径
     */
    private String rawPath;

    /**
     * 改后路径
     */
    private String path;

    /**
     * 原始参数
     */
    private String rawQuery;

    /**
     * 改后参数
     */
    private String query;

    /**
     * 方法
     */
    private HttpMethod method;

    /**
     * 请求数据类型
     */
    private MediaType requestContentType;

    /**
     * 原始请求体
     */
    private String rawRequestBody;

    /**
     * 改后请求体
     */
    private String requestBody;

    /**
     * 响应数据类型
     */
    private MediaType responseContentType;

    /**
     * 原始响应体
     */
    private String rawResponseBody;

    /**
     * 改后响应体
     */
    private String responseBody;

    /**
     * 状态码
     */
    private HttpStatusCode statusCode;

    /**
     * 消息
     */
    private String message;

    /**
     * 异常
     */
    private Throwable throwable;

    /**
     * 获取实例
     *
     * @param exchange Web Exchange
     * @param level    日志级别
     * @return {@link LogSchema} 实例
     */
    public static LogSchema newInstance(ServerWebExchange exchange, Level level) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        long startTime = AttributeHolder.getStartTime(exchange);
        long stopTime = System.currentTimeMillis();

        String levelName = (level != null) ? level.name() : null;
        String routeId = AttributeHolder.getRouteId(exchange);
        long elapsed = stopTime - startTime;
        String requestId = AttributeHolder.getRequestId(exchange);
        String ipClient = AttributeHolder.getIpClient(exchange);
        String ipForwarded = AttributeHolder.getIpForwarded(exchange);
        String userAgent = RequestUtils.getUserAgent(request);
        String from = RequestUtils.getFrom(request);
        String server = RequestUtils.getServer(request);
        String origin = RequestUtils.getOrigin(request);
        String referer = RequestUtils.getReferer(request);
        String scheme = RequestUtils.getScheme(request);
        String rawHost = AttributeHolder.getRawHost(exchange);
        String host = RequestUtils.getHost(request);
        int rawPort = AttributeHolder.getRawPort(exchange);
        int port = RequestUtils.getPort(request);
        String rawPath = AttributeHolder.getRawPath(exchange);
        String path = RequestUtils.getRawPath(request);
        String rawQuery = AttributeHolder.getRawQuery(exchange);
        String query = RequestUtils.getRawQuery(request);
        HttpMethod method = RequestUtils.getMethod(request);
        MediaType requestContentType = RequestUtils.getContentType(request);
        String rawRequestBody = AttributeHolder.getRawRequestBody(exchange);
        String requestBody = AttributeHolder.getRequestBody(exchange);
        MediaType responseContentType = ResponseUtils.getContentType(response);
        String rawResponseBody = AttributeHolder.getRawResponseBody(exchange);
        String responseBody = AttributeHolder.getResponseBody(exchange);
        HttpStatusCode statusCode = ResponseUtils.getStatusCode(response);
        String message = AttributeHolder.getMessage(exchange);
        Throwable throwable = AttributeHolder.getThrowable(exchange);

        LogSchema result = new LogSchema();

        result.setLevelName(levelName);
        result.setRouteId(routeId);
        result.setElapsed(elapsed);
        result.setRequestId(requestId);
        result.setIpClient(ipClient);
        result.setIpForwarded(ipForwarded);
        result.setUserAgent(userAgent);
        result.setFrom(from);
        result.setServer(server);
        result.setOrigin(origin);
        result.setReferer(referer);
        result.setScheme(scheme);
        result.setRawHost(rawHost);
        result.setHost(host);
        result.setRawPort(rawPort);
        result.setPort(port);
        result.setRawPath(rawPath);
        result.setPath(path);
        result.setRawQuery(rawQuery);
        result.setQuery(query);
        result.setMethod(method);
        result.setRequestContentType(requestContentType);
        result.setRawRequestBody(rawRequestBody);
        result.setRequestBody(requestBody);
        result.setResponseContentType(responseContentType);
        result.setRawResponseBody(rawResponseBody);
        result.setResponseBody(responseBody);
        result.setStatusCode(statusCode);
        result.setMessage(message);
        result.setThrowable(throwable);

        return result;
    }

    @Override
    public String toString() {
        String levelName = getLevelName();
        String routeId = StringEscapeUtils.escapeJava(getRouteId());
        Long elapsed = getElapsed();
        String requestId = StringEscapeUtils.escapeJava(getRequestId());
        String ipClient = StringEscapeUtils.escapeJava(getIpClient());
        String ipForwarded = StringEscapeUtils.escapeJava(getIpForwarded());
        String userAgent = StringEscapeUtils.escapeJava(getUserAgent());
        String from = StringEscapeUtils.escapeJava(getFrom());
        String server = StringEscapeUtils.escapeJava(getServer());
        String origin = StringEscapeUtils.escapeJava(getOrigin());
        String referer = StringEscapeUtils.escapeJava(getReferer());
        String scheme = getScheme();
        String rawHost = getRawHost();
        String host = getHost();
        Integer rawPort = getRawPort();
        Integer port = getPort();
        String rawPath = StringEscapeUtils.escapeJava(getRawPath());
        String path = StringEscapeUtils.escapeJava(getPath());
        String rawQuery = StringEscapeUtils.escapeJava(getRawQuery());
        String query = StringEscapeUtils.escapeJava(getQuery());
        HttpMethod method = getMethod();
        MediaType requestContentType = getRequestContentType();
        String rawRequestBody = StringEscapeUtils.escapeJava(getRawRequestBody());
        String requestBody = StringEscapeUtils.escapeJava(getRequestBody());
        MediaType responseContentType = getResponseContentType();
        String rawResponseBody = StringEscapeUtils.escapeJava(getRawResponseBody());
        String responseBody = StringEscapeUtils.escapeJava(getResponseBody());
        HttpStatusCode statusCode = getStatusCode();
        String message = StringEscapeUtils.escapeJava(getMessage());
        Throwable throwable = getThrowable();

        return STR."""
                {
                    "level_name": "\{levelName}",
                    "route_id": "\{routeId}",
                    "elapsed": "\{elapsed}",
                    "request_id": "\{requestId}",
                    "ip_client": "\{ipClient}",
                    "ip_forwarded": "\{ipForwarded}",
                    "user_agent": "\{userAgent}",
                    "from": "\{from}",
                    "server": "\{server}",
                    "origin": "\{origin}",
                    "referer": "\{referer}",
                    "scheme": "\{scheme}",
                    "raw_host": "\{rawHost}",
                    "host": "\{host}",
                    "raw_port": "\{rawPort}",
                    "port": "\{port}",
                    "raw_path": "\{rawPath}",
                    "path": "\{path}",
                    "raw_query": "\{rawQuery}",
                    "query": "\{query}",
                    "method": "\{method}",
                    "request_content_type": "\{requestContentType}",
                    "raw_request_body": "\{rawRequestBody}",
                    "request_body": "\{requestBody}",
                    "response_content_type": "\{responseContentType}",
                    "raw_response_body": "\{rawResponseBody}",
                    "response_body": "\{responseBody}",
                    "status_code": "\{statusCode}",
                    "message": "\{message}",
                    "throwable": "\{throwable}"
                }
                """;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Long getElapsed() {
        return elapsed;
    }

    public void setElapsed(Long elapsed) {
        this.elapsed = elapsed;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public String getIpForwarded() {
        return ipForwarded;
    }

    public void setIpForwarded(String ipForwarded) {
        this.ipForwarded = ipForwarded;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getRawHost() {
        return rawHost;
    }

    public void setRawHost(String rawHost) {
        this.rawHost = rawHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getRawPort() {
        return rawPort;
    }

    public void setRawPort(Integer rawPort) {
        this.rawPort = rawPort;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRawPath() {
        return rawPath;
    }

    public void setRawPath(String rawPath) {
        this.rawPath = rawPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRawQuery() {
        return rawQuery;
    }

    public void setRawQuery(String rawQuery) {
        this.rawQuery = rawQuery;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public MediaType getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(MediaType requestContentType) {
        this.requestContentType = requestContentType;
    }

    public String getRawRequestBody() {
        return rawRequestBody;
    }

    public void setRawRequestBody(String rawRequestBody) {
        this.rawRequestBody = rawRequestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public MediaType getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(MediaType responseContentType) {
        this.responseContentType = responseContentType;
    }

    public String getRawResponseBody() {
        return rawResponseBody;
    }

    public void setRawResponseBody(String rawResponseBody) {
        this.rawResponseBody = rawResponseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

}
