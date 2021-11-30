package com.ps.quibbler.security.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.ps.quibbler.properties.JwtProperties;
import com.ps.quibbler.utils.JwtUtil;
import com.ps.quibbler.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ps.quibbler.global.Constants.REDIS_TOKEN;

/**
 * @author paksu
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        log.info("JWT过滤开始执行，进行获取请求头token自动登录...");
        String authToken = jwtUtil.getToken(request);
        String prefix = jwtProperties.getPrefix();

        if (CharSequenceUtil.isNotBlank(authToken) && authToken.startsWith(prefix)) {

            String token = authToken.substring(prefix.length());
            String loginAccount = jwtUtil.getSubjectFromToken(token);

            // todo save user in Security Context in login api
            if (CharSequenceUtil.isNotBlank(loginAccount) && SecurityContextHolder.getContext().getAuthentication()==null) {

                //todo put user details into redis?
                UserDetails userDetails = JSON.parseObject(redisUtil.get(REDIS_TOKEN + token), UserDetails.class);

                //拿到用户信息后进行token校验
                if (userDetails != null && jwtUtil.validateToken(token, userDetails)) {

                    //构造authentication对象
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                            userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    log.info("JWT过滤器校验请求头token自动登录,User：{}", userDetails.getUsername());
                }
            }
        }
        //往下一个过滤器
        chain.doFilter(request, response);
    }
}
