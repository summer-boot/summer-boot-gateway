package io.github.summer.boot.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 路由
 *
 * @author changebooks@qq.com
 */
public interface RouterService {
    /**
     * 查询列表
     *
     * @return [ RouteDefinition ]
     */
    List<RouteDefinition> selectList();

}
