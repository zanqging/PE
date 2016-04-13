package com.jinzht.pro.badgeview.utils;


import com.jinzht.pro.badgeview.values.IValue;
import com.jinzht.pro.badgeview.values.TextValue;

public class StringUtils {

    public static boolean isNumber(IValue value) {
        if (value instanceof TextValue) {
            CharSequence str = ((TextValue) value).getValue();
            return isNumber(str);
        } else {
            return false;
        }
    }

    public static boolean isNumber(CharSequence str) {
        try {
            Double.parseDouble(str.toString());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
