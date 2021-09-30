package com.newrelic.codingchallenge.numberprocessing;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private static ConcurrentHashMap.KeySetView<String, Boolean> lookupSet;
    private static Cache cacheObject = null;

    private Cache() {
        lookupSet = ConcurrentHashMap.newKeySet();
    }

    public static Cache getCacheObject() {
        if (cacheObject == null) {
            cacheObject = new Cache();
        }
        return cacheObject;
    }

    /*
        adds number to cache
     */
    public boolean addNumber(String key) {
        if (key == null || key.isEmpty()) {
            return false;
        }
        return lookupSet.add(key);
    }
}
