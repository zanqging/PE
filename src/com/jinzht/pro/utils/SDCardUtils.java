package com.jinzht.pro.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/8/2
 * @time 23:20
 */
public class SDCardUtils {
    private SDCardUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * �ж�SDCard�Ƿ����
     *
     * @return
     */
    public static boolean isSDCardEnable()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * ��ȡSD��·��
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * ��ȡSD����ʣ������ ��λbyte
     *
     * @return
     */
    public static long getSDCardAllSize()
    {
        if (isSDCardEnable())
        {
            StatFs stat = new StatFs(getSDCardPath());
            // ��ȡ���е����ݿ������
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // ��ȡ�������ݿ�Ĵ�С��byte��
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * ��ȡָ��·�����ڿռ��ʣ����������ֽ�������λbyte
     *
     * @param filePath
     * @return �����ֽ� SDCard���ÿռ䣬�ڲ��洢���ÿռ�
     */
    public static long getFreeBytes(String filePath)
    {
        // �����sd�����µ�·�������ȡsd����������
        if (filePath.startsWith(getSDCardPath()))
        {
            filePath = getSDCardPath();
        } else
        {// ������ڲ��洢��·�������ȡ�ڴ�洢�Ŀ�������
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * ��ȡϵͳ�洢·��
     *
     * @return
     */
    public static String getRootDirectoryPath()
    {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
