package com.ps.quibbler.controller;

import com.ps.quibbler.global.Result;
import com.ps.quibbler.model.dto.SysUserRegisterParam;
import com.ps.quibbler.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author paksu
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody SysUserRegisterParam param) {
        return Result.successWithData(loginService.register(param));
    }
}
