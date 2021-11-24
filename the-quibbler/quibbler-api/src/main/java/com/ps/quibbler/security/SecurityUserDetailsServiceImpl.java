package com.ps.quibbler.security;

import com.ps.quibbler.pojo.po.Role;
import com.ps.quibbler.pojo.po.SysUser;
import com.ps.quibbler.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
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
        return new SecurityUserDetails(user.getId(), user.getAccount(), user.getPassword(),
                getGrantedAuthorities(user.getId()));
    }

    /**
     * get user role by id
     *
     * @param id user Id
     * @return authorities
     */
    private List<GrantedAuthority> getGrantedAuthorities(String id) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        List<Role> roleList = sysUserService.getRoleByUserId(id);
        roleList.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode())));

        return authorities;
    }
}
