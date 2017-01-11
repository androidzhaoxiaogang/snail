package com.snail.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地副本信息存储.
 */
public class ThreadLocalUtils {
    private static final ThreadLocal<Map<String, Object>> local = ThreadLocal.withInitial(() -> new HashMap<>());

    public static <T> T put(String key, T value) {
        local.get().put(key, value);
        return value;
    }

    public static void remove(String key) {
        local.get().remove(key);
    }

    public static void clear() {
        local.remove();
    }

    public static <T> T get(String key) {
        return ((T) local.get().get(key));
    }
}
