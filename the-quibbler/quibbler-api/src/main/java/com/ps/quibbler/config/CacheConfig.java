package com.ps.quibbler.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.ps.quibbler.global.Constants.CACHE_PERMISSION;
import static com.ps.quibbler.global.Constants.CACHE_USER;

/**
 * @author paksu
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                //设置初始容量
                .initialCapacity(10)
                //缓存失效时间
                .refreshAfterWrite(1, TimeUnit.DAYS);
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setCacheLoader(cacheLoader());
        caffeineCacheManager.setCacheNames(Arrays.asList(CACHE_USER, CACHE_PERMISSION));
        return caffeineCacheManager;
    }

    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        return new CacheLoader<Object, Object>() {
            @Override
            public @Nullable Object load(Object o) throws Exception {
                return null;
            }

            //缓存失效时调用
            @Override
            public @Nullable Object reload(Object key, Object oldValue) throws Exception {
                return oldValue;
            }
        };
    }
}
