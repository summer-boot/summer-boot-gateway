package io.github.summer.boot.gateway.pojo;

import io.github.summer.boot.gateway.util.JsonParser;

import java.io.Serializable;

/**
 * 路径限流
 *
 * @author changebooks@qq.com
 */
public final class PathLimiter implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 路径
     */
    private String uriPath;

    /**
     * 总秒数（x秒内）
     */
    private Integer totalSeconds;

    /**
     * 总许可数（许可n次）
     */
    private Integer totalPermits;

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

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public Integer getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(Integer totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public Integer getTotalPermits() {
        return totalPermits;
    }

    public void setTotalPermits(Integer totalPermits) {
        this.totalPermits = totalPermits;
    }

}
