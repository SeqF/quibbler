package com.ps.quibbler.service.impl;

import com.alibaba.fastjson.JSON;
import com.ps.quibbler.enums.ErrorCodeEnum;
import com.ps.quibbler.exception.QuibblerException;
import com.ps.quibbler.pojo.bo.AccessToken;
import com.ps.quibbler.pojo.dto.SysUserLoginParam;
import com.ps.quibbler.pojo.dto.SysUserRegisterParam;
import com.ps.quibbler.pojo.po.SysUser;
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
    public AccessToken login(SysUserLoginParam param) {
        String account = param.getAccount();
        String password = param.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            throw new QuibblerException(ErrorCodeEnum.REQUEST_PARAM_VALIDATION_FAILED);
        }
        password = DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
        SysUser sysUser = sysUserService.getUserByAccountAndPassword(account, password);
        if (sysUser == null) {
            throw new QuibblerException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }
        AccessToken accessToken = jwtUtil.createToken(sysUser.getId());
        redisUtil.setEx(REDIS_TOKEN + accessToken.getToken(), JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return accessToken;
    }

    @Override
    public void logout(String token) {
        redisUtil.delete(REDIS_TOKEN + token);
    }

    @Override
    public AccessToken register(SysUserRegisterParam param) {

        String account = param.getAccount();
        String password = param.getPassword();
        String username = param.getUsername();
        //TODO global handle param validation exception
        if (StringUtils.isBlank(account) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new QuibblerException(ErrorCodeEnum.REQUEST_PARAM_VALIDATION_FAILED);
        }
        SysUser sysUser = sysUserService.getUserByAccount(account);
        if (sysUser != null) {
            throw new QuibblerException(ErrorCodeEnum.USER_EXIST);
        }
        SysUser newSysUser = new SysUser();
        newSysUser.setAccount(account);
        newSysUser.setPassword(DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8)));
        newSysUser.setUsername(username);
        newSysUser.setAvatar("/static/img/default-avatar.png");
        //TODO create a base class to set default status
        newSysUser.setAdmin(0); // 0 为false
        newSysUser.setDeleted(0);// 0 为false
        newSysUser.setSalt("");
        newSysUser.setStatus("");
        newSysUser.setEmail("");
        sysUserService.save(newSysUser);

        AccessToken accessToken = jwtUtil.createToken(newSysUser.getId());
        redisUtil.setEx(REDIS_TOKEN + accessToken.getToken(), JSON.toJSONString(newSysUser), 1, TimeUnit.DAYS);
        return accessToken;
    }

    @Override
    public SysUser checkToken(String token) {
        SysUser sysUser = null;
        if (StringUtils.isBlank(token)) {
            return null;
        }

        String username = jwtUtil.getSubjectFromToken(token);
        if (jwtUtil.validateToken(token, username)) {
            return null;
        }

        String userJson = redisUtil.get(REDIS_TOKEN + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
}
