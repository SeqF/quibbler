package com.ps.quibbler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.model.dao.mapper.SysUserMapper;
import com.ps.quibbler.model.entity.SysUser;
import com.ps.quibbler.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author Paksu
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
