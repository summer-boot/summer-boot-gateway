package io.github.summer.boot.gateway.repository;

import io.github.summer.boot.gateway.pojo.Router;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 路由
 *
 * @author changebooks@qq.com
 */
public interface RouterRepository {
    /**
     * 查询列表
     *
     * @return [ RouteDefinition ]
     */
    List<RouteDefinition> selectAll();

    /**
     * 查询列表
     *
     * @return [ Router ]
     */
    List<Router> selectList();

}
