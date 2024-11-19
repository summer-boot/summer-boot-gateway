package io.github.summer.boot.gateway.scheduler;

import io.github.summer.boot.gateway.service.RefreshListService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 刷新列表
 *
 * @author changebooks@qq.com
 */
@Configuration
public class RefreshListScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshListScheduler.class);

    @Resource
    private List<RefreshListService> refreshListServiceList;

    /**
     * 异步刷新
     */
    public void asyncRefresh() {
        if (refreshListServiceList == null) {
            LOGGER.warn("asyncRefresh warning, refreshListServiceList is null");
            return;
        }

        for (RefreshListService refreshListService : refreshListServiceList) {
            if (refreshListService == null) {
                LOGGER.warn("asyncRefresh warning, refreshListService is null");
                continue;
            }

            try {
                refreshListService.asyncRefresh();
            } catch (Throwable ex) {
                LOGGER.warn("asyncRefresh warning, throwable: ", ex);
            }
        }
    }

    /**
     * 刷新列表
     */
    public void refreshList() {
        if (refreshListServiceList == null) {
            LOGGER.warn("refreshList warning, refreshListServiceList is null");
            return;
        }

        for (RefreshListService refreshListService : refreshListServiceList) {
            if (refreshListService == null) {
                LOGGER.warn("refreshList warning, refreshListService is null");
                continue;
            }

            try {
                refreshListService.refreshList();
            } catch (Throwable ex) {
                LOGGER.warn("refreshList warning, throwable: ", ex);
            }
        }
    }

}
