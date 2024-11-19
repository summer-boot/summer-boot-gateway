package io.github.summer.boot.gateway.repository.impl;

import io.github.summer.boot.gateway.pojo.Router;
import io.github.summer.boot.gateway.repository.RouterRepository;
import io.github.summer.boot.gateway.util.DataTemplate;
import io.github.summer.boot.gateway.util.JsonMap;
import io.github.summer.boot.gateway.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author changebooks@qq.com
 */
@Repository
public class RouterRepositoryImpl implements RouterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterRepositoryImpl.class);

    /**
     * Table Name
     */
    private static final String TABLE_NAME = "router";

    /**
     * Class Mapper
     */
    private static final Class<Router> CLASS_MAPPER = Router.class;

    /**
     * Data Template
     */
    private final DataTemplate dataTemplate;

    public RouterRepositoryImpl(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    @Override
    public List<RouteDefinition> selectAll() {
        List<Router> list = selectList();
        if (list == null) {
            LOGGER.error("selectAll failed, list must not be null");
            return null;
        }

        return list.stream()
                .map(this::cast)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<Router> selectList() {
        List<Router> list = dataTemplate.selectList(TABLE_NAME, null, CLASS_MAPPER);
        if (list != null) {
            return list;
        } else {
            LOGGER.error("selectList failed, list must not be null");
            return null;
        }
    }

    /**
     * Router to RouteDefinition
     *
     * @param router Router
     * @return RouteDefinition
     */
    public RouteDefinition cast(Router router) {
        Router record = parse(router);
        if (record == null) {
            LOGGER.error("cast failed, record must not be null, router: {}", router);
            return null;
        }

        String routeId = record.getRouteId();
        String routePredicates = record.getRoutePredicates();
        String routeUri = record.getRouteUri();
        String routeFilters = record.getRouteFilters();
        String routeMetadata = record.getRouteMetadata();
        int routeOrder = record.getRouteOrder();
        if (routeId.isEmpty() ||
                routePredicates.isEmpty() ||
                routeUri.isEmpty()) {
            LOGGER.error("cast failed, routeId and routePredicates and routeUri must not be empty, record: {}, router: {}", record, router);
            return null;
        }

        List<PredicateDefinition> predicates = JsonParser.fromList(routePredicates, PredicateDefinition.class);
        if (predicates == null || predicates.isEmpty()) {
            LOGGER.error("cast failed, predicates must not be empty, router: {}, record: {}", router, record);
            return null;
        }

        URI uri;
        try {
            uri = URI.create(routeUri);
        } catch (Throwable ex) {
            LOGGER.error("cast failed, illegal routeUri, routeUri: {}, router: {}, record: {}, throwable: ", routeUri, router, record, ex);
            return null;
        }

        RouteDefinition result = new RouteDefinition();

        result.setId(routeId);
        result.setPredicates(predicates);
        result.setUri(uri);
        result.setOrder(routeOrder);

        if (!routeFilters.isEmpty()) {
            List<FilterDefinition> filters = JsonParser.fromList(routeFilters, FilterDefinition.class);
            if (filters != null) {
                result.setFilters(filters);
            }
        }

        if (!routeMetadata.isEmpty()) {
            Map<String, Object> metadata = JsonMap.readObject(routeMetadata);
            if (metadata != null) {
                result.setMetadata(metadata);
            }
        }

        return result;
    }

    /**
     * 解析
     *
     * @param record Router
     * @return Router
     */
    public Router parse(Router record) {
        if (record == null) {
            LOGGER.error("parse failed, record must not be null");
            return null;
        }

        String routeId = record.getRouteId();
        String routePredicates = record.getRoutePredicates();
        String routeFilters = record.getRouteFilters();
        String routeUri = record.getRouteUri();
        String routeMetadata = record.getRouteMetadata();
        Integer routeOrder = record.getRouteOrder();
        if (routeId == null ||
                routePredicates == null ||
                routeUri == null) {
            LOGGER.error("parse failed, routeId and routePredicates and routeUri must not be null, record: {}", record);
            return null;
        }

        Router result = new Router();

        result.setRouteId(routeId.trim());
        result.setRoutePredicates(routePredicates.trim());
        result.setRouteFilters(routeFilters != null ? routeFilters.trim() : "");
        result.setRouteUri(routeUri.trim());
        result.setRouteMetadata(routeMetadata != null ? routeMetadata.trim() : "");
        result.setRouteOrder(routeOrder != null ? routeOrder : 0);

        return result;
    }

}
