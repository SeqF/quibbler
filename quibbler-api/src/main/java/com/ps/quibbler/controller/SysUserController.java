package com.ps.quibbler.controller;

import com.ps.quibbler.global.Result;
import com.ps.quibbler.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


/**
 * @author ps
 */
@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/currentUser")
    public Result getCurrentUser(@RequestHeader(AUTHORIZATION) String token) {
        return Result.successWithData(sysUserService.getUserByToken(token));
    }
}
