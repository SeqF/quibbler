package com.ps.quibbler.pojo.vo;

import com.ps.quibbler.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author paksu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginSysUserVO extends BaseVO {

    private String account;

    private String username;

    private String avatar;
}
