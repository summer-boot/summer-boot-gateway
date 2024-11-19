package io.github.summer.boot.gateway.repository;

import io.github.summer.boot.gateway.pojo.PathReplace;

import java.util.List;
import java.util.Map;

/**
 * 路径替换
 *
 * @author changebooks@qq.com
 */
public interface PathReplaceRepository {
    /**
     * 查询列表
     *
     * @return [ RouteId : [ RawPath : ReplacePath ] ]
     */
    Map<String, Map<String, String>> selectAll();

    /**
     * 查询列表
     *
     * @return [ PathReplace ]
     */
    List<PathReplace> selectList();

}
