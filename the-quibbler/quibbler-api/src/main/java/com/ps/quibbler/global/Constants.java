package com.ps.quibbler.global;

/**
 * @author ps
 */
public class Constants {

    private Constants(){
        throw new IllegalStateException("Constants class");
    }

    /**
     * Article Constants
     */
    public static final int ARTICLE_TOP = 1;
    public static final int ARTICLE_COMMON = 0;

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
}
