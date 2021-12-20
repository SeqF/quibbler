package com.ps.quibbler.utils;

import com.ps.quibbler.pojo.bo.AccessToken;
import com.ps.quibbler.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author paksu
 */
@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 从请求中获取token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(jwtProperties.getHeader());
    }

    /**
     * 根据user details生成token
     *
     * @param userDetails
     * @return
     */
    public AccessToken createToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    /**
     * 根据user details校验token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        return validateToken(token, userDetails.getUsername());
    }

    /**
     * 生成token
     *
     * @param subject
     * @return
     */
    public AccessToken createToken(String subject) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + jwtProperties.getExpirationTime());

        String token = jwtProperties.getPrefix() + Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();

        return AccessToken.builder()
                .loginAccount(subject)
                .token(token)
                .expiration(expiration)
                .build();
    }

    /**
     * 校验token，解析token中的信息并且校验过期时间
     *
     * @param token
     * @param username
     * @return
     */
    public boolean validateToken(String token, String username) {
        Claims claims = getClaimsFromToken(token);
        return claims != null && claims.getSubject().equals(username) && !isTokenExpired(claims);
    }

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     */
    public AccessToken refreshToken(String oldToken) {
        String token = oldToken.substring(jwtProperties.getPrefix().length());

        Claims claims = getClaimsFromToken(token);

        // 如果token在30分钟之内刷新过，则返回原token，否则返回新的token
        if (claims != null && tokenRefreshJustBefore(claims)) {
            return AccessToken.builder()
                    .loginAccount(claims.getSubject())
                    .token(oldToken)
                    .expiration(claims.getExpiration())
                    .build();
        } else {
            return createToken(claims == null ? null : claims.getSubject());
        }
    }

    /**
     * 从token中获取subject
     *
     * @param token
     * @return
     */
    public String getSubjectFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims == null ? null : claims.getSubject();
    }

    /**
     * 判断JWT是否过期
     *
     * @param claims
     * @return
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    /**
     * 从token中获取claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断token是否在指定时间内刷新
     */
    private boolean tokenRefreshJustBefore(Claims claims) {

        Date refreshDate = new Date();

        return refreshDate.after(claims.getExpiration())
                && refreshDate.before(DateUtils.addMilliseconds(refreshDate,
                jwtProperties.getExpirationTime().intValue()));
    }


}
