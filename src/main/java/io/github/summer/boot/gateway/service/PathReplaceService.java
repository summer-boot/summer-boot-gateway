package io.github.summer.boot.gateway.service;

import java.util.Map;

/**
 * 路径替换
 *
 * @author changebooks@qq.com
 */
public interface PathReplaceService {
    /**
     * 获取一条记录
     *
     * @param routeId 路由id
     * @param path    原始路径1
     * @param orPath  原始路径2
     * @return 改后路径
     */
    String selectOne(String routeId, String path, String orPath);

    /**
     * 获取一条记录
     *
     * @param routeId 路由id
     * @param path    Raw Path
     * @return Replaced Path
     */
    String selectOne(String routeId, String path);

    /**
     * 查询列表
     *
     * @param routeId 路由id
     * @return [ RawPath : ReplacePath ]
     */
    Map<String, String> selectList(String routeId);

    /**
     * 查询列表
     *
     * @return [ RouteId : [ RawPath : ReplacePath ] ]
     */
    Map<String, Map<String, String>> selectList();

}
