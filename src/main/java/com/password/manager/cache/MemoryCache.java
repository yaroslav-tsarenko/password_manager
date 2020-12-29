package com.password.manager.cache;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache {

    private static volatile ConcurrentHashMap<String, String> cache = new ConcurrentHashMap();

    public static void readProps(String src) {
        String[] lines = src.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] kv = lines[i].split("=");
            cache.put(kv[0], kv[1]);
        }
    }

    public static void setProperty(String key, String value) {
        cache.put(key, value);
    }

    public static String getProperty(String key) {
        return cache.get(key);
    }

    public static void clearCache() {
        cache.clear();
    }

    public static boolean notEmpty() {
        return !cache.isEmpty();
    }

    public static boolean hasProperty(String key) {
        if (cache.containsKey(key)) {
            return cache.containsValue(cache.get(key));
        }
        return false;
    }
}

