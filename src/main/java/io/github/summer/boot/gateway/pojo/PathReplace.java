package io.github.summer.boot.gateway.pojo;

import io.github.summer.boot.gateway.util.JsonParser;

import java.io.Serializable;

/**
 * 路径替换
 *
 * @author changebooks@qq.com
 */
public final class PathReplace implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 原始路径
     */
    private String rawPath;

    /**
     * 改后路径
     */
    private String replacePath;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRawPath() {
        return rawPath;
    }

    public void setRawPath(String rawPath) {
        this.rawPath = rawPath;
    }

    public String getReplacePath() {
        return replacePath;
    }

    public void setReplacePath(String replacePath) {
        this.replacePath = replacePath;
    }

}
