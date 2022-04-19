package com.ps.quibbler.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

@Configuration
@EnableCaching
@ConditionalOnProperty(value = "spring.redis.enabled", havingValue = "true")
public class RedisConfig {

    /**
     * 基于注解时的缓存使用,如：@Cacheable，指定 redisCacheManager
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(
                RedisCacheWriter
                        .nonLockingRedisCacheWriter(Objects
                                .requireNonNull(redisTemplate
                                        .getConnectionFactory())),
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        .serializeValuesWith(RedisSerializationContext
                                .fromSerializer(getSerializer())
                                .getValueSerializationPair())
//                        .entryTtl() 设置过期时间
        );
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);
        // 其他类型保持原样，只设置value来支持复杂类型
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setValueSerializer(getSerializer());
        template.setHashValueSerializer(getSerializer());

        template.afterPropertiesSet();

        return template;
    }

    private Jackson2JsonRedisSerializer<Object> getSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        serializer.setObjectMapper(objectMapper);
        return serializer;
    }
}
