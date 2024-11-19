package io.github.summer.boot.gateway.scheduler;

import io.github.summer.boot.gateway.controller.HealthController;
import jakarta.annotation.Resource;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * WebServer Initialized
 *
 * @author changebooks@qq.com
 */
@Configuration
public class WebServerInitializedListener implements ApplicationListener<WebServerInitializedEvent> {

    @Resource
    private HealthController healthController;

    @Resource
    private RefreshListScheduler refreshListScheduler;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        refreshListScheduler.refreshList();
        healthController.online();
    }

    /**
     * 定时刷新
     */
    @Scheduled(cron = "0/59 * * * * ?")
    public void scheduleRefresh() {
        refreshListScheduler.asyncRefresh();
    }

}
