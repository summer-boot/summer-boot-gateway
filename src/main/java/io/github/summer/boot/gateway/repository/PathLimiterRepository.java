package io.github.summer.boot.gateway.repository;

import io.github.summer.boot.gateway.pojo.PathLimiter;
import io.github.summer.boot.gateway.util.RateLimiter;

import java.util.List;
import java.util.Map;

/**
 * 路径限流
 *
 * @author changebooks@qq.com
 */
public interface PathLimiterRepository {
    /**
     * 查询列表
     *
     * @return [ RouteId : [ UriPath : RateLimiter.Parameter ] ]
     */
    Map<String, Map<String, RateLimiter.Parameter>> selectAll();

    /**
     * 查询列表
     *
     * @return [ PathLimiter ]
     */
    List<PathLimiter> selectList();

}
