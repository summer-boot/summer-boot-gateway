package io.github.summer.boot.gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 响应属性
 *
 * @author changebooks@qq.com
 */
public final class ResponseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 压缩方式
     */
    private static final String COMPRESS = "gzip";

    /**
     * 默认字符编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private ResponseUtils() {
    }

    /**
     * 获取数据类型
     *
     * @param response Http Response
     * @return 数据类型
     */
    public static MediaType getContentType(ServerHttpResponse response) {
        return response.getHeaders().getContentType();
    }

    /**
     * 获取字符编码
     *
     * @param response Http Response
     * @return 字符编码
     */
    public static Charset getCharset(ServerHttpResponse response) {
        return getCharset(response, DEFAULT_CHARSET);
    }

    /**
     * 获取字符编码
     *
     * @param response       Http Response
     * @param defaultCharset 默认字符编码
     * @return 字符编码
     */
    public static Charset getCharset(ServerHttpResponse response, Charset defaultCharset) {
        MediaType contentType = response.getHeaders().getContentType();
        if (contentType != null) {
            Charset charset = contentType.getCharset();
            if (charset != null) {
                return charset;
            }
        }

        return defaultCharset;
    }

    /**
     * 获取数据编码
     *
     * @param response Http Response
     * @return 数据编码列表
     */
    public static List<String> getContentEncodings(ServerHttpResponse response) {
        return response.getHeaders().get(HttpHeaders.CONTENT_ENCODING);
    }

    /**
     * 压缩？
     *
     * @param response Http Response
     * @return True-Compress, False-NonCompress
     */
    public static boolean isCompress(ServerHttpResponse response) {
        List<String> encodingList = getContentEncodings(response);
        if (encodingList != null) {
            return encodingList.contains(COMPRESS);
        } else {
            return false;
        }
    }

    /**
     * 获取状态码
     *
     * @param response Http Response
     * @return 状态码
     */
    public static HttpStatusCode getStatusCode(ServerHttpResponse response) {
        return response.getStatusCode();
    }

    /**
     * 读响应体
     *
     * @param exchange Web Exchange
     * @param value    Data Buffer
     * @return Response body
     */
    public static String readBody(ServerWebExchange exchange, DataBuffer value) {
        ServerHttpResponse response = exchange.getResponse();

        try {
            return readBody(response, value);
        } catch (Throwable ex) {
            LOGGER.error("readBody failed, throwable: ", ex);
            return null;
        }
    }

    /**
     * 读响应体
     *
     * @param response Http Response
     * @param value    Data Buffer
     * @return Response body
     * @throws IOException decompress failed
     */
    public static String readBody(ServerHttpResponse response, DataBuffer value) throws IOException {
        byte[] data = getBody(response, value);
        if (data != null) {
            Charset charset = getCharset(response);
            return new String(data, charset);
        } else {
            return null;
        }
    }

    /**
     * 写响应体
     *
     * @param response Http Response
     * @param value    Response body
     * @return Data Buffer
     */
    public static DataBuffer writeBody(ServerHttpResponse response, String value) {
        HttpHeaders headers = response.getHeaders();

        try {
            byte[] data = getBytes(response, value);
            if (data != null) {
                headers.setContentLength(data.length);
                return response.bufferFactory().allocateBuffer(data.length).write(data);
            }
        } catch (Throwable ex) {
            LOGGER.error("writeBody failed, value: {}, throwable: ", value, ex);
        }

        headers.setContentLength(0);
        return null;
    }

    /**
     * 获取响应体
     *
     * @param response Http Response
     * @param value    Data Buffer
     * @return Byte array
     * @throws IOException decompress failed
     */
    public static byte[] getBody(ServerHttpResponse response, DataBuffer value) throws IOException {
        if (value == null) {
            return null;
        }

        int len = value.readableByteCount();
        if (len <= 0) {
            return null;
        }

        byte[] data = new byte[len];
        value.read(data);
        DataBufferUtils.release(value);

        return decompress(response, data);
    }

    /**
     * Response body to Byte array
     *
     * @param response Http Response
     * @param value    Response body
     * @return Byte array
     * @throws IOException compress failed
     */
    public static byte[] getBytes(ServerHttpResponse response, String value) throws IOException {
        if (value != null) {
            Charset charset = getCharset(response);
            byte[] data = value.getBytes(charset);
            return compress(response, data);
        } else {
            return null;
        }
    }

    /**
     * 压缩
     *
     * @param response Http Response
     * @param value    Byte array
     * @return compressed Byte array
     * @throws IOException compress failed
     */
    public static byte[] compress(ServerHttpResponse response, byte[] value) throws IOException {
        if (isCompress(response)) {
            return CompressUtils.compress(value);
        } else {
            return value;
        }
    }

    /**
     * 解压
     *
     * @param response Http Response
     * @param value    Byte array
     * @return decompressed Byte array
     * @throws IOException decompress failed
     */
    public static byte[] decompress(ServerHttpResponse response, byte[] value) throws IOException {
        if (isCompress(response)) {
            return CompressUtils.decompress(value);
        } else {
            return value;
        }
    }

}
