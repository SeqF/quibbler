package com.ps.quibbler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.ps.quibbler.global.Constants.*;

/**
 * @author ps
 */
@Configuration
public class RabbitmqConfig {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Bean
    public Queue quibblerMq() {
        return new Queue(QUEUE_NAME, true);
    }

    /**
     * 创建 fanout 交换机并进行队列绑定
     *
     * @return
     */
    @Bean
    public FanoutExchange fanOutExchange() {
        return new FanoutExchange(FAN_OUT_EXCHANGE_NAME, false, false);
    }

    @Bean
    public Queue fanOut1() {
        return new Queue(FAN_OUT_QUEUE_NAME_1);
    }

    @Bean
    public Queue fanOut2() {
        return new Queue(FAN_OUT_QUEUE_NAME_2);
    }

    @Bean
    public Binding fanOutBinding1() {
        return BindingBuilder.bind(fanOut1()).to(fanOutExchange());
    }

    @Bean
    public Binding fanOutBinding2() {
        return BindingBuilder.bind(fanOut2()).to(fanOutExchange());
    }

    /**
     * 创建 direct 交换机并绑定队列
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, false, false);
    }

    @Bean
    public Queue directQueue1() {
        return new Queue(DIRECT_QUEUE_NAME_1);
    }

    @Bean
    public Queue directQueue2() {
        return new Queue(DIRECT_QUEUE_NAME_2);
    }

    @Bean
    public Binding directBinding1() {
        // with 后跟 routing key
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("sms");
    }

    @Bean
    public Binding directBinding2() {
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("mail");
    }

    /**
     * 创建 topic 交换机
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue("topicQueue1");
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue("topicQueue2");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange", false, false);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("sms.*");
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("mail.#");
    }

    /**
     * TTL 队列
     */
    @Bean
    public Queue ttlQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        // 设置 30s 过期
        arguments.put("x-message-ttl", 30000);
        return new Queue("topicQueue1", false, false, false, arguments);
    }

    /**
     * DLX 队列
     */
    @Bean
    public Queue dlxQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 指定消息死亡后发送到 ExchangeName = "dlx.change" 的交换机
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        return new Queue("topicQueue1", false, false, false, arguments);
    }

    /**
     * 传入自定义的json序列化
     *
     * @return
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(jacksonObjectMapper);
    }
}
