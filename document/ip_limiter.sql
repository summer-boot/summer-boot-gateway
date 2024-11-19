DROP TABLE IF EXISTS ip_limiter;
CREATE TABLE ip_limiter
(
    id            int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    route_id      varchar(64)      NOT NULL DEFAULT '' COMMENT '路由id',
    total_seconds int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总秒数（x秒内）',
    total_permits int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总许可数（许可n次）',
    PRIMARY KEY (id),
    UNIQUE KEY idx_unique_route_id (route_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT = 'ip限流';
