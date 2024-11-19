package io.github.summer.boot.gateway.rewriter;

/**
 * 重写，协议、域名、端口、路径、请求参数、请求头、请求体、响应体
 *
 * @author changebooks@qq.com
 */
public interface IRewriter {
    /**
     * 获取名称
     *
     * @return 名称
     */
    String name();

}
