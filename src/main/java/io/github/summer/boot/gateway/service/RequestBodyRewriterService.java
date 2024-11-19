package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.RequestBodyRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写请求体
 *
 * @author changebooks@qq.com
 */
public interface RequestBodyRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ RequestBodyRewriter ]
     */
    List<RequestBodyRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ RequestBodyRewriter ] ]
     */
    Map<String, List<RequestBodyRewriter>> selectList();

}
