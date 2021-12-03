package com.ps.quibbler.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ps.quibbler.pojo.po.Permission;
import com.ps.quibbler.service.PermissionService;
import com.ps.quibbler.service.impl.PermissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static com.ps.quibbler.global.Constants.CACHE_PERMISSION;

/**
 * 预加载
 *
 * @author ps
 */
@Component
@Slf4j
public class InitProcessor {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private Cache caffeineCache;

    @PostConstruct
    public void initPermission() {
        log.info("{}: Start set permission in cache", LocalDateTime.now());
        //todo set permission in cache when app is launch
        List<Permission> permissionList = permissionService.list();
        permissionList.forEach(permission -> {
            caffeineCache.put(CACHE_PERMISSION, permission.getUri() + ":" + permission.getMethod());
        });

    }
}
