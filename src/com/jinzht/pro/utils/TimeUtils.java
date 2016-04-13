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
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/7
 * @time 16:05
 */

public class TimeUtils {
    public static boolean compareTime(String time1,String time2) throws ParseException
    {
        //�����Ƚ�������д��"yyyy-MM-dd"�Ϳ�����
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //���ַ�����ʽ��ʱ��ת��ΪDate���͵�ʱ��
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        //Date���һ�����������a����b����true�����򷵻�false
        if(a.before(b))
            return true;
        else
            return false;
        /*
         * ����㲻ϲ�����������̫��å�ķ�����Ҳ���Ը��ݽ�Dateת���ɺ���
        if(a.getTime()-b.getTime()<0)
            return true;
        else
            return false;
        */
    }
    public static boolean compareNow(String time1) throws ParseException{
        //�����Ƚ�������д��"yyyy-MM-dd"�Ϳ�����
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
