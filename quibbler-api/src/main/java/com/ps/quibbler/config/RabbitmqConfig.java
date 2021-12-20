package com.ps.quibbler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
     * 传入自定义的json序列化
     *
     * @return
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(jacksonObjectMapper);
    }
}
