package com.ps.quibbler.service;

import com.ps.quibbler.model.dto.SysUserLoginParam;
import com.ps.quibbler.model.entity.SysUser;

/**
 * @author Paksu
 */
public interface LoginService {

    /**
     * User login
     * @param param
     * @return
     */
    String login(SysUserLoginParam param);

    /**
     * User logout
     * @param token
     */
    void logout(String token);

    SysUser checkToken(String token);
}
