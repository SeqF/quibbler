package com.ps.quibbler.global;

/**
 * @author ps
 */
public class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    /**
     * Result Constants
     */
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    /**
     * Page Constants
     */
    public static final int PAGE_NUM = 1;
    public static final int PAGE_SIZE = 10;

    /**
     * Md5 salt
     */
    public static final String SALT = "ioi?@#";

    /**
     * Redis key
     */
    public static final String REDIS_TOKEN = "TOKEN_";

    /**
     * Cache Key
     */
    public static final String CACHE_USER = "USER";
    public static final String CACHE_PERMISSION = "PERMISSION";

    /**
     * RabbitMQ
     */
    public static final String QUEUE_NAME = "quibblerMQ";
    public static final String FAN_OUT_EXCHANGE_NAME = "fanOutExchange";
    public static final String FAN_OUT_QUEUE_NAME_1 = "fanOut1";
    public static final String FAN_OUT_QUEUE_NAME_2 = "fanOut2";
    public static final String DIRECT_EXCHANGE_NAME = "directExchange";
    public static final String DIRECT_QUEUE_NAME_1 = "direct1";
    public static final String DIRECT_QUEUE_NAME_2 = "direct2";
}
