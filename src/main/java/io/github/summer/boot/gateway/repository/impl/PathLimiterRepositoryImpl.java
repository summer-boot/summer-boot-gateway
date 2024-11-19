package io.github.summer.boot.gateway.repository.impl;

import io.github.summer.boot.gateway.pojo.PathLimiter;
import io.github.summer.boot.gateway.repository.PathLimiterRepository;
import io.github.summer.boot.gateway.util.DataTemplate;
import io.github.summer.boot.gateway.util.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
@Repository
public class PathLimiterRepositoryImpl implements PathLimiterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathLimiterRepositoryImpl.class);

    /**
     * Table Name
     */
    private static final String TABLE_NAME = "path_limiter";

    /**
     * Class Mapper
     */
    private static final Class<PathLimiter> CLASS_MAPPER = PathLimiter.class;

    /**
     * Name Separator
     */
    private static final String SEPARATOR = ":";

    /**
     * Data Template
     */
    private final DataTemplate dataTemplate;

    public PathLimiterRepositoryImpl(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    @Override
    public Map<String, Map<String, RateLimiter.Parameter>> selectAll() {
        List<PathLimiter> list = selectList();
        if (list == null) {
            LOGGER.error("selectAll failed, list must not be null");
            return null;
        }

        Map<String, Map<String, RateLimiter.Parameter>> result = new HashMap<>();

        for (PathLimiter item : list) {
            PathLimiter record = parse(item);
            if (record == null) {
                LOGGER.warn("selectAll warning, record is null, item: {}", item);
                continue;
            }

            String routeId = record.getRouteId();
            String uriPath = record.getUriPath();
            int totalSeconds = record.getTotalSeconds();
            int totalPermits = record.getTotalPermits();
            if (routeId.isEmpty() ||
                    uriPath.isEmpty() ||
                    totalSeconds <= 0 ||
                    totalPermits <= 0) {
                LOGGER.warn("selectAll warning, routeId or uriPath or totalSeconds or totalPermits is illegal, record: {}, item: {}", record, item);
                continue;
            }

            String name = prefixedUriPath(routeId, uriPath);
            RateLimiter.Parameter parameter = RateLimiter.createParameter(name, totalSeconds, totalPermits);

            Map<String, RateLimiter.Parameter> data = result.getOrDefault(routeId, new HashMap<>());
            data.put(uriPath, parameter);

            result.put(routeId, data);
        }

        return result;
    }

    @Override
    public List<PathLimiter> selectList() {
        List<PathLimiter> list = dataTemplate.selectList(TABLE_NAME, null, CLASS_MAPPER);
        if (list != null) {
            return list;
        } else {
            LOGGER.error("selectList failed, list must not be null");
            return null;
        }
    }

    /**
     * 解析
     *
     * @param record PathLimiter
     * @return PathLimiter
     */
    public PathLimiter parse(PathLimiter record) {
        if (record == null) {
            LOGGER.error("parse failed, record must not be null");
            return null;
        }

        Integer id = record.getId();
        String routeId = record.getRouteId();
        String uriPath = record.getUriPath();
        Integer totalSeconds = record.getTotalSeconds();
        Integer totalPermits = record.getTotalPermits();
        if (id == null ||
                routeId == null ||
                uriPath == null ||
                totalSeconds == null ||
                totalPermits == null) {
            LOGGER.error("parse failed, id and routeId and uriPath and totalSeconds and totalPermits must not be null, record: {}", record);
            return null;
        }

        PathLimiter result = new PathLimiter();

        result.setId(id);
        result.setRouteId(routeId.trim());
        result.setUriPath(uriPath.trim());
        result.setTotalSeconds(totalSeconds);
        result.setTotalPermits(totalPermits);

        return result;
    }

    /**
     * 拼接限流名称
     *
     * @param routeId 路由id
     * @param uriPath 路径
     * @return 表名 + 路由id + 路径
     */
    public String prefixedUriPath(String routeId, String uriPath) {
        return TABLE_NAME + SEPARATOR + routeId + SEPARATOR + uriPath;
    }

}
