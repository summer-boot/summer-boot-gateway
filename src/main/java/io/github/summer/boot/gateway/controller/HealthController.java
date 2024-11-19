package io.github.summer.boot.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 *
 * @author changebooks@qq.com
 */
@RequestMapping("health")
@RestController
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    /**
     * 已上线？
     */
    private volatile boolean online = false;

    /**
     * 提供服务？
     *
     * @return 上线？
     */
    @GetMapping(value = "/check")
    public Boolean check() {
        LOGGER.info("check trace");

        return online;
    }

    /**
     * 上线
     */
    @PutMapping({"/online"})
    public void online() {
        LOGGER.info("online trace");

        online = true;
    }

    /**
     * 下线
     */
    @PutMapping({"/offline"})
    public void offline() {
        LOGGER.info("offline trace");

        online = false;
    }

}
