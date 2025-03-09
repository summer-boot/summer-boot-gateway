DROP TABLE IF EXISTS rewriter;
CREATE TABLE rewriter
(
    id            int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    route_id      varchar(64)      NOT NULL DEFAULT '' COMMENT '路由id',
    rewrite_type  int(10) unsigned NOT NULL DEFAULT '0' COMMENT '类型，协议、域名、端口、路径、请求参数、请求头、请求体、响应体',
    rewrite_name  varchar(255)     NOT NULL DEFAULT '' COMMENT '名称',
    rewrite_order int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
    PRIMARY KEY (id),
    UNIQUE KEY idx_unique_route_id_rewrite_type_rewrite_name (route_id, rewrite_type, rewrite_name),
    KEY idx_rewrite_name (rewrite_name)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT = '重写';

INSERT INTO rewriter
VALUES (1, 'qq', 1, 'scheme_1', 1);

INSERT INTO rewriter
VALUES (2, 'qq', 2, 'host_1', 1);

INSERT INTO rewriter
VALUES (3, 'qq', 3, 'port_1', 1);

INSERT INTO rewriter
VALUES (4, 'qq', 4, 'path_1', 1);

INSERT INTO rewriter
VALUES (5, 'qq', 5, 'query_1', 1);

INSERT INTO rewriter
VALUES (6, 'qq', 6, 'request_header_1', 1);

INSERT INTO rewriter
VALUES (7, 'qq', 7, 'request_body_1', 1);

INSERT INTO rewriter
VALUES (8, 'qq', 8, 'response_body_1', 1);
