package com.ps.quibbler.pojo.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AccessToken {

    private String loginAccount;
    private String token;
    private Date expiration;
}
