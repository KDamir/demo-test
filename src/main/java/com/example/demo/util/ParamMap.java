package com.example.demo.util;

import java.util.HashMap;

public class ParamMap<K,V> extends HashMap<K,V> {

    public String getString(K key) {
        V v = get(key);
        return v == null ? null : ((String) v);
    }

    public boolean isNotEmptyString(K key) {
        return StringUtils.isNotEmpty(getString(key));
    }

    public boolean isNotNull(K key) {
        return get(key) != null;
    }

}
