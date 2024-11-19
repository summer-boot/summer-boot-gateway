package io.github.summer.boot.gateway.repository.impl;

import io.github.summer.boot.gateway.pojo.IpLimiter;
import io.github.summer.boot.gateway.repository.IpLimiterRepository;
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
public class IpLimiterRepositoryImpl implements IpLimiterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpLimiterRepositoryImpl.class);

    /**
     * Table Name
     */
    private static final String TABLE_NAME = "ip_limiter";

    /**
     * Class Mapper
     */
    private static final Class<IpLimiter> CLASS_MAPPER = IpLimiter.class;

    /**
     * Name Separator
     */
    private static final String SEPARATOR = ":";

    /**
     * Data Template
     */
    private final DataTemplate dataTemplate;

    public IpLimiterRepositoryImpl(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    @Override
    public Map<String, RateLimiter.Parameter> selectAll() {
        List<IpLimiter> list = selectList();
        if (list == null) {
            LOGGER.error("selectAll failed, list must not be null");
            return null;
        }

        Map<String, RateLimiter.Parameter> result = new HashMap<>();

        for (IpLimiter item : list) {
            IpLimiter record = parse(item);
            if (record == null) {
                LOGGER.warn("selectAll warning, record is null, item: {}", item);
                continue;
            }

            String routeId = record.getRouteId();
            int totalSeconds = record.getTotalSeconds();
            int totalPermits = record.getTotalPermits();
            if (routeId.isEmpty() ||
                    totalSeconds <= 0 ||
                    totalPermits <= 0) {
                LOGGER.warn("selectAll warning, routeId or totalSeconds or totalPermits is illegal, record: {}, item: {}", record, item);
                continue;
            }

            String name = prefixedRouteId(routeId);
            RateLimiter.Parameter parameter = RateLimiter.createParameter(name, totalSeconds, totalPermits);

            result.put(routeId, parameter);
        }

        return result;
    }

    @Override
    public List<IpLimiter> selectList() {
        List<IpLimiter> list = dataTemplate.selectList(TABLE_NAME, null, CLASS_MAPPER);
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
     * @param record IpLimiter
     * @return IpLimiter
     */
    public IpLimiter parse(IpLimiter record) {
        if (record == null) {
            LOGGER.error("parse failed, record must not be null");
            return null;
        }

        Integer id = record.getId();
        String routeId = record.getRouteId();
        Integer totalSeconds = record.getTotalSeconds();
        Integer totalPermits = record.getTotalPermits();
        if (id == null ||
                routeId == null ||
                totalSeconds == null ||
                totalPermits == null) {
            LOGGER.error("parse failed, id and routeId and totalSeconds and totalPermits must not be null, record: {}", record);
            return null;
        }

        IpLimiter result = new IpLimiter();

        result.setId(id);
        result.setRouteId(routeId.trim());
        result.setTotalSeconds(totalSeconds);
        result.setTotalPermits(totalPermits);

        return result;
    }

    /**
     * 拼接限流名称前缀
     *
     * @param routeId 路由id
     * @return 表名 + 路由id
     */
    public String prefixedRouteId(String routeId) {
        return TABLE_NAME + SEPARATOR + routeId + SEPARATOR;
    }

}
