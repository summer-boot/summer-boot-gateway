package io.github.summer.boot.gateway.repository.impl;

import io.github.summer.boot.gateway.pojo.IpBlacklist;
import io.github.summer.boot.gateway.repository.IpBlacklistRepository;
import io.github.summer.boot.gateway.util.DataTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author changebooks@qq.com
 */
@Repository
public class IpBlacklistRepositoryImpl implements IpBlacklistRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpBlacklistRepositoryImpl.class);

    /**
     * Table Name
     */
    private static final String TABLE_NAME = "ip_blacklist";

    /**
     * Class Mapper
     */
    private static final Class<IpBlacklist> CLASS_MAPPER = IpBlacklist.class;

    /**
     * Data Template
     */
    private final DataTemplate dataTemplate;

    public IpBlacklistRepositoryImpl(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    @Override
    public Map<String, Set<String>> selectAll() {
        List<IpBlacklist> list = selectList();
        if (list == null) {
            LOGGER.error("selectAll failed, list must not be null");
            return null;
        }

        Map<String, Set<String>> result = new HashMap<>();

        for (IpBlacklist item : list) {
            IpBlacklist record = parse(item);
            if (record == null) {
                LOGGER.warn("selectAll warning, record is null, item: {}", item);
                continue;
            }

            String routeId = record.getRouteId();
            String ipAddr = record.getIpAddr();
            if (routeId.isEmpty() || ipAddr.isEmpty()) {
                LOGGER.warn("selectAll warning, routeId or ipAddr is empty, record: {}, item: {}", record, item);
                continue;
            }

            Set<String> data = result.getOrDefault(routeId, new HashSet<>());
            data.add(ipAddr);

            result.put(routeId, data);
        }

        return result;
    }

    @Override
    public List<IpBlacklist> selectList() {
        List<IpBlacklist> list = dataTemplate.selectList(TABLE_NAME, null, CLASS_MAPPER);
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
     * @param record IpBlacklist
     * @return IpBlacklist
     */
    public IpBlacklist parse(IpBlacklist record) {
        if (record == null) {
            LOGGER.error("parse failed, record must not be null");
            return null;
        }

        Integer id = record.getId();
        String routeId = record.getRouteId();
        String ipAddr = record.getIpAddr();
        if (id == null ||
                routeId == null ||
                ipAddr == null) {
            LOGGER.error("parse failed, id and routeId and ipAddr must not be null, record: {}", record);
            return null;
        }

        IpBlacklist result = new IpBlacklist();

        result.setId(id);
        result.setRouteId(routeId.trim());
        result.setIpAddr(ipAddr.trim());

        return result;
    }

}
