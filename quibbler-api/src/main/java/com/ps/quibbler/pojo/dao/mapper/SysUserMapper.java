package com.ps.quibbler.pojo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ps.quibbler.pojo.po.Role;
import com.ps.quibbler.pojo.po.SysUser;

import java.util.List;

/**
 * @author ps
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * get Role Code by user id
     * @param userId
     * @return
     */
    List<Role> getRoleByUserId(String userId);
}
