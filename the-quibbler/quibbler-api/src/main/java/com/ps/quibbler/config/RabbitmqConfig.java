package com.ps.quibbler.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author ps
 */
public class RabbitmqConfig {

    @Bean
    public Queue quibblerMq() {
        return new Queue("quibblerMQ", true);
    }
}
