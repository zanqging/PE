package com.jinzht.pro.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;

import java.io.*;

/**
 * Created by Administrator on 2015/10/24.
 */
public class ImageHandleUtils {

    private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;
    private static int MB = 1024 * 1024;
    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//ֻ����,��������
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//���ò�����

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//��ģʽ��Ĭ�ϵ�,�ɲ���
        newOpts.inPurgeable = true;// ͬʱ���òŻ���Ч
        newOpts.inInputShareable = true;//����ϵͳ�ڴ治��ʱ��ͼƬ�Զ�������

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//ԭ���ķ������������������ͼ���ж���ѹ��
        //��ʵ����Ч��,��Ҿ��ܳ���
        return bitmap;
    }
    public static Bitmap compress440ImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//ֻ����,��������
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 400f;//
        float ww = 720f;//
        int be = 1;
        if (w > h /*&& w > ww*/) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h /*&& h > hh*/) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//���ò�����

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//��ģʽ��Ĭ�ϵ�,�ɲ���
        newOpts.inPurgeable = true;// ͬʱ���òŻ���Ч
        newOpts.inInputShareable = true;//����ϵͳ�ڴ治��ʱ��ͼƬ�Զ�������
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        try {
            ImageTools.saveImageFile(bitmap, Environment.getExternalStorageDirectory().getPath()+"" , "/circle.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
//      return compressBmpFromBmp(bitmap);//ԭ���ķ������������������ͼ���ж���ѹ��
        //��ʵ����Ч��,��Ҿ��ܳ���
        return bitmap;
    }

    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
        new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        // options.inJustDecodeBounds=true��ô��������ʵ�ʵ�bitmap���󣬲���������ڴ�ռ䵫�ǿ��Եõ�һЩ����߽���Ϣ��ͼƬ��С����Ϣ
        // outHeight(ͼƬԭʼ�߶�)�� outWidth(ͼƬ��ԭʼ���)
        // inSampleSize��ʾ����ͼ��СΪԭʼͼƬ��С�ļ���֮һ
        // options.outWidth >> i(���������)��ʾ��outWidth/(2^i)
        while (true) {
            if ((options.outWidth >> i <= 2000)
                    && (options.outHeight >> i <= 2000)) {
                in = new BufferedInputStream(
                new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i); // ������ iΪ���η�
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        Log.e("Image_size",bitmap.getByteCount()+"");
        return bitmap;
    }
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
//        image.compress(Bitmap.CompressFormat.PNG, 30, baos);
        if (baos.toByteArray().length/1024<=200){
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            //��ByteArrayInputStream��������ͼƬ
            Bitmap bitmap = BitmapFactory.decodeStream(isBm ,null,null);
            return bitmap ;
        }
//        int options = 100;
        //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb�����ڼ���ѹ��
        //����baos�����baos
        Log.e("ss", "ya");
//        baos.reset();
        //����ѹ��options%����ѹ��������ݴ�ŵ�baos��
        image.compress(Bitmap.CompressFormat.PNG,30, baos);
        //��ѹ���������baos��ŵ�ByteArrayInputStream��
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //��ByteArrayInputStream��������ͼƬ
        Bitmap bitmap = BitmapFactory.decodeStream(isBm ,null,null);
        Log.e("image", bitmap.getByteCount() + "");
        return bitmap;
    }

    public static boolean saveBmpToSd(String dir, Bitmap bm, String filename,
                                      int quantity, boolean recyle) {
        boolean ret = true;
        if (bm == null) {
            return false;
        }


        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
            bm.recycle();
            bm = null;
            return false;
        }


        File dirPath = new File(dir);


        if (!exists(dir)) {
            dirPath.mkdirs();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }

        File file = new File(dir + filename);
        OutputStream outStream = null;
        try {
            file.createNewFile();
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, quantity, outStream);
            outStream.flush();
            outStream.close();
            Log.e("images", file.length()/1024 + "now_image_size");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ret = false;
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            ret = false;
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (recyle && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
                Log.e("BitmaptoCard", "saveBmpToSd, recyle");
            }
        }


        return ret;
    }

    /**
     * ���sdcard���ÿռ�
     *
     * @return
     */
    public static int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());

        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
                .getBlockSize()) / MB;

        return (int) sdFreeMB;
    }

    public static boolean exists(String url) {
        File file = new File(url);


        return file.exists();
    }
}
