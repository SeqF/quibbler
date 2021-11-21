package com.ps.quibbler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.enums.ErrorCodeEnum;
import com.ps.quibbler.exception.QuibblerException;
import com.ps.quibbler.pojo.dao.mapper.SysUserMapper;
import com.ps.quibbler.pojo.po.SysUser;
import com.ps.quibbler.pojo.vo.LoginSysUserVO;
import com.ps.quibbler.service.LoginService;
import com.ps.quibbler.service.SysUserService;
import com.ps.quibbler.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Paksu
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    LoginService loginService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public SysUser getUserByAccountAndPassword(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getUsername);
        queryWrapper.last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public LoginSysUserVO getUserByToken(String token) {

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            throw new QuibblerException(ErrorCodeEnum.TOKEN_VALIDATION_FAILED);
        }
        LoginSysUserVO loginSysUserVO = new LoginSysUserVO();
        loginSysUserVO.setId(sysUser.getId());
        loginSysUserVO.setUsername(sysUser.getUsername());
        loginSysUserVO.setAccount(sysUser.getAccount());
        loginSysUserVO.setAvatar(sysUser.getAvatar());
        return loginSysUserVO;
    }

    @Override
    public SysUser getUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }

}
