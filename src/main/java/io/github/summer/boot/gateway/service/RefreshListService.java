package io.github.summer.boot.gateway.service;

/**
 * 刷新列表
 *
 * @author changebooks@qq.com
 */
public interface RefreshListService {
    /**
     * 异步刷新
     */
    void asyncRefresh();

    /**
     * 刷新列表
     */
    void refreshList();

}
