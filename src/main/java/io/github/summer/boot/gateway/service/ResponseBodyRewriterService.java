package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.ResponseBodyRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写响应体
 *
 * @author changebooks@qq.com
 */
public interface ResponseBodyRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ ResponseBodyRewriter ]
     */
    List<ResponseBodyRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ ResponseBodyRewriter ] ]
     */
    Map<String, List<ResponseBodyRewriter>> selectList();

}
