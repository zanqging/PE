package com.jinzht.pro.view;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/12/3.
 */
public class CopyFile {
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {                  //�ļ�����ʱ
                InputStream inStream = new FileInputStream(oldPath);      //����ԭ�ļ�
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;            //�ֽ��� �ļ���С
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }  catch (Exception e) {
            Log.e("file","���Ƶ����ļ���������");
            e.printStackTrace();
        }
    }
}
