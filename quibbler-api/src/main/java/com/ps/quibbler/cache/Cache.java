package com.ps.quibbler.cache;

/**
 * @author ps
 */
public interface Cache {

    <T> T get(String cacheName, String key, Class<T> clazz);

    void put(String cacheName, String key, Object value);

    void remove(String cacheName, String key);
}
