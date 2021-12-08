package com.ps.quibbler.mq;

import com.ps.quibbler.pojo.po.SysUser;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ps
 */
@Component
//@RabbitListener(queues = Producer.QUEUE_NAME)
@Slf4j
public class Consumer {

//    @RabbitHandler
    public void onMessage(@Payload String msg, Channel channel, Message message) throws IOException {
        log.info("Message content :" + msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {
                log.error("消息重复处理，拒绝接收");
            }else {
                log.error("消息返回队列处理");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            }

        }
    }

//    @RabbitHandler
//    public void onMessage(@Payload SysUser user) {
//        System.out.println("Message content :" + user);
//    }
}
