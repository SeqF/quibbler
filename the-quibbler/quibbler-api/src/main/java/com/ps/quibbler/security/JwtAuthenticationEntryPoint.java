package com.ps.quibbler.security;

import cn.hutool.core.text.CharSequenceUtil;
import com.ps.quibbler.global.Result;
import com.ps.quibbler.utils.JacksonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt认证失败处理器
 *
 * @author paksu
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        String msg = CharSequenceUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JacksonUtil.toJSONString(Result.errorWithMessage(msg)));
        response.getWriter().flush();
    }
}
