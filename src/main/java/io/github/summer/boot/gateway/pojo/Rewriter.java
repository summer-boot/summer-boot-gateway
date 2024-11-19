package io.github.summer.boot.gateway.pojo;

import io.github.summer.boot.gateway.util.JsonParser;

import java.io.Serializable;

/**
 * 重写
 *
 * @author changebooks@qq.com
 */
public final class Rewriter implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 类型，协议、域名、端口、路径、请求参数、请求头、请求体、响应体
     */
    private Integer rewriteType;

    /**
     * 名称
     */
    private String rewriteName;

    /**
     * 排序
     */
    private Integer rewriteOrder;

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

    public Integer getRewriteType() {
        return rewriteType;
    }

    public void setRewriteType(Integer rewriteType) {
        this.rewriteType = rewriteType;
    }

    public String getRewriteName() {
        return rewriteName;
    }

    public void setRewriteName(String rewriteName) {
        this.rewriteName = rewriteName;
    }

    public Integer getRewriteOrder() {
        return rewriteOrder;
    }

    public void setRewriteOrder(Integer rewriteOrder) {
        this.rewriteOrder = rewriteOrder;
    }

}
