package com.ps.quibbler.service.impl;

import com.alibaba.fastjson.JSON;
import com.ps.quibbler.enums.ErrorCodeEnum;
import com.ps.quibbler.exception.QuibblerException;
import com.ps.quibbler.model.dto.SysUserLoginParam;
import com.ps.quibbler.model.entity.SysUser;
import com.ps.quibbler.service.LoginService;
import com.ps.quibbler.service.SysUserService;
import com.ps.quibbler.utils.JwtUtil;
import com.ps.quibbler.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static com.ps.quibbler.global.Constants.REDIS_TOKEN;
import static com.ps.quibbler.global.Constants.SALT;

/**
 * @author Paksu
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(SysUserLoginParam param) {
        String account = param.getAccount();
        String password = param.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            throw new QuibblerException(ErrorCodeEnum.REQUEST_PARAM_VALIDATION_FAILED);
        }
        password = DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
        SysUser sysUser = sysUserService.getUserByUsernameAndPassword(account, password);
        if (sysUser == null) {
            throw new QuibblerException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }
        String token = jwtUtil.generateToken(sysUser.getId());
        redisUtil.setEx(REDIS_TOKEN + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public void logout(String token) {
        redisUtil.delete(REDIS_TOKEN + token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        // TODO check token expired?
        Claims claim = jwtUtil.getClaimByToken(token);
        if (claim == null) {
            return null;
        }
        String userJson = redisUtil.get(REDIS_TOKEN + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }
}
