package com.ps.quibbler.security.voter;

import com.alibaba.fastjson.JSON;
import com.ps.quibbler.pojo.po.Permission;
import com.ps.quibbler.security.SecurityUserDetails;
import com.ps.quibbler.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import sun.security.util.Cache;

import java.util.Collection;
import java.util.List;

/**
 * 自定义投票 用于用户的鉴权
 * @author ps
 */
@Slf4j
public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private Cache caffeineCache;

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {

        String requestUri = object.getRequest().getRequestURI();
        String method = object.getRequest().getMethod();
        log.debug("进入自定义鉴权投票器,URI:{} {}",  method, requestUri);

        String key = requestUri + ":" + method;

        //获取在缓存中的系统权限
        Permission permission = JSON.parseObject(redisUtil.get(key), Permission.class);
        if (permission == null) {
            return ACCESS_ABSTAIN;
        }
        //获取当前登录用户的权限
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getRoles();
        if (roles.contains(permission.getUri())) {
            return ACCESS_GRANTED;
        }else {
            return ACCESS_DENIED;
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
