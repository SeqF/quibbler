package com.ps.quibbler.service;

import com.ps.quibbler.model.dto.SysUserLoginParam;
import com.ps.quibbler.model.dto.SysUserRegisterParam;
import com.ps.quibbler.model.entity.SysUser;

/**
 * @author Paksu
 */
public interface LoginService {

    /**
     * User login
     * @param param
     * @return user token
     */
    String login(SysUserLoginParam param);

    /**
     * User logout,delete user token saved in redis
     * @param token
     */
    void logout(String token);

    /**
     * User register
     * @param param
     * @return
     */
    String register(SysUserRegisterParam param);

    SysUser checkToken(String token);
}
