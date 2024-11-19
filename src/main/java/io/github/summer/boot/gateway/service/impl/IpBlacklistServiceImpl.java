package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.IpBlacklistRepository;
import io.github.summer.boot.gateway.service.IpBlacklistService;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author changebooks@qq.com
 */
@Service
public class IpBlacklistServiceImpl implements IpBlacklistService, RefreshListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpBlacklistServiceImpl.class);

    /**
     * Ip Blacklist Repository
     */
    private final IpBlacklistRepository ipBlacklistRepository;

    /**
     * [ RouteId : [ IpAddr ] ]
     */
    private volatile Map<String, Set<String>> data;

    public IpBlacklistServiceImpl(IpBlacklistRepository ipBlacklistRepository) {
        this.ipBlacklistRepository = ipBlacklistRepository;
    }

    @Override
    public boolean isDeny(String routeId, String ipAddr) {
        if (routeId == null || routeId.isEmpty()) {
            return false;
        }

        if (ipAddr == null || ipAddr.isEmpty()) {
            return false;
        }

        Set<String> list = selectList(routeId);
        if (list == null || list.isEmpty()) {
            return false;
        } else {
            return list.contains(ipAddr);
        }
    }

    @Override
    public Set<String> selectList(String routeId) {
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
    public Map<String, Set<String>> selectList() {
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
        Map<String, Set<String>> list = doSelectList();
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
     * @return [ RouteId : [ IpAddr ] ]
     */
    private Map<String, Set<String>> doSelectList() {
        try {
            return ipBlacklistRepository.selectAll();
        } catch (Throwable ex) {
            LOGGER.error("doSelectList failed, throwable: ", ex);
            return null;
        }
    }

}
