package com.jinzht.pro.utils;

/**
 * Created by Administrator on 2016/1/13.
 */
public class MathUtils {
    public static String floatNum(float fl){
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.##");
        return df.format(fl);
    }
    public static float floatNums(float value){
            float l1 = Math.round(value*100);   //四舍五入
            float ret =  l1/(float) 100.0;    //注意：使用100.0而不是100
            return ret;
    }
}
