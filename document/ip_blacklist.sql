DROP TABLE IF EXISTS ip_blacklist;
CREATE TABLE ip_blacklist
(
    id       int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    route_id varchar(64)      NOT NULL DEFAULT '' COMMENT '路由id',
    ip_addr  varchar(64)      NOT NULL DEFAULT '' COMMENT 'ip地址',
    PRIMARY KEY (id),
    UNIQUE KEY idx_unique_route_id_ip_addr (route_id, ip_addr),
    KEY idx_ip_addr (ip_addr)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT = 'ip黑名单';
