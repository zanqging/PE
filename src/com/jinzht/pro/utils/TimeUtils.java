package com.jinzht.pro.utils;

import android.content.Context;
import com.jinzht.pro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/7
 * @time 16:05
 */

public class TimeUtils {
    public static boolean compareTime(String time1,String time2) throws ParseException
    {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if(a.before(b))
            return true;
        else
            return false;
        /*
         * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
        if(a.getTime()-b.getTime()<0)
            return true;
        else
            return false;
        */
    }
    public static boolean compareNow(String time1) throws ParseException{
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date a=sdf.parse(time1);
        String ss = sdf.format(new Date());
        Date dates = sdf.parse(ss);
        if(a.before(dates))
            return true;
        else
            return false;
    }
    public static String timeTitle(Context context)throws  ParseException{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"+context.getResources().getString(R.string.year)+"MM"+context.getResources().getString(R.string.month)+"dd"+
                context.getResources().getString(R.string.day)+" E "+"HH"+context.getResources().getString(R.string.shi)+"mm"+context.getResources().getString(R.string.minute)+"ss"
                +context.getResources().getString(R.string.second), Locale.CHINA);
        return sdf.format(date);
    }
    public static boolean isDate(String date){
        String eL = "[0-9]{4}.[0-9]{2}.[0-9]{2}";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(date);
        return m.matches();
    }

}
