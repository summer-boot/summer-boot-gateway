package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.PathRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写路径
 *
 * @author changebooks@qq.com
 */
public interface PathRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ PathRewriter ]
     */
    List<PathRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ PathRewriter ] ]
     */
    Map<String, List<PathRewriter>> selectList();

}
