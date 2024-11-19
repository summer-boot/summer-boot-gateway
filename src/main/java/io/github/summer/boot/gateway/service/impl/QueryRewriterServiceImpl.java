package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.RewriterRepository;
import io.github.summer.boot.gateway.rewriter.QueryRewriter;
import io.github.summer.boot.gateway.service.QueryRewriterService;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.util.RewriteType;
import io.github.summer.boot.gateway.util.RewriterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
@Service
public class QueryRewriterServiceImpl implements QueryRewriterService, RefreshListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryRewriterServiceImpl.class);

    /**
     * 重写类型
     */
    private static final int REWRITE_TYPE = RewriteType.QUERY;

    /**
     * Rewriter Repository
     */
    private final RewriterRepository rewriterRepository;

    /**
     * [ QueryRewriter ]
     */
    private final List<QueryRewriter> rewriterList;

    /**
     * [ RouteId : [ QueryRewriter ] ]
     */
    private volatile Map<String, List<QueryRewriter>> data;

    public QueryRewriterServiceImpl(RewriterRepository rewriterRepository, List<QueryRewriter> rewriterList) {
        this.rewriterRepository = rewriterRepository;
        this.rewriterList = rewriterList;
    }

    @Override
    public List<QueryRewriter> selectList(String routeId) {
        if (routeId == null || routeId.isEmpty()) {
            return null;
        }

        if (data == null || data.isEmpty()) {
            return null;
        } else {
            return data.get(routeId);
        }
    }

    @Override
    public Map<String, List<QueryRewriter>> selectList() {
        return data;
    }

    @Override
    public void asyncRefresh() {
        Thread.ofVirtual().start(this::refreshList);
    }

    @Override
    public void refreshList() {
        try {
            Map<String, List<QueryRewriter>> list = RewriterUtils.selectMap(rewriterRepository, REWRITE_TYPE, rewriterList);
            if (list != null) {
                this.data = list;
            }
        } catch (Throwable ex) {
            LOGGER.error("refreshList failed, throwable: ", ex);
        }
    }

}
