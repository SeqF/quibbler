package com.ps.quibbler.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ps.quibbler.global.Constants.*;

/**
 * @author ps
 */
@Component
//@RabbitListener(queues = Producer.QUEUE_NAME)
@Slf4j
public class RabbitConsumer {

    /**
     * 测试队列接收消息
     *
     * @param msg
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = QUEUE_NAME)
    public void onMessage(@Payload String msg, Channel channel, Message message) throws IOException {
        log.info("Message content :" + msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                log.error("消息重复处理，拒绝接收");
            } else {
                log.error("消息返回队列处理");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

//    @RabbitHandler
//    public void onMessage(@Payload SysUser user) {
//        System.out.println("Message content :" + user);
//    }

    /**
     * 测试 fanout 队列接收消息
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = FAN_OUT_QUEUE_NAME_1)
    public void onMessageFanOut1(Message message, Channel channel) throws IOException {
        log.info("Message content: {}", message);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                log.error("消息重复处理，拒绝接收");
            } else {
                log.error("消息返回队列处理");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = FAN_OUT_QUEUE_NAME_2)
    public void onMessageFanOut2(Message message, Channel channel) throws IOException {
        log.info("Message content: {}", message);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                log.error("消息重复处理，拒绝接收");
            } else {
                log.error("消息返回队列处理");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    /**
     * 测试 Direct 交换机接收消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = DIRECT_QUEUE_NAME_1) // routing key : "sms"
    public void onMessageDirect1(Message message, Channel channel) throws IOException {
        log.info("Message content: {}", message);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                log.error("消息重复处理，拒绝接收");
            } else {
                log.error("消息返回队列处理");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(queues = DIRECT_QUEUE_NAME_2) // routing key : "mail"
    public void onMessageDirect2(Message message, Channel channel) throws IOException {
        log.info("Message content: {}", message);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                log.error("消息重复处理，拒绝接收");
            } else {
                log.error("消息返回队列处理");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}
