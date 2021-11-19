package com.ps.quibbler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ps.quibbler.model.entity.SysUser;
import com.ps.quibbler.model.vo.LoginSysUserVO;

/**
 * @author Paksu
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserByAccountAndPassword(String account, String password);

    LoginSysUserVO getUserByToken(String token);

    SysUser getUserByAccount(String account);
}
