package com.jinzht.pro.utils;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/6/2
 * @time 9:50
 */

public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEquals(String actual, String expected) {
        return ObjectUtils.isEquals(actual, expected);
    }
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

}
