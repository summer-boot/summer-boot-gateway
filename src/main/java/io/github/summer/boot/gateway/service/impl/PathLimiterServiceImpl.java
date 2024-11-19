package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.PathLimiterRepository;
import io.github.summer.boot.gateway.service.PathLimiterService;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.util.JsonParser;
import io.github.summer.boot.gateway.util.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author changebooks@qq.com
 */
@Service
public class PathLimiterServiceImpl implements PathLimiterService, RefreshListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathLimiterServiceImpl.class);

    /**
     * Path Limiter Repository
     */
    private final PathLimiterRepository pathLimiterRepository;

    /**
     * [ RouteId : [ UriPath : RateLimiter.Parameter ] ]
     */
    private volatile Map<String, Map<String, RateLimiter.Parameter>> data;

    public PathLimiterServiceImpl(PathLimiterRepository pathLimiterRepository) {
        this.pathLimiterRepository = pathLimiterRepository;
    }

    @Override
    public RateLimiter.Parameter selectOne(String routeId, String path, String orPath) {
        RateLimiter.Parameter result = selectOne(routeId, path);
        if (result == null) {
            return selectOne(routeId, orPath);
        } else {
            return result;
        }
    }

    @Override
    public RateLimiter.Parameter selectOne(String routeId, String uriPath) {
        if (routeId == null || routeId.isEmpty()) {
            return null;
        }

        if (uriPath == null || uriPath.isEmpty()) {
            return null;
        }

        Map<String, RateLimiter.Parameter> list = selectList(routeId);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(uriPath);
        }
    }

    @Override
    public Map<String, RateLimiter.Parameter> selectList(String routeId) {
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
    public Map<String, Map<String, RateLimiter.Parameter>> selectList() {
        return data;
    }

    @Override
    public void asyncRefresh() {
        Thread.ofVirtual().start(this::refreshList);
    }

    @Override
    public void refreshList() {
        try {
            doRefreshList();
        } catch (Throwable ex) {
            LOGGER.error("refreshList failed, throwable: ", ex);
        }
    }

    /**
     * 刷新列表
     */
    private void doRefreshList() {
        Map<String, Map<String, RateLimiter.Parameter>> list = doSelectList();
        if (list != null) {
            LOGGER.info("doRefreshList trace, list: {}", JsonParser.toJson(list));
            this.data = list;
        } else {
            LOGGER.warn("doRefreshList warning, list is null, data: {}", JsonParser.toJson(data));
        }
    }

    /**
     * 查询列表
     *
     * @return [ RouteId : [ UriPath : RateLimiter.Parameter ] ]
     */
    private Map<String, Map<String, RateLimiter.Parameter>> doSelectList() {
        try {
            return pathLimiterRepository.selectAll();
        } catch (Throwable ex) {
            LOGGER.error("doSelectList failed, throwable: ", ex);
            return null;
        }
    }

}
