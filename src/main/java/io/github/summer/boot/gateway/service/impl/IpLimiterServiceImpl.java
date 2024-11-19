package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.IpLimiterRepository;
import io.github.summer.boot.gateway.service.IpLimiterService;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.util.JsonParser;
import io.github.summer.boot.gateway.util.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
@Service
public class IpLimiterServiceImpl implements IpLimiterService, RefreshListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpLimiterServiceImpl.class);

    /**
     * Ip Limiter Repository
     */
    private final IpLimiterRepository ipLimiterRepository;

    /**
     * [ RouteId : RateLimiter.Parameter ]
     */
    private volatile Map<String, RateLimiter.Parameter> data;

    public IpLimiterServiceImpl(IpLimiterRepository ipLimiterRepository) {
        this.ipLimiterRepository = ipLimiterRepository;
    }

    @Override
    public RateLimiter.Parameter selectOne(String routeId, String ipAddr) {
        if (routeId == null || routeId.isEmpty()) {
            return null;
        }

        if (ipAddr == null || ipAddr.isEmpty()) {
            return null;
        }

        if (data == null || data.isEmpty()) {
            return null;
        }

        RateLimiter.Parameter parameter = data.get(routeId);
        if (parameter == null) {
            return null;
        }

        List<String> keys = parameter.keys()
                .stream()
                .map(x -> x + ipAddr)
                .toList();
        List<String> args = parameter.args();

        return new RateLimiter.Parameter(keys, args);
    }

    @Override
    public Map<String, RateLimiter.Parameter> selectList() {
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
        Map<String, RateLimiter.Parameter> list = doSelectList();
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
     * @return [ RouteId : RateLimiter.Parameter ]
     */
    private Map<String, RateLimiter.Parameter> doSelectList() {
        try {
            return ipLimiterRepository.selectAll();
        } catch (Throwable ex) {
            LOGGER.error("doSelectList failed, throwable: ", ex);
            return null;
        }
    }

}
