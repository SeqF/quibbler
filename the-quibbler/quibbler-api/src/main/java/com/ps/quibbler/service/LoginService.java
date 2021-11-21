package com.ps.quibbler.service;

import com.ps.quibbler.pojo.bo.AccessToken;
import com.ps.quibbler.pojo.dto.SysUserLoginParam;
import com.ps.quibbler.pojo.dto.SysUserRegisterParam;
import com.ps.quibbler.pojo.po.SysUser;

/**
 * @author Paksu
 */
public interface LoginService {

    /**
     * User login
     * @param param
     * @return user token
     */
    AccessToken login(SysUserLoginParam param);

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
    AccessToken register(SysUserRegisterParam param);

    SysUser checkToken(String token);
}
