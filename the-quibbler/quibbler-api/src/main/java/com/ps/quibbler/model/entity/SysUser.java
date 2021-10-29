package com.ps.quibbler.model.entity;

import lombok.Data;

/**
 * @author ps
 */
@Data
public class SysUser {

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobile;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
