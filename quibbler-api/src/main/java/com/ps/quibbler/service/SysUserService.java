package com.ps.quibbler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ps.quibbler.pojo.po.Role;
import com.ps.quibbler.pojo.po.SysUser;
import com.ps.quibbler.pojo.vo.LoginSysUserVO;

import java.util.List;

/**
 * @author Paksu
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserByAccountAndPassword(String account, String password);

    LoginSysUserVO getUserByToken(String token);

    SysUser getUserByAccount(String account);

    List<Role> getRoleByUserId(String id);
}
