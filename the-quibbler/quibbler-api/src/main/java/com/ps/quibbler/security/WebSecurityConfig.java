package com.ps.quibbler.security;

import com.ps.quibbler.security.handler.JwtAccessDenieHandler;
import com.ps.quibbler.security.handler.JwtAuthenticationFilter;
import com.ps.quibbler.security.voter.AccessDecisionProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author paksu
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] URL_WHITELIST = {
            "/login",
            "/test"
    };

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAccessDenieHandler jwtAccessDenieHandler() {
        return new JwtAccessDenieHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AccessDecisionVoter<FilterInvocation> accessDecisionProcessor() {
        return new AccessDecisionProcessor();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        // WebExpressionVoter 用于解析配置文件中的放行规则
        // AccessDecisionProcessor 是自定义投票器，用于查看当前用户权限URI是否包含其请求的URI
        List<AccessDecisionVoter<?>> accessDecisionVoters = Arrays.asList(new WebExpressionVoter(),
                accessDecisionProcessor());
        return new UnanimousBased(accessDecisionVoters);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                // disable CSRF
                .csrf().disable()
                // disable Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // configure authentication role
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()
                // use custom accessDecisionManager
                .accessDecisionManager(accessDecisionManager())

                .and()
                .exceptionHandling()
                // 添加权限不足异常处理器
                .accessDeniedHandler(jwtAccessDenieHandler())
                // 添加未登录异常处理器
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())

                // configure custom filter
                .and()
                // 添加自定义JWT过滤器
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
