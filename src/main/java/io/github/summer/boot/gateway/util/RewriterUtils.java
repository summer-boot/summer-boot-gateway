package io.github.summer.boot.gateway.util;

import io.github.summer.boot.gateway.repository.RewriterRepository;
import io.github.summer.boot.gateway.rewriter.IRewriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 重写
 *
 * @author changebooks@qq.com
 */
public final class RewriterUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewriterUtils.class);

    private RewriterUtils() {
    }

    /**
     * 查询列表
     *
     * @param rewriterRepository Rewriter Repository
     * @param rewriteType        Rewrite Type
     * @param rewriterList       [ IRewriter ]
     * @return [ RouteId : [ IRewriter ] ]
     */
    public static <T extends IRewriter> Map<String, List<T>> selectMap(RewriterRepository rewriterRepository, int rewriteType, List<T> rewriterList) {
        try {
            // [ RewriteName : IRewriter ]
            Map<String, T> rewriterMap = listToMap(rewriterList);
            // [ RouteId : [ RewriteName ] ]
            Map<String, List<String>> rewriteNameMap = rewriterRepository.selectAll(rewriteType);

            // [ RouteId : [ IRewriter ] ]
            Map<String, List<T>> result = RewriterUtils.nameToRewriter(rewriterMap, rewriteNameMap);
            if (result != null) {
                LOGGER.info("selectMap trace, rewriteType: {}, result: {}",
                        rewriteType, JsonParser.toJson(RewriterUtils.rewriterToName(result)));
                return result;
            } else {
                LOGGER.warn("selectMap warning, result is null, rewriteType: {}, rewriteNameMap: {}",
                        rewriteType, JsonParser.toJson(rewriteNameMap));
                return null;
            }
        } catch (Throwable ex) {
            LOGGER.error("selectMap failed, rewriteType: {}, rewriterList: {}, throwable: ",
                    rewriteType, JsonParser.toJson(rewriterList), ex);
            return null;
        }
    }

    /**
     * IRewriter List to Map
     *
     * @param list [ IRewriter ]
     * @return [ RewriteName : IRewriter ]
     */
    public static <T extends IRewriter> Map<String, T> listToMap(List<T> list) {
        return Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> StringUtils.hasText(x.name()))
                .collect(Collectors.toMap(IRewriter::name, x -> x));
    }

    /**
     * RewriteName to IRewriter
     *
     * @param rewriterMap    [ RouteId : IRewriter ]
     * @param rewriteNameMap [ RouteId : [ RewriteName ] ]
     * @return [ RouteId : [ IRewriter ] ]
     */
    public static <T extends IRewriter> Map<String, List<T>> nameToRewriter(Map<String, T> rewriterMap, Map<String, List<String>> rewriteNameMap) {
        if (rewriterMap == null || rewriterMap.isEmpty()) {
            LOGGER.warn("nameToRewriter warning, rewriterMap is empty");
            return null;
        }

        if (rewriteNameMap == null) {
            LOGGER.warn("nameToRewriter warning, rewriteNameMap is null");
            return null;
        }

        Map<String, List<T>> result = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : rewriteNameMap.entrySet()) {
            if (entry == null) {
                LOGGER.warn("nameToRewriter warning, entry is null");
                continue;
            }

            String routeId = entry.getKey();
            if (routeId == null || routeId.isEmpty()) {
                LOGGER.warn("nameToRewriter warning, routeId is empty");
                continue;
            }

            List<String> rewriteNameList = entry.getValue();
            if (rewriteNameList == null || rewriteNameList.isEmpty()) {
                LOGGER.warn("nameToRewriter warning, rewriteNameList is empty, routeId: {}", routeId);
                continue;
            }

            List<T> rewriterList = rewriteNameList
                    .stream()
                    .filter(Objects::nonNull)
                    .map(rewriterMap::get)
                    .filter(Objects::nonNull)
                    .toList();
            if (rewriterList.isEmpty()) {
                LOGGER.warn("nameToRewriter warning, rewriterList is empty, routeId: {}, rewriteNameList: {}",
                        routeId, JsonParser.toJson(rewriteNameList));
                continue;
            }

            result.put(routeId, rewriterList);
        }

        return result;
    }

    /**
     * IRewriter to RewriteName
     *
     * @param rewriterMap [ RouteId : [ IRewriter ] ]
     * @return [ RouteId : [ RewriteName ] ]
     */
    public static <T extends IRewriter> Map<String, List<String>> rewriterToName(Map<String, List<T>> rewriterMap) {
        if (rewriterMap == null) {
            LOGGER.warn("rewriterToName warning, rewriterMap is null");
            return null;
        }

        Map<String, List<String>> result = new HashMap<>();

        for (Map.Entry<String, List<T>> entry : rewriterMap.entrySet()) {
            if (entry == null) {
                LOGGER.warn("rewriterToName warning, entry is null");
                continue;
            }

            String routeId = entry.getKey();
            if (routeId == null || routeId.isEmpty()) {
                LOGGER.warn("rewriterToName warning, routeId is empty");
                continue;
            }

            List<T> rewriterList = entry.getValue();
            if (rewriterList == null || rewriterList.isEmpty()) {
                LOGGER.warn("rewriterToName warning, rewriterList is empty, routeId: {}", routeId);
                continue;
            }

            List<String> rewriteNameList = rewriterList
                    .stream()
                    .filter(Objects::nonNull)
                    .map(IRewriter::name)
                    .filter(Objects::nonNull)
                    .toList();
            if (rewriteNameList.isEmpty()) {
                LOGGER.warn("rewriterToName warning, rewriteNameList is empty, routeId: {}, rewriterList: {}",
                        routeId, JsonParser.toJson(rewriterList));
                continue;
            }

            result.put(routeId, rewriteNameList);
        }

        return result;
    }

}
