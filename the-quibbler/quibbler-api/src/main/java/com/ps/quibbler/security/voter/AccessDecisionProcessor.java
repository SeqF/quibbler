package com.ps.quibbler.security.voter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * 自定义投票 用于用户的鉴权
 * @author ps
 */
@Slf4j
public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {


    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {

        String requestUri = object.getRequest().getRequestURI();
        String method = object.getRequest().getMethod();
        log.debug("进入自定义鉴权投票器,URI:{} {}",  method, requestUri);

        String key = requestUri + ":" + method;

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
