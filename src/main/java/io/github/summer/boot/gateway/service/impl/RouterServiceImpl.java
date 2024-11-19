package io.github.summer.boot.gateway.service.impl;

import io.github.summer.boot.gateway.repository.RouterRepository;
import io.github.summer.boot.gateway.service.RefreshListService;
import io.github.summer.boot.gateway.service.RouterService;
import io.github.summer.boot.gateway.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changebooks@qq.com
 */
@Service
public class RouterServiceImpl implements RouterService, RefreshListService, RouteDefinitionLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterServiceImpl.class);

    /**
     * Router Repository
     */
    private final RouterRepository routerRepository;

    /**
     * Refresh Publisher
     */
    private final ApplicationEventPublisher refreshPublisher;

    /**
     * Last Useful
     */
    private volatile List<RouteDefinition> last = new ArrayList<>();

    public RouterServiceImpl(RouterRepository routerRepository, ApplicationEventPublisher refreshPublisher) {
        this.routerRepository = routerRepository;
        this.refreshPublisher = refreshPublisher;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> list = selectList();
        if (list == null || list.isEmpty()) {
            return Flux.empty();
        } else {
            return Flux.fromIterable(list);
        }
    }

    @Override
    public List<RouteDefinition> selectList() {
        List<RouteDefinition> list = doSelectList();
        if (list == null) {
            LOGGER.warn("selectList warning, list is null, use last, last: {}", JsonParser.toJson(last));
            return last;
        }

        LOGGER.info("selectList trace, list: {}, last: {}", JsonParser.toJson(list), JsonParser.toJson(last));
        last = list;
        return list;
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
        refreshPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 查询列表
     *
     * @return [ RouteDefinition ]
     */
    private List<RouteDefinition> doSelectList() {
        try {
            return routerRepository.selectAll();
        } catch (Throwable ex) {
            LOGGER.error("doSelectList failed, throwable: ", ex);
            return null;
        }
    }

}
