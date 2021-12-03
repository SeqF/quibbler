package com.ps.quibbler.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ps
 */
@Slf4j
@Service
public class CaffeineCache implements Cache{

    @Autowired
    private CacheManager caffeineCacheManger;

    @Override
    public <T> T get(String cacheName, String key, Class<T> clazz) {
        log.debug("{} get -> Cache name:{},Key:{},Class type:{}", this.getClass().getName(), cacheName, key,
                clazz.getName());
        return Objects.requireNonNull(caffeineCacheManger.getCache(cacheName).get(key, clazz));
    }

    @Override
    public void put(String cacheName, String key, Object value) {

    }

    @Override
    public void remove(String cacheName, String key) {

    }
}
