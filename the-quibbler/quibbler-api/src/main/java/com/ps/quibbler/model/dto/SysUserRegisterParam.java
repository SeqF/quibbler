package com.ps.quibbler.model.dto;

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
