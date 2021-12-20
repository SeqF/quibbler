package com.ps.quibbler.pojo.dto;

import lombok.Data;

/**
 * @author paksu
 */
@Data
public class SysUserRegisterParam {

    private String account;

    private String password;

    private String username;
}
