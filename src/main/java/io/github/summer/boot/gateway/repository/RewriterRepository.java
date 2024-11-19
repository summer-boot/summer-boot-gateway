package io.github.summer.boot.gateway.repository;

import io.github.summer.boot.gateway.pojo.Rewriter;

import java.util.List;
import java.util.Map;

/**
 * 重写
 *
 * @author changebooks@qq.com
 */
public interface RewriterRepository {
    /**
     * 查询列表
     *
     * @param rewriteType 重写类型
     * @return [ RouteId : [ RewriteName ] ]
     */
    Map<String, List<String>> selectAll(Integer rewriteType);

    /**
     * 查询列表
     *
     * @param rewriteType 重写类型
     * @return [ Rewriter ]
     */
    List<Rewriter> selectList(Integer rewriteType);

}
