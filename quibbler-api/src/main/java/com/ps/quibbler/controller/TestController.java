package com.ps.quibbler.controller;

import com.ps.quibbler.global.Result;
import com.ps.quibbler.mq.RabbitProducer;
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
    private RabbitProducer rabbitProducer;
    @GetMapping
    public Result test() {
        rabbitProducer.send();
        return Result.successWithData("消息发送成功");
    }

}
