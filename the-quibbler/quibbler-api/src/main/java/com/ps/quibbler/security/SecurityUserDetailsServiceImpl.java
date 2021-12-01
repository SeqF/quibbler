package com.ps.quibbler.security;

import com.ps.quibbler.pojo.po.Role;
import com.ps.quibbler.pojo.po.SysUser;
import com.ps.quibbler.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paksu
 */
@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {


        // todo add redis get user?
        SysUser user = sysUserService.getUserByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在，登录失败");
        }
        List<Role> roleList = sysUserService.getRoleByUserId(user.getId());
        return new SecurityUserDetails(user.getId(), user.getAccount(), user.getPassword(),
                roleList);
    }
}
