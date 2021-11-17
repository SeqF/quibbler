package com.ps.quibbler.service.impl;

import com.ps.quibbler.enums.ErrorCodeEnum;
import com.ps.quibbler.exception.QuibblerException;
import com.ps.quibbler.model.dto.SysUserLoginParam;
import com.ps.quibbler.service.LoginService;
import com.ps.quibbler.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Paksu
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public String login(SysUserLoginParam param) {
        String username = param.getUsername();
        String password = param.getPassword();
        throw new QuibblerException(ErrorCodeEnum.REQUEST_VALIDATION_FAILED);
        return null;
    }
}
