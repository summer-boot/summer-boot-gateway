package io.github.summer.boot.gateway.util;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Data Template
 *
 * @author changebooks@qq.com
 */
public final class DataTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataTemplate.class);

    /**
     * Jdbc Template
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Redis Template
     */
    private final StringRedisTemplate stringRedisTemplate;

    public DataTemplate(JdbcTemplate jdbcTemplate, StringRedisTemplate stringRedisTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 查询列表
     *
     * @param tableName Table Name
     * @param condition Where Condition
     * @param clazz     Class Mapper
     * @param <T>       the type of the desired object
     * @return list
     */
    public <T> List<T> selectList(String tableName, String condition, final Class<T> clazz) {
        try {
            Assert.hasText(tableName, "tableName must not be empty");
            Assert.notNull(clazz, "clazz must not be null");

            String sql = buildSql(tableName, condition);

            List<T> list = selectDb(sql, clazz);
            if (list == null) {
                return getCache(sql, clazz);
            }

            setCache(sql, list);
            return list;
        } catch (Throwable ex) {
            LOGGER.error("selectList failed, tableName: {}, condition: {}, throwable: ", tableName, condition, ex);
            return null;
        }
    }

    /**
     * 查库
     *
     * @param sql   Select Sql
     * @param clazz Class Mapper
     * @param <T>   the type of the desired object
     * @return list
     */
    public <T> List<T> selectDb(@NotNull String sql, @NotNull final Class<T> clazz) {
        try {
            RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);

            return jdbcTemplate.query(sql, rowMapper);
        } catch (Throwable ex) {
            LOGGER.error("selectDb failed, sql: {}, className: {}, throwable: ", sql, clazz.getName(), ex);
            return null;
        }
    }

    /**
     * 查询缓存
     *
     * @param sql   Select Sql
     * @param clazz Class Mapper
     * @param <T>   the type of the desired object
     * @return list
     */
    public <T> List<T> getCache(@NotNull String sql, @NotNull final Class<T> clazz) {
        String className = clazz.getName();

        String jsonStr;
        try {
            jsonStr = stringRedisTemplate.opsForValue().get(sql);
        } catch (Throwable ex) {
            LOGGER.error("getCache failed, sql: {}, className: {}, throwable: ", sql, className, ex);
            return null;
        }

        if (jsonStr == null) {
            LOGGER.warn("getCache warning, jsonStr is null, sql: {}, className: {}", sql, className);
            return null;
        }

        if (jsonStr.isEmpty()) {
            LOGGER.error("getCache failed, jsonStr must not be empty, sql: {}, className: {}", sql, className);
            return null;
        }

        List<T> list = JsonParser.fromList(jsonStr, clazz);
        if (list != null) {
            LOGGER.info("getCache trace, sql: {}, className: {}, jsonStr: {}, size: {}", sql, className, jsonStr, list.size());
        } else {
            LOGGER.warn("getCache warning, list is null, sql: {}, className: {}, jsonStr: {}", sql, className, jsonStr);
        }

        return list;
    }

    /**
     * 设置缓存
     *
     * @param sql  Select Sql
     * @param list Data List
     * @param <T>  the type of the desired object
     */
    public <T> void setCache(@NotNull String sql, @NotNull final List<T> list) {
        String jsonStr = JsonParser.toJson(list);

        if (jsonStr == null) {
            LOGGER.error("setCache failed, jsonStr must not be null, sql: {}", sql);
            return;
        }

        if (jsonStr.isEmpty()) {
            LOGGER.error("setCache failed, jsonStr must not be empty, sql: {}", sql);
            return;
        }

        try {
            stringRedisTemplate.opsForValue().set(sql, jsonStr);
            LOGGER.info("setCache trace, sql: {}, jsonStr: {}", sql, jsonStr);
        } catch (Throwable ex) {
            LOGGER.error("setCache failed, sql: {}, jsonStr: {}, throwable: ", sql, jsonStr, ex);
        }
    }

    /**
     * 查询SQL
     *
     * @param tableName Table Name
     * @param condition Where Condition
     * @return SELECT SQL
     */
    public String buildSql(@NotNull String tableName, @Nullable String condition) {
        if (condition != null) {
            return String.format("SELECT * FROM %s WHERE %s", tableName, condition);
        } else {
            return String.format("SELECT * FROM %s", tableName);
        }
    }

}
