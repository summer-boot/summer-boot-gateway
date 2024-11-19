package io.github.summer.boot.gateway.util;

import org.apache.logging.log4j.core.util.datetime.FastDateFormat;

import java.util.UUID;

/**
 * 请求id
 *
 * @author changebooks@qq.com
 */
public final class RequestId {
    /**
     * 时间格式
     */
    private static final String DATE_PATTERN = "MMddHHmmssSSS";

    /**
     * 格式化时间
     */
    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance(DATE_PATTERN);

    private RequestId() {
    }

    /**
     * 生成新id
     *
     * @return id
     */
    public static String nextId() {
        return randomId();
    }

    /**
     * 当前时间
     *
     * @return 月日时分秒毫秒
     */
    public static String nowTime() {
        long time = System.currentTimeMillis();
        return DATE_FORMAT.format(time);
    }

    /**
     * 随机串
     *
     * @return UUID
     */
    public static String randomId() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }

}
