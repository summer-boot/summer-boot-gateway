package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.util.RateLimiter;

import java.util.Map;

/**
 * ip限流
 *
 * @author changebooks@qq.com
 */
public interface IpLimiterService {
    /**
     * 获取一条记录
     *
     * @param routeId 路由id
     * @param ipAddr  ip地址
     * @return RateLimiter.Parameter
     */
    RateLimiter.Parameter selectOne(String routeId, String ipAddr);

    /**
     * 查询列表
     *
     * @return [ RouteId : RateLimiter.Parameter ]
     */
    Map<String, RateLimiter.Parameter> selectList();

}
