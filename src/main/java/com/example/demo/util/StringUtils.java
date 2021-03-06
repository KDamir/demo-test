package com.example.demo.util;

public class StringUtils {

    public static boolean isNotEmpty(String value){
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value){
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
