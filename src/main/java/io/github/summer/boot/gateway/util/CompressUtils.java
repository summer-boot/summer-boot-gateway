package io.github.summer.boot.gateway.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩解压
 *
 * @author changebooks@qq.com
 */
public final class CompressUtils {
    /**
     * 每次读写字节数
     */
    private static final int BUFFER_SIZE = 1024 * 4;

    private CompressUtils() {
    }

    /**
     * 压缩
     *
     * @param value Byte array
     * @return compressed Byte array
     * @throws IOException compress failed
     */
    public static byte[] compress(byte[] value) throws IOException {
        if (value == null) {
            return null;
        }

        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             GZIPOutputStream writerStream = new GZIPOutputStream(outStream)) {
            writerStream.write(value);

            return outStream.toByteArray();
        }
    }

    /**
     * 解压
     *
     * @param value Byte array
     * @return decompressed Byte array
     * @throws IOException decompress failed
     */
    public static byte[] decompress(byte[] value) throws IOException {
        if (value == null) {
            return null;
        }

        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             GZIPInputStream inStream = new GZIPInputStream(new ByteArrayInputStream(value), value.length)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }

            outStream.flush();
            return outStream.toByteArray();
        }
    }

}
