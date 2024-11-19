package io.github.summer.boot.gateway;

import io.github.summer.boot.gateway.util.DataTemplate;
import io.github.summer.boot.gateway.util.RateLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author changebooks@qq.com
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DataTemplate dataTemplate(JdbcTemplate jdbcTemplate, StringRedisTemplate stringRedisTemplate) {
        return new DataTemplate(jdbcTemplate, stringRedisTemplate);
    }

    @Bean
    public RateLimiter rateLimiter(ReactiveStringRedisTemplate reactiveStringRedisTemplate) {
        return new RateLimiter(reactiveStringRedisTemplate);
    }

}
