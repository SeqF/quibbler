package com.ps.quibbler.service;

import com.ps.quibbler.model.dto.SysUserLoginParam;

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
}
