package com.jinzht.pro.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jinzht.pro.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/9
 * @time 9:34
 */

public class UpLoadImageUtils {

    public static String uploadFile(String urls, Context context, String uploadFilea, String fileNames) throws Exception {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
//        try
//        {
        URL url = new URL(urls);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
        con.setDoOutput(true);
          /* Read from the connection. Default is true.*/
        con.setDoInput(true);
          /* Post cannot use caches */
        con.setUseCaches(false);
          /* Set the post method. Default is GET*/
        con.setRequestMethod("POST");
          /* ������������ */
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        con.setRequestProperty("Cookie", SharePreferencesUtils.getSession(context));
          /*����StrictMode ����HTTPURLConnection����ʧ�ܣ���Ϊ�������������н�����������*/
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
          /* ����DataOutputStream��getOutputStream��Ĭ�ϵ���connect()*/
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; " +
                "name=\"file\";filename=\"" +
                fileNames + "\"" + end);
        ds.writeBytes(end);
          /* ȡ���ļ���FileInputStream */
        FileInputStream fStream = new FileInputStream(uploadFilea);
          /* ����ÿ��д��8192bytes */
        int bufferSize = 8192;
        byte[] buffer = new byte[bufferSize];   //8k
        int length = -1;
          /* ���ļ���ȡ������������ */
        while ((length = fStream.read(buffer)) != -1) {
            /* ������д��DataOutputStream�� */
            ds.write(buffer, 0, length);
        }
        ds.writeBytes(end);
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* �ر�����д��Ķ����Զ�����Http����*/
        fStream.close();
          /* �ر�DataOutputStream */
        ds.close();
          /* �ӷ��ص���������ȡ��Ӧ��Ϣ */
        InputStream is = con.getInputStream();  //input from the connection ��ʽ����HTTP����
        int ch;
        StringBuffer b = new StringBuffer();
        while ((ch = is.read()) != -1) {
            b.append((char) ch);
        }
          /* ��ʾ��ҳ��Ӧ���� */

//        } catch (Exception e) {
//            /* ��ʾ�쳣��Ϣ */
//            Log.e("tag",e+"");
//            return null;
//        }
        return b.toString();
    }

    public static StringBuffer uploadFile(String urls, Context context, String uploadFilea, String fileNames, String investor_type, String real_name, String position, String company, String investor_qualification) throws Exception {
        StringBuffer b = null;
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
//        try
//        {
        URL url = new URL(urls);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
        con.setDoOutput(true);
          /* Read from the connection. Default is true.*/
        con.setDoInput(true);
          /* Post cannot use caches */
        con.setUseCaches(false);
          /* Set the post method. Default is GET*/
        con.setRequestMethod("POST");
          /* ������������ */
        // ���ݵ�����
        String data = "investor_type=" + URLEncoder.encode(investor_type, "GBK")
                + "&real_name=" + URLEncoder.encode(real_name, "GBK") + "&position="
                + URLEncoder.encode(position, "GBK") + "&company=" + company
                + "&investor_qualification=" + URLEncoder.encode(investor_qualification, "GBK");
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        con.setRequestProperty("Cookie", SharePreferencesUtils.getSession(context));
//        con.setRequestProperty("Content-Length",
//                String.valueOf(data.getBytes().length));
//          /*����StrictMode ����HTTPURLConnection����ʧ�ܣ���Ϊ�������������н�����������*/
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
          /* ����DataOutputStream��getOutputStream��Ĭ�ϵ���connect()*/
        byte[] bypes = data.toString().getBytes();
        con.getOutputStream().write(bypes);// �������
        InputStream inStream = con.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] bufferS = new byte[1024];
        int len = 0;
        while ((len = inStream.read(bufferS)) != -1) {
            outStream.write(bufferS, 0, len);
        }
        byte[] datas = outStream.toByteArray();//��ҳ�Ķ���������
        Log.e("tags", new String(datas, "GBK"));
        outStream.close();
        inStream.close();
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
        ds.writeBytes(twoHyphens + boundary + end);
        ds.writeBytes("Content-Disposition: form-data; " +
                "name=\"file\";filename=\"" +
                fileNames + "\"" + end);
        ds.writeBytes(end);
          /* ȡ���ļ���FileInputStream */
        FileInputStream fStream = new FileInputStream(uploadFilea);
          /* ����ÿ��д��8192bytes */
        int bufferSize = 8192;
        byte[] buffer = new byte[bufferSize];   //8k
        int length = -1;
          /* ���ļ���ȡ������������ */
        while ((length = fStream.read(buffer)) != -1) {
            /* ������д��DataOutputStream�� */
            ds.write(buffer, 0, length);
        }
        ds.writeBytes(end);
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* �ر�����д��Ķ����Զ�����Http����*/
        fStream.close();
          /* �ر�DataOutputStream */
        ds.close();
          /* �ӷ��ص���������ȡ��Ӧ��Ϣ */
        InputStream is = con.getInputStream();  //input from the connection ��ʽ����HTTP����
        int ch;
        b = new StringBuffer();
        while ((ch = is.read()) != -1) {
            b.append((char) ch);
        }
          /* ��ʾ��ҳ��Ӧ���� */

//        } catch (Exception e) {
//            /* ��ʾ�쳣��Ϣ */
//            Log.e("tag",e+"");
//            return null;
//        }
        return b;
    }

    public static void getImage(String image_uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(5,5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }

    public static void getRoundImage(String image_uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(5, 5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }

    public static void getUserImage(String image_uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(5,5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }

    public static void getAnimImage(String image_uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(5,5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }

    public static void getCacheImage(String image_uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .bitmapConfig()
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//                .displayer(new RoundedBitmapDisplayer(5,5))
                .build();
//        Log.e("tupian",ImageLoader.getInstance().getDiskCache().getDirectory().getPath());
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }

    public static void getAuthImage(Activity activity, String image_uri, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity)
                //如果图片尺寸大于了这个参数，那么就会这按照这个参数对图片大小进行限制并缓存
                .memoryCacheExtraOptions(ScreenUtils.getScreenWidth(activity) - DisplayUtils.convertDipOrPx(activity, 24), 340) // default=device screen dimensions
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(config);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .displayer(new RoundedBitmapDisplayer(5, 5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }


    public static void getCirlceImage(Context activity, String image_uri, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity)
                //如果图片尺寸大于了这个参数，那么就会这按照这个参数对图片大小进行限制并缓存
                .memoryCacheExtraOptions(480, 800) // default=device screen dimensions
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(config);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .displayer(new RoundedBitmapDisplayer(5, 5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }


    public static void getPublishImage(Context activity, String image_uri, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity)
                //如果图片尺寸大于了这个参数，那么就会这按照这个参数对图片大小进行限制并缓存
//                .memoryCacheExtraOptions(200, 200) // default=device screen dimensions
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(config);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .displayer(new RoundedBitmapDisplayer(5, 5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
    }


    public static void getSelectImage(Context context, String image_uri, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                //如果图片尺寸大于了这个参数，那么就会这按照这个参数对图片大小进行限制并缓存
//                .memoryCacheExtraOptions(200, 200) // default=device screen dimensions
                .diskCacheExtraOptions(260, 260, null)
//                .diskCache(new UnlimitedDiscCache(cacheDir)) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(config);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .displayer(new RoundedBitmapDisplayer(5, 5))
                .build();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options);
        ImageLoader.getInstance().clearDiskCache();
    }

    public static void getSelectImage(String image_uri, ImageView imageView) {
        ImageSize mImageSize = new ImageSize(100, 100);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(false).resetViewBeforeLoading(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(5,5))
                .build();
        ImageLoader.getInstance().displayImage(image_uri, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
//        imageView.setImageBitmap(ImageLoader.getInstance().loadImageSync(image_uri, mImageSize, options));
//        ImageLoader.getInstance().clearDiskCache();
    }
}
