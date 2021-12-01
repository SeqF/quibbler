package com.ps.quibbler.security;

import com.ps.quibbler.pojo.po.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.PipedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装的Security User 信息
 * @author paksu
 */
@Data
public class SecurityUserDetails implements UserDetails {


    private final String id;
    private final String account;
    private final String password;
    private final List<Role> roleList;
    private Collection<? extends GrantedAuthority> authorities;
    private List<String> roles;

    public SecurityUserDetails(String id, String account, String password,
                               List<Role> roleList) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.roleList = roleList;
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        roleList.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
            roles.add(role.getCode());
        });

        this.authorities = authorities;
        this.roles = roles;
        
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
