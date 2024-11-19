package io.github.summer.boot.gateway.repository;

import io.github.summer.boot.gateway.pojo.IpBlacklist;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ip黑名单
 *
 * @author changebooks@qq.com
 */
public interface IpBlacklistRepository {
    /**
     * 查询列表
     *
     * @return [ RouteId : [ IpAddr ] ]
     */
    Map<String, Set<String>> selectAll();

    /**
     * 查询列表
     *
     * @return [ IpBlacklist ]
     */
    List<IpBlacklist> selectList();

}
