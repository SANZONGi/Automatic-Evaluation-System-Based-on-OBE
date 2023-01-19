package com.sanzong.obe.utils;

public class StringUtils {
    public static boolean isNotBlank(String s) {
        if (null != s && !s.isEmpty()) {
            return true;
        }
        return false;
    }
}
