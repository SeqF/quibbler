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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * 自定义投票 用于用户的鉴权
 * @author ps
 */
@Slf4j
public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    RedisUtil redisUtil;


    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {

        String requestUri = object.getRequest().getRequestURI();
        String method = object.getRequest().getMethod();
        log.debug("进入自定义鉴权投票器,URI:{} {}",  method, requestUri);

        String key = requestUri + ":" + method;

        Permission permission = JSON.parseObject(redisUtil.get(key), Permission.class);
        if (permission == null) {
            return ACCESS_ABSTAIN;
        }
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return 0;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
