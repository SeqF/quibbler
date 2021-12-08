package com.ps.quibbler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ps
 */
@Configuration
public class RabbitmqConfig {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Bean
    public Queue quibblerMq() {
        return new Queue("quibblerMQ", true);
    }

    /**
     * 传入自定义的json序列化
     * @return
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(jacksonObjectMapper);
    }
}
