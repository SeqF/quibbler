package com.ps.quibbler.controller;

import com.ps.quibbler.global.Result;
import com.ps.quibbler.model.dto.SysUserLoginParam;
import com.ps.quibbler.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Paksu
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping
    public Result sysUserLogin(@RequestBody SysUserLoginParam param) {
        return Result.successWithData(loginService.login(param));
    }

}
