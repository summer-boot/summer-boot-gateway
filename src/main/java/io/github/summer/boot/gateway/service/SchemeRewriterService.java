package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.SchemeRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写协议
 *
 * @author changebooks@qq.com
 */
public interface SchemeRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ SchemeRewriter ]
     */
    List<SchemeRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ SchemeRewriter ] ]
     */
    Map<String, List<SchemeRewriter>> selectList();

}
