package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.QueryRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写请求参数
 *
 * @author changebooks@qq.com
 */
public interface QueryRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ QueryRewriter ]
     */
    List<QueryRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ QueryRewriter ] ]
     */
    Map<String, List<QueryRewriter>> selectList();

}
