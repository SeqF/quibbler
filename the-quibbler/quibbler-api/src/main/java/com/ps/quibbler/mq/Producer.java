package com.ps.quibbler.mq;

import com.ps.quibbler.pojo.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author ps
 */
@Component
@Slf4j
public class Producer {

    public final static String QUEUE_NAME = "quibblerMQ";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String message = "Hello" + LocalDateTime.now();

        log.info("待发送消息为：{}", message);

        MessageProperties properties =
                MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

        rabbitTemplate.send(QUEUE_NAME, new Message(message.getBytes(StandardCharsets.UTF_8), properties));
        log.info("发送完毕");
    }

    public void convertAndSend() {
        SysUser user = new SysUser();

        rabbitTemplate.convertAndSend(QUEUE_NAME, user);
    }

}
