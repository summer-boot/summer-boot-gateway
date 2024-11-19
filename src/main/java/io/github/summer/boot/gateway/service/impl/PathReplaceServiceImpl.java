package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.PathReplaceRepository;
import io.github.summer.boot.gateway.service.PathReplaceService;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author changebooks@qq.com
 */
@Service
public class PathReplaceServiceImpl implements PathReplaceService, RefreshListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathReplaceServiceImpl.class);

    /**
     * Path Replace Repository
     */
    private final PathReplaceRepository pathReplaceRepository;

    /**
     * [ RouteId : [ RawPath : ReplacePath ] ]
     */
    private volatile Map<String, Map<String, String>> data;

    public PathReplaceServiceImpl(PathReplaceRepository pathReplaceRepository) {
        this.pathReplaceRepository = pathReplaceRepository;
    }

    @Override
    public String selectOne(String routeId, String path, String orPath) {
        String result = selectOne(routeId, path);
        if (result == null) {
            return selectOne(routeId, orPath);
        } else {
            return result;
        }
    }

    @Override
    public String selectOne(String routeId, String path) {
        if (routeId == null || routeId.isEmpty()) {
            return null;
        }

        if (path == null || path.isEmpty()) {
            return null;
        }

        Map<String, String> list = selectList(routeId);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(path);
        }
    }

    @Override
    public Map<String, String> selectList(String routeId) {
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
    public Map<String, Map<String, String>> selectList() {
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
        Map<String, Map<String, String>> list = doSelectList();
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
     * @return [ RouteId : [ RawPath : ReplacePath ] ]
     */
    private Map<String, Map<String, String>> doSelectList() {
        try {
            return pathReplaceRepository.selectAll();
        } catch (Throwable ex) {
            LOGGER.error("doSelectList failed, throwable: ", ex);
            return null;
        }
    }

}
