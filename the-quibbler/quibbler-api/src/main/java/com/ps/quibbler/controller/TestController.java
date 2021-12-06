package com.ps.quibbler.controller;

import com.ps.quibbler.global.Result;
import com.ps.quibbler.pojo.po.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ps
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public Result testJson() {
        return Result.successWithData(new SysUser());
    }
}
