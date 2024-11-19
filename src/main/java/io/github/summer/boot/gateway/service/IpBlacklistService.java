package io.github.summer.boot.gateway.service;

import java.util.Map;
import java.util.Set;

/**
 * ip黑名单
 *
 * @author changebooks@qq.com
 */
public interface IpBlacklistService {
    /**
     * 检查ip地址
     *
     * @param routeId 路由id
     * @param ipAddr  ip地址
     * @return 拒绝访问？
     */
    boolean isDeny(String routeId, String ipAddr);

    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ IpAddr ]
     */
    Set<String> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ IpAddr ] ]
     */
    Map<String, Set<String>> selectList();

}
