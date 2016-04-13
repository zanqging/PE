package com.jinzht.pro.utils;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/6/2
 * @time 9:52
 */

public class ObjectUtils {
    private ObjectUtils() {
        throw new AssertionError();
    }
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }
}
