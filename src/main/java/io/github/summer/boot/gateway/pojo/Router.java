package io.github.summer.boot.gateway.pojo;

import io.github.summer.boot.gateway.util.JsonParser;

import java.io.Serializable;

/**
 * 路由
 *
 * @author changebooks@qq.com
 */
public final class Router implements Serializable {
    /**
     * id
     */
    private String routeId;

    /**
     * 断言
     */
    private String routePredicates;

    /**
     * 过滤
     */
    private String routeFilters;

    /**
     * uri
     */
    private String routeUri;

    /**
     * 属性
     */
    private String routeMetadata;

    /**
     * 排序
     */
    private Integer routeOrder;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRoutePredicates() {
        return routePredicates;
    }

    public void setRoutePredicates(String routePredicates) {
        this.routePredicates = routePredicates;
    }

    public String getRouteFilters() {
        return routeFilters;
    }

    public void setRouteFilters(String routeFilters) {
        this.routeFilters = routeFilters;
    }

    public String getRouteUri() {
        return routeUri;
    }

    public void setRouteUri(String routeUri) {
        this.routeUri = routeUri;
    }

    public String getRouteMetadata() {
        return routeMetadata;
    }

    public void setRouteMetadata(String routeMetadata) {
        this.routeMetadata = routeMetadata;
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

}
