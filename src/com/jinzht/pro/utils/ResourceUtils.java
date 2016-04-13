package com.jinzht.pro.utils;

import android.content.Context;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/6/2
 * @time 9:49
 */

public class ResourceUtils {
    private ResourceUtils() {
        throw new AssertionError();
    }

    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || StringUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getFromAssets(Context context,String fileName){
        if (context == null || StringUtils.isEmpty(fileName)) {
            return null;
        }
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            //��ȡ�ļ����ֽ���
            int lenght = in.available();
            //����byte����
            byte[]  buffer = new byte[lenght];
            //���ļ��е����ݶ���byte������
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
