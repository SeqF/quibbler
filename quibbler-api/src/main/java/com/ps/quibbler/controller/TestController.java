package com.ps.quibbler.controller;

import com.ps.quibbler.enums.ErrorCodeEnum;
import com.ps.quibbler.exception.QuibblerException;
import com.ps.quibbler.global.Result;
import com.ps.quibbler.mq.Consumer;
import com.ps.quibbler.mq.Producer;
import com.ps.quibbler.pojo.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ps
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private Producer producer;
    @GetMapping
    public Result test() {
        producer.send();
        return Result.successWithData("消息发送成功");
    }

}
