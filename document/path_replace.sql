DROP TABLE IF EXISTS path_replace;
CREATE TABLE path_replace
(
    id           int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    route_id     varchar(64)      NOT NULL DEFAULT '' COMMENT '路由id',
    raw_path     varchar(255)     NOT NULL DEFAULT '' COMMENT '原始路径',
    replace_path varchar(255)     NOT NULL DEFAULT '' COMMENT '改后路径',
    PRIMARY KEY (id),
    UNIQUE KEY idx_unique_route_id_raw_path (route_id, raw_path),
    KEY idx_raw_path (raw_path)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT = '路径替换';
