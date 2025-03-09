DROP TABLE IF EXISTS router;
CREATE TABLE router
(
    route_id         varchar(64)      NOT NULL DEFAULT '' COMMENT 'id',
    route_predicates varchar(2048)    NOT NULL DEFAULT '' COMMENT '断言',
    route_filters    varchar(2048)    NOT NULL DEFAULT '' COMMENT '过滤',
    route_uri        varchar(1024)    NOT NULL DEFAULT '' COMMENT 'uri',
    route_metadata   varchar(2048)    NOT NULL DEFAULT '' COMMENT '属性',
    route_order      int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
    PRIMARY KEY (route_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '路由';

INSERT INTO router (route_id,
                    route_predicates,
                    route_filters,
                    route_uri,
                    route_metadata,
                    route_order)
VALUES ('qq',
        '[{"args":{"_genkey_0":"GET","_genkey_1":"POST"},"name":"Method"},{"args":{"_genkey_0":"qq.gw.com:8080"},"name":"Host"}]',
        '[]',
        'https://www.qq.com',
        '{"connect-timeout": "1000", "response-timeout": "20000"}',
        1);

INSERT INTO router (route_id,
                    route_predicates,
                    route_filters,
                    route_uri,
                    route_metadata,
                    route_order)
VALUES ('baidu',
        '[{"args":{"_genkey_0":"GET","_genkey_1":"POST"},"name":"Method"},{"args":{"_genkey_0":"baidu.gw.com:8080"},"name":"Host"}]',
        '[]',
        'https://www.baidu.com',
        '{"connect-timeout": "1000", "response-timeout": "20000"}',
        2);
