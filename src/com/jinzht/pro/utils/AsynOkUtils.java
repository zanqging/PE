package com.jinzht.pro.utils;

import android.content.Context;
import android.util.Log;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.InvestFinacingDetailsActivity;
import com.jinzht.pro.application.MyApplication;
import com.jinzht.pro.supertoasts.SuperToast;
import com.squareup.okhttp.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/10/30.
 */
public class AsynOkUtils {
//    public static void doGet(String urls,Context context ,Callback responseCallback)  throws Exception {
//        final Request request = new Request.Builder()
//                .addHeader("Cookie", SharePreferencesUtils.getSession(context))
//                .url(urls)
//                .build();
//        MyApplication.getInstance().okHttpClient.newCall(request).enqueue(responseCallback);
//    }

    public static String doGet(String urls,Context context)  throws IOException {
        String body="";
        final Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(urls)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient/*.setCookieHandler().*/.newCall(request).execute();
        Log.e("ss",request.headers().value(0)+"\ncode"+response.code()+"\n"+response+"header\n"+response);
        if (response.isSuccessful()){
            body = response.body().string();
        }else {
//            if (response.code()==500){
//                SuperToastUtils.showSuperToast(context,1, R.string.app_name);
//            }else if (response.code()==404){
//                SuperToastUtils.showSuperToast(context,1, R.string.app_name);
//            }
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }


    public static String doLoginPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,
                                     String str9,String str10,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
                .add(str9,str10)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try {
                if (!StringUtils.isBlank(response.header("Set-Cookie"))){
                    SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").split(";")[0]);
                }
            }catch (NullPointerException e){

            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doNewLoginPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try{
                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
                    UiHelp.ToastMessageShort(context,"sessionbuzunzai");
                }else {
                    Log.e("session",response.header("Set-Cookie").toString());
                    SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doImprovePost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,
                                       String str9,String str10,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
                .add(str9,str10)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", SharePreferencesUtils.getSession(context))
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doPushPost(String str1,String str2,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doPushPost(String str1,String str2,String str3,String str4,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3,str4)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }

    public static String doPushPost(String str1,String str2,String str3,String str4,String str5,String str6,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3,str4)
                .add(str5,str6)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
//    public static  String doAuthPost(String str1,String str2,String str3,String str4,
//                                     String str5,String str6,String str7,String str8,String paths,String url,Context activity)throws IOException{
//        String body = "";
//        //����OkHttpClientʵ��
//        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
//        //����map�����в�����builder
//        //����paths������ͼƬ����·����builder����Լ��key�硰upload����Ϊ��̨���ܶ���ͼƬ��key
//        builder.addFormDataPart("file","file", RequestBody.create(MultipartBuilder.FORM, new File(paths)));
//        builder.addFormDataPart(str1,str2);
//        builder.addFormDataPart(str3,str4);
//        builder.addFormDataPart(str5,str6);
//        builder.addFormDataPart(str7,str8);
//        //����������
//        RequestBody requestBody = builder.
//                build();
//        //��������
//        Request request = new Request.Builder()
//                .url(url)//��ַ
//                .post(requestBody)//���������
//                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
//                .build();
//        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
//        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//        body = response.body().string();
//        return body;
//    }


    public static  String doAuthPost(String str1,String str2,String str3,String str4,
                                    String str5,String str6,String str7,String str8, String paths,String url,Context activity)throws IOException{
        String body = "";
        //����OkHttpClientʵ��
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //����map�����в�����builder
        //����paths������ͼƬ����·����builder����Լ��key�硰upload����Ϊ��̨���ܶ���ͼƬ��key
        builder.addFormDataPart("idpic","idpic", RequestBody.create(MultipartBuilder.FORM, new File(paths)));
        builder.addFormDataPart(str1,str2);
        builder.addFormDataPart(str3,str4);
        builder.addFormDataPart(str5,str6);
        builder.addFormDataPart(str7,str8);
        //����������
        RequestBody requestBody = builder.
                build();
        //��������
        Request request = new Request.Builder()
                .url(url)//��ַ
                .post(requestBody)//���������
                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        body = response.body().string();
        return body;
    }

    public static  String doPersonAuthPost(String str1,String str2,String str3,String str4,
                                    String identity,String img,String url,Context activity)throws IOException{
        String body = "";
        //����OkHttpClientʵ��
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //����map�����в�����builder
        //����paths������ͼƬ����·����builder����Լ��key�硰upload����Ϊ��̨���ܶ���ͼƬ��key
        builder.addFormDataPart("idpic","idpic", RequestBody.create(MultipartBuilder.FORM, new File(identity)));
        builder.addFormDataPart("img","img", RequestBody.create(MultipartBuilder.FORM, new File(img)));
        builder.addFormDataPart(str1,str2);
        builder.addFormDataPart(str3,str4);
        //����������
        RequestBody requestBody = builder.
                build();
        //��������
        Request request = new Request.Builder()
                .url(url)//��ַ
                .post(requestBody)//���������
                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        body = response.body().string();
        return body;
    }


    public static  String dowechatLogin(String str1,String str2,String str3,String str4,
                                     String str5,String str6,String str7,String str8,String str9,String str10,String str11,String str12,
                                     String paths,String url,Context activity)throws IOException{
        String body = "";
        //����OkHttpClientʵ��
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //����map�����в�����builder
        //����paths������ͼƬ����·����builder����Լ��key�硰upload����Ϊ��̨���ܶ���ͼƬ��key
        builder.addFormDataPart("file","file", RequestBody.create(MultipartBuilder.FORM, new File(paths)));
        builder.addFormDataPart(str1,str2);
        builder.addFormDataPart(str3,str4);
        builder.addFormDataPart(str5,str6);
        builder.addFormDataPart(str7,str8);
        builder.addFormDataPart(str9,str10);
        builder.addFormDataPart(str11,str12);
        //����������
        RequestBody requestBody = builder.
                build();
        //��������
        Request request = new Request.Builder()
                .url(url)//��ַ
                .post(requestBody)//���������
                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try{
                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
                }else {
                    Log.e("session",response.header("Set-Cookie").toString());
                    SharePreferencesUtils.saveSession(activity, response.header("Set-Cookie").toString().split(";")[0]);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }



    public static  String dowechatLogin(String str1,String str2,String str3,String str4,
                                        String str5,String str6,String str7,String str8,String str9,String str10,
                                        String paths,String url,Context activity)throws IOException{
        String body = "";
        //����OkHttpClientʵ��
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //����map�����в�����builder
        //����paths������ͼƬ����·����builder����Լ��key�硰upload����Ϊ��̨���ܶ���ͼƬ��key
        builder.addFormDataPart("file","file", RequestBody.create(MultipartBuilder.FORM, new File(paths)));
        builder.addFormDataPart(str1,str2);
        builder.addFormDataPart(str3,str4);
        builder.addFormDataPart(str5,str6);
        builder.addFormDataPart(str7,str8);
        builder.addFormDataPart(str9,str10);
        //����������
        RequestBody requestBody = builder.
                build();
        //��������
        Request request = new Request.Builder()
                .url(url)//��ַ
                .post(requestBody)//���������
                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try{
                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
                }else {
                    Log.e("session",response.header("Set-Cookie").toString());
                    SharePreferencesUtils.saveSession(activity, response.header("Set-Cookie").toString().split(";")[0]);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }


    public static String doWechatLoginPost(String str1,String str2,String str3,String str4,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try{
                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
                    UiHelp.ToastMessageShort(context,"sessionbuzunzai");
                }else {
                    Log.e("session",response.header("Set-Cookie").toString());
                    SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }

}
