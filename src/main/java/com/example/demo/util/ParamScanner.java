package com.example.demo.util;

import java.util.Map;

public class ParamScanner {
    private Map<String, Object> map;

    public ParamScanner(Map<String, Object> map) {
        this.map = map;
    }

    public String getString(String fieldName) {
        Object obj = getObjectOrThrow(fieldName);
        return obj == null ? null : obj.toString();
    }

    public Long getLong(String fieldName) {
        Object obj = getObjectOrThrow(fieldName);
        return obj == null ? null : ((Number)obj).longValue();
    }

    private Object getObjectOrThrow(String fieldName){
        if(!map.containsKey(fieldName)){
            throw new IllegalArgumentException("Incorrect fieldName: " + fieldName);
        }
        return map.get(fieldName);
    }


}
