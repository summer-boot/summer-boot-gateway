package io.github.summer.boot.gateway.repository;

import io.github.summer.boot.gateway.pojo.IpLimiter;
import io.github.summer.boot.gateway.util.RateLimiter;

import java.util.List;
import java.util.Map;

/**
 * ip限流
 *
 * @author changebooks@qq.com
 */
public interface IpLimiterRepository {
    /**
     * 查询列表
     *
     * @return [ RouteId : RateLimiter.Parameter ]
     */
    Map<String, RateLimiter.Parameter> selectAll();

    /**
     * 查询列表
     *
     * @return [ IpLimiter ]
     */
    List<IpLimiter> selectList();

}
