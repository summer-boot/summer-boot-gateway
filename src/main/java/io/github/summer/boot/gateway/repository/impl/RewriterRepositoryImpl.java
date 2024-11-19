package io.github.summer.boot.gateway.repository.impl;

import io.github.summer.boot.gateway.pojo.Rewriter;
import io.github.summer.boot.gateway.repository.RewriterRepository;
import io.github.summer.boot.gateway.util.DataTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author changebooks@qq.com
 */
@Repository
public class RewriterRepositoryImpl implements RewriterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewriterRepositoryImpl.class);

    /**
     * Table Name
     */
    private static final String TABLE_NAME = "rewriter";

    /**
     * Class Mapper
     */
    private static final Class<Rewriter> CLASS_MAPPER = Rewriter.class;

    /**
     * Data Template
     */
    private final DataTemplate dataTemplate;

    public RewriterRepositoryImpl(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    @Override
    public Map<String, List<String>> selectAll(Integer rewriteType) {
        List<Rewriter> list = selectList(rewriteType);
        if (list == null) {
            LOGGER.error("selectAll failed, list must not be null, rewriteType: {}", rewriteType);
            return null;
        }

        List<Rewriter> data = list.stream()
                .map(this::parse)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Rewriter::getRewriteOrder))
                .toList();

        Map<String, List<String>> result = new HashMap<>();

        for (Rewriter record : data) {
            if (record == null) {
                LOGGER.warn("selectAll failed, record is null, rewriteType: {}", rewriteType);
                continue;
            }

            String routeId = record.getRouteId();
            String rewriteName = record.getRewriteName();
            if (routeId.isEmpty() || rewriteName.isEmpty()) {
                LOGGER.warn("selectAll failed, routeId or rewriteName is empty, record: {}, rewriteType: {}", record, rewriteType);
                continue;
            }

            List<String> rewriteNames = result.getOrDefault(routeId, new ArrayList<>());
            rewriteNames.add(rewriteName);

            result.put(routeId, rewriteNames);
        }

        return result;
    }

    @Override
    public List<Rewriter> selectList(Integer rewriteType) {
        if (rewriteType == null) {
            LOGGER.error("selectList failed, rewriteType must not be null");
            return null;
        }

        if (rewriteType <= 0) {
            LOGGER.error("selectList failed, rewriteType must be greater than 0, rewriteType: {}", rewriteType);
            return null;
        }

        String condition = String.format("rewrite_type = %d", rewriteType);
        List<Rewriter> list = dataTemplate.selectList(TABLE_NAME, condition, CLASS_MAPPER);
        if (list != null) {
            return list;
        } else {
            LOGGER.error("selectList failed, list must not be null, rewriteType: {}", rewriteType);
            return null;
        }
    }

    /**
     * 解析
     *
     * @param record Rewriter
     * @return Rewriter
     */
    public Rewriter parse(Rewriter record) {
        if (record == null) {
            LOGGER.error("parse failed, record must not be null");
            return null;
        }

        Integer id = record.getId();
        String routeId = record.getRouteId();
        Integer rewriteType = record.getRewriteType();
        String rewriteName = record.getRewriteName();
        Integer rewriteOrder = record.getRewriteOrder();
        if (id == null ||
                routeId == null ||
                rewriteType == null ||
                rewriteName == null) {
            LOGGER.error("parse failed, id and routeId and rewriteType and rewriteName must not be null, record: {}", record);
            return null;
        }

        Rewriter result = new Rewriter();

        result.setId(id);
        result.setRouteId(routeId.trim());
        result.setRewriteType(rewriteType);
        result.setRewriteName(rewriteName.trim());
        result.setRewriteOrder(rewriteOrder != null ? rewriteOrder : 0);

        return result;
    }

}
