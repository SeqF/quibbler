package com.ps.quibbler.mq;

import cn.hutool.core.lang.UUID;
import com.ps.quibbler.pojo.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.ps.quibbler.global.Constants.*;

/**
 * @author ps
 */
@Component
@Slf4j
public class RabbitProducer {

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

    /**
     * fanout 发送消息
     */
    public void sendFanOut() {

        String message = "Hello" + LocalDateTime.now();

        log.info("待发送消息为：{}", message);

        rabbitTemplate.convertAndSend(FAN_OUT_EXCHANGE_NAME, message);

        log.info("发送完毕");
    }

    /**
     * direct 发送消息
     */
    public void sendDirect() {
        String message = "Hello" + LocalDateTime.now();

        log.info("待发送消息为：{}", message);

        // 指定 routing key 来选择队列
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_NAME, "sms", message);

        log.info("发送完毕");
    }

    /**
     * 发送消息确认机制
     */
    public void sendAndConfirm() {
        String message = "Hello" + LocalDateTime.now();

        log.info("Message content: {}", message);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(QUEUE_NAME, (Object) message, correlationData);

        log.info("发送完毕");

        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            log.info("CorrelationData content: {}", data);
            log.info("Ack status:{}", ack);
            log.info("Cause:{}", cause);
            if (ack) {
                log.info("消息发送成功");
            } else {
                log.info("消息发送失败：{},出现异常：{}", data, cause);
            }
        });
    }

    /**
     * 发送消息失败返回
     */
    public void sendAndReturn() {

        String message = "Hello" + LocalDateTime.now();

        log.info("Message content: {}", message);

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("Return message: {}", returnedMessage.getMessage());
            log.info("Reply Code: {}", returnedMessage.getReplyCode());
            log.info("Reply Text: {}", returnedMessage.getReplyText());
            log.info("Exchange: {}", returnedMessage.getExchange());
            log.info("Routing Key: {}", returnedMessage.getRoutingKey());
        });

        rabbitTemplate.convertAndSend(QUEUE_NAME, message);

        log.info("发送完毕");
    }

}
