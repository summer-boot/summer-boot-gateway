package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.PortRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写端口
 *
 * @author changebooks@qq.com
 */
public interface PortRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ PortRewriter ]
     */
    List<PortRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ PortRewriter ] ]
     */
    Map<String, List<PortRewriter>> selectList();

}
