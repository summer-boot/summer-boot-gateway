package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.RequestHeaderRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写请求头
 *
 * @author changebooks@qq.com
 */
public interface RequestHeaderRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ RequestHeaderRewriter ]
     */
    List<RequestHeaderRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ RequestHeaderRewriter ] ]
     */
    Map<String, List<RequestHeaderRewriter>> selectList();

}
