package com.ps.quibbler.controller;

import com.ps.quibbler.global.Result;
import com.ps.quibbler.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author paksu
 */
@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader(AUTHORIZATION) String token) {
        loginService.logout(token);
        return Result.successWithMessage("LOGOUT_SUCCESS");
    }
}
