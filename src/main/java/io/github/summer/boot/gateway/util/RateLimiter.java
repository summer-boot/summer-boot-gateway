package io.github.summer.boot.gateway.util;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 分布式限流（固定时间窗口）
 * x秒内，许可n次
 *
 * @author changebooks@qq.com
 */
public final class RateLimiter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiter.class);

    /**
     * 脚本路径
     */
    private static final String SCRIPT_PATH = "rate-limiter.lua";

    /**
     * 脚本命令
     */
    private static final DefaultRedisScript<Boolean> SCRIPT = new DefaultRedisScript<>();

    static {
        ClassPathResource pathResource = new ClassPathResource(SCRIPT_PATH);
        ResourceScriptSource scriptSource = new ResourceScriptSource(pathResource);

        SCRIPT.setResultType(Boolean.class);
        SCRIPT.setScriptSource(scriptSource);
    }

    /**
     * Redis Template
     */
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    public RateLimiter(ReactiveStringRedisTemplate reactiveStringRedisTemplate) {
        this.reactiveStringRedisTemplate = reactiveStringRedisTemplate;
    }

    /**
     * 创建 {@link Parameter} 实例
     *
     * @param name         名称
     * @param totalSeconds 总秒数（x秒内）
     * @param totalPermits 总许可数（许可n次）
     * @return {@link Parameter} 实例
     */
    public static Parameter createParameter(String name, int totalSeconds, int totalPermits) {
        Assert.hasText(name, "name must not be empty");
        Assert.isTrue(totalSeconds > 0, "totalSeconds must be greater than 0");
        Assert.isTrue(totalPermits > 0, "totalPermits must be greater than 0");

        String seconds = String.valueOf(totalSeconds);
        String permits = String.valueOf(totalPermits);

        List<String> keys = Collections.singletonList(name);
        List<String> args = Arrays.asList(seconds, permits);

        return new Parameter(keys, args);
    }

    /**
     * 获取许可
     *
     * @param parameter 参数
     * @return 得到许可？
     */
    public Mono<Boolean> acquire(@NotNull Parameter parameter) {
        List<String> keys = parameter.keys();
        List<String> args = parameter.args();

        return reactiveStringRedisTemplate
                .execute(SCRIPT, keys, args)
                .reduce((r1, r2) -> r1 != null && r2 != null && r1 && r2)
                .onErrorResume(ex -> {
                    LOGGER.error("acquire failed, parameter: {}, throwable: ", JsonParser.toJson(parameter), ex);
                    return Mono.just(true);
                });
    }

    /**
     * 参数
     *
     * @param keys [ Name ]
     * @param args [ TotalSeconds, TotalPermits ]
     */
    public record Parameter(@NotEmpty List<String> keys, @NotEmpty List<String> args) implements Serializable {
        public Parameter(List<String> keys, List<String> args) {
            this.keys = keys;
            this.args = args;
        }

        @Override
        public List<String> keys() {
            return keys;
        }

        @Override
        public List<String> args() {
            return args;
        }

    }

}
