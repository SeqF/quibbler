package com.ps.quibbler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.pojo.dao.mapper.PermissionMapper;
import com.ps.quibbler.pojo.po.Permission;
import com.ps.quibbler.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author ps
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
