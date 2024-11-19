package io.github.summer.boot.gateway.repository.impl;

import io.github.summer.boot.gateway.pojo.PathReplace;
import io.github.summer.boot.gateway.repository.PathReplaceRepository;
import io.github.summer.boot.gateway.util.DataTemplate;
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
public class PathReplaceRepositoryImpl implements PathReplaceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathReplaceRepositoryImpl.class);

    /**
     * Table Name
     */
    private static final String TABLE_NAME = "path_replace";

    /**
     * Class Mapper
     */
    private static final Class<PathReplace> CLASS_MAPPER = PathReplace.class;

    /**
     * Data Template
     */
    private final DataTemplate dataTemplate;

    public PathReplaceRepositoryImpl(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    @Override
    public Map<String, Map<String, String>> selectAll() {
        List<PathReplace> list = selectList();
        if (list == null) {
            LOGGER.error("selectAll failed, list must not be null");
            return null;
        }

        Map<String, Map<String, String>> result = new HashMap<>();

        for (PathReplace item : list) {
            PathReplace record = parse(item);
            if (record == null) {
                LOGGER.warn("selectAll warning, record is null, item: {}", item);
                continue;
            }

            String routeId = record.getRouteId();
            String rawPath = record.getRawPath();
            String replacePath = record.getReplacePath();
            if (routeId.isEmpty() ||
                    rawPath.isEmpty() ||
                    replacePath.isEmpty()) {
                LOGGER.warn("selectAll warning, routeId or rawPath or replacePath is empty, record: {}, item: {}", record, item);
                continue;
            }

            Map<String, String> data = result.getOrDefault(routeId, new HashMap<>());
            data.put(rawPath, replacePath);

            result.put(routeId, data);
        }

        return result;
    }

    @Override
    public List<PathReplace> selectList() {
        List<PathReplace> list = dataTemplate.selectList(TABLE_NAME, null, CLASS_MAPPER);
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
     * @param record PathReplace
     * @return PathReplace
     */
    public PathReplace parse(PathReplace record) {
        if (record == null) {
            LOGGER.error("parse failed, record must not be null");
            return null;
        }

        Integer id = record.getId();
        String routeId = record.getRouteId();
        String rawPath = record.getRawPath();
        String replacePath = record.getReplacePath();
        if (id == null ||
                routeId == null ||
                rawPath == null ||
                replacePath == null) {
            LOGGER.error("parse failed, id and routeId and rawPath and replacePath must not be null, record: {}", record);
            return null;
        }

        PathReplace result = new PathReplace();

        result.setId(id);
        result.setRouteId(routeId.trim());
        result.setRawPath(rawPath.trim());
        result.setReplacePath(replacePath.trim());

        return result;
    }

}
