package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.RewriterRepository;
import io.github.summer.boot.gateway.rewriter.RequestHeaderRewriter;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.service.RequestHeaderRewriterService;
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
public class RequestHeaderRewriterServiceImpl implements RequestHeaderRewriterService, RefreshListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHeaderRewriterServiceImpl.class);

    /**
     * 重写类型
     */
    private static final int REWRITE_TYPE = RewriteType.REQUEST_HEADER;

    /**
     * Rewriter Repository
     */
    private final RewriterRepository rewriterRepository;

    /**
     * [ RequestHeaderRewriter ]
     */
    private final List<RequestHeaderRewriter> rewriterList;

    /**
     * [ RouteId : [ RequestHeaderRewriter ] ]
     */
    private volatile Map<String, List<RequestHeaderRewriter>> data;

    public RequestHeaderRewriterServiceImpl(RewriterRepository rewriterRepository, List<RequestHeaderRewriter> rewriterList) {
        this.rewriterRepository = rewriterRepository;
        this.rewriterList = rewriterList;
    }

    @Override
    public List<RequestHeaderRewriter> selectList(String routeId) {
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
    public Map<String, List<RequestHeaderRewriter>> selectList() {
        return data;
    }

    @Override
    public void asyncRefresh() {
        Thread.ofVirtual().start(this::refreshList);
    }

    @Override
    public void refreshList() {
        try {
            Map<String, List<RequestHeaderRewriter>> list = RewriterUtils.selectMap(rewriterRepository, REWRITE_TYPE, rewriterList);
            if (list != null) {
                this.data = list;
            }
        } catch (Throwable ex) {
            LOGGER.error("refreshList failed, throwable: ", ex);
        }
    }

}
