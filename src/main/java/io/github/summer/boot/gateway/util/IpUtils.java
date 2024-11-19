package io.github.summer.boot.gateway.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Ip Address
 *
 * @author changebooks@qq.com
 */
public final class IpUtils {
    /**
     * Default Address
     */
    public static final String DEFAULT_IP = "0.0.0.0";

    /**
     * Squid
     */
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * Nginx
     */
    public static final String X_REAL_IP = "X-Real-IP";

    /**
     * Apache
     */
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * Other
     */
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    /**
     * Unknown
     */
    private static final String UNKNOWN = "UNKNOWN";

    /**
     * Address Separator
     */
    private static final String SEPARATOR = ",";

    private IpUtils() {
    }

    /**
     * 服务器ip
     *
     * @return ip地址
     */
    public static String serverIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 客户端ip
     *
     * @param headers    Http Headers
     * @param headerName Header Name
     * @return ip地址
     */
    public static String clientIp(HttpHeaders headers, String headerName) {
        if (headers == null) {
            return null;
        }

        if (headerName == null || headerName.isEmpty()) {
            return null;
        }

        return headers.getFirst(headerName);
    }

    /**
     * 客户端ip
     *
     * @param request Http Request
     * @return ip地址
     */
    public static String clientIp(ServerHttpRequest request) {
        if (request == null) {
            return null;
        }

        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (remoteAddress == null) {
            return null;
        }

        InetAddress address = remoteAddress.getAddress();
        if (address == null) {
            return null;
        }

        String result = address.getHostAddress();
        return parseIp(result);
    }

    /**
     * 解析ip
     *
     * @param forwardedIp 转发ip
     * @return ip地址
     */
    public static String parseIp(String forwardedIp) {
        if (forwardedIp == null) {
            return null;
        }

        if (forwardedIp.isBlank()) {
            return null;
        }

        int firstPos = forwardedIp.indexOf(SEPARATOR);
        String firstIp = (firstPos == -1) ? forwardedIp : forwardedIp.substring(0, firstPos);

        String result = firstIp.trim();
        if (result.isEmpty()) {
            return null;
        }

        if (UNKNOWN.equalsIgnoreCase(result)) {
            return null;
        } else {
            return result;
        }
    }

    /**
     * 检查ip地址
     *
     * @param ipAddress ip地址
     * @return 无效ip？
     */
    public static boolean isEmpty(String ipAddress) {
        if (ipAddress == null || ipAddress.isBlank()) {
            return true;
        } else {
            return DEFAULT_IP.equals(ipAddress);
        }
    }

}
