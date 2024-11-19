DROP TABLE IF EXISTS path_limiter;
CREATE TABLE path_limiter
(
    id            int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    route_id      varchar(64)      NOT NULL DEFAULT '' COMMENT '路由id',
    uri_path      varchar(255)     NOT NULL DEFAULT '' COMMENT '路径',
    total_seconds int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总秒数（x秒内）',
    total_permits int(11) unsigned NOT NULL DEFAULT '0' COMMENT '总许可数（许可n次）',
    PRIMARY KEY (id),
    UNIQUE KEY idx_unique_route_id_uri_path (route_id, uri_path),
    KEY idx_uri_path (uri_path)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT = '路径限流';
