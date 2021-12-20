package com.ps.quibbler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.pojo.dao.mapper.RoleMapper;
import com.ps.quibbler.pojo.po.Role;
import com.ps.quibbler.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author ps
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
