package io.github.summer.boot.gateway.service;

import io.github.summer.boot.gateway.rewriter.HostRewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写域名
 *
 * @author changebooks@qq.com
 */
public interface HostRewriterService {
    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ HostRewriter ]
     */
    List<HostRewriter> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ HostRewriter ] ]
     */
    Map<String, List<HostRewriter>> selectList();

}
