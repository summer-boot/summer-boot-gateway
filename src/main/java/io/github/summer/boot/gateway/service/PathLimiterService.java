package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.util.RateLimiter;

import java.util.Map;

/**
 * 路径限流
 *
 * @author changebooks@qq.com
 */
public interface PathLimiterService {
    /**
     * 获取一条记录
     *
     * @param routeId 路由id
     * @param path    路径1
     * @param orPath  路径2
     * @return RateLimiter.Parameter
     */
    RateLimiter.Parameter selectOne(String routeId, String path, String orPath);

    /**
     * 获取一条记录
     *
     * @param routeId 路由id
     * @param uriPath 路径
     * @return RateLimiter.Parameter
     */
    RateLimiter.Parameter selectOne(String routeId, String uriPath);

    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ UriPath : RateLimiter.Parameter ]
     */
    Map<String, RateLimiter.Parameter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ UriPath : RateLimiter.Parameter ] ]
     */
    Map<String, Map<String, RateLimiter.Parameter>> selectList();

}
