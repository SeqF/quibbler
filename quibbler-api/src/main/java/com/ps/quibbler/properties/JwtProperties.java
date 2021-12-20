package com.ps.quibbler.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间，单位毫秒
     */
    private Long expirationTime;

    /**
     * 存放 token 的请求头
     */
    private String header;

    /**
     * token 前缀
     */
    private String prefix;
}
