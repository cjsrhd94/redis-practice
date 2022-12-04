package com.example.redis.common.redis;

public class CacheKey {

    private CacheKey() {

    }

    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = 1800;

    public static final String POST = "post";
    public static final int POST_EXPIRE_SEC = 1800;
}
