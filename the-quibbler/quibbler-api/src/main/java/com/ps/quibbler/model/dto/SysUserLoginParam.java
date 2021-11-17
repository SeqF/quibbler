package com.ps.quibbler.model.dto;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * @author Paksu
 */
@Data
public class SysUserLoginParam {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
