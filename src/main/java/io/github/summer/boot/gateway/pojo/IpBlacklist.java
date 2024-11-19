package io.github.summer.boot.gateway.pojo;

import io.github.summer.boot.gateway.util.JsonParser;

import java.io.Serializable;

/**
 * ip黑名单
 *
 * @author changebooks@qq.com
 */
public final class IpBlacklist implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * ip地址
     */
    private String ipAddr;

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

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

}
