package com.jinzht.pro.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jinzht.pro.application.MyApplication;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/4/14
 * @time 23:44
 */
public class OkHttpUtils {

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

    public static URLConnection sendGetRequest(String url,
                                               Map<String, String> params, Map<String, String> headers)
            throws Exception {
        StringBuilder buf = new StringBuilder(url);
        Set<Map.Entry<String, String>> entrys = null;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty()) {
            buf.append("?");
            entrys = params.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                buf.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(buf.toString());
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.getResponseCode();
        return conn;
    }
    public static String doGet(String urls,Context context)  throws IOException{
        String body="";
        final Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(urls)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient/*.setCookieHandler().*/.newCall(request).execute();
        Log.i("sss'", response.request().toString());
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        body = response.body().string();
        Log.i("Vary: ", response.header("Set-Cookie") + "");
        return body;
    }
    public static String doLoginGet(String urls) throws IOException{
        String body="";
        final Request request = new Request.Builder()
                .url(urls)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient/*.setCookieHandler().*/.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        body = response.body().string();
        Log.e("Cookie: ", response.header("Set-Cookie") + "");
        return body;
    }

    public static String doPost(String str1,String str2,String str3,String str4,String url) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .build();
        Request request = new Request.Builder()
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

    public static  String doPhotoPost(List<String> paths,String url,String content,Context activity)throws IOException{

        String body = "";
        int count = 0;
//        RequestBody requestBody = new MultipartBuilder()
//                .type(MultipartBuilder.FORM)
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
//                        RequestBody.create(null, "Square Logo"))
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"image\""),
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                .build();
        //参数类型
          final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        //创建OkHttpClient实例
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //遍历map中所有参数到builder
//        for (String key : map.keySet()) {
//            builder.addFormDataPart(key, map.get(key));
//        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (paths!=null&&paths.size()!=0){
            for (String path : paths) {
                builder.addFormDataPart("file"+count, "file", RequestBody.create(MultipartBuilder.FORM, new File(path)));
                count++;
            }
        }
        builder.addFormDataPart("content",content);
        //构建请求体
        RequestBody requestBody = builder.
                build();
        //构建请求
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.body().string());
        body = response.body().string();
        return body;
    }

    public static String doLoginPost(String str1,String str2,String str3,String str4,String str5,String str6,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
//            Log.e("sss",response.header("Set-Cookie").toString()/*.split(";")[0]*/);
            try{
                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
                    UiHelp.ToastMessageShort(context,"sessionbuzunzai");
                }else {
                    SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
                }
            }catch (NullPointerException e){

            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doModifyPost(String str1,String str2,String str3,String str4,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
//            Log.e("sss",response.header("Set-Cookie").toString()/*.split(";")[0]*/);
//            SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }

    public static String doPushPost(String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
//            Log.e("sss",response.header("Set-Cookie").toString()/*.split(";")[0]*/);
//            SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doPushPost(String str1,String str2,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            Log.e("sss",response.header("Set-Cookie").toString()/*.split(";")[0]*/);

            SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doForgetPost(String str1,String str2,String str3,String str4,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
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
    public static String doForgetPost(String str1,String str2,String str3,String str4,String str5,String str6,String url,Context context) throws  IOException{
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
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

    public static String doPost(String str1,String str2,String str3,String str4,String str5,String str6,String url) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3,str4)
                .add(str5, str6)
                .build();
        Request request = new Request.Builder()
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

    public static String doSubmitPost(String str1,String str2,String str3,String str4,String str5,String str6,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .add(str5, str6)
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
    public static String doSubmitPost(String str1,String str2,String str3,String str4,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
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
    public static String doSessionPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Log.e("seession",SharePreferencesUtils.getSession(context));
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try {
                SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doLoginPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
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
                Log.e("jjjj",response.header("Set-Cookie").toString());
                SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }

    public static String doSessionPost(String str1,String str2,String str3,String str4,String str5,String str6,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .add(str3, str4)
                .add(str5,str6)
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
                SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String url) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
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
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doSubmitPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
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

    public static String doSubmitPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,String str10,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
                .add(str9,str10)
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
    public static String doSubmitPost(String str1,String str2,String str3,String str4,String str5,String str6,String str7,String str8,String str9,String str10,String str11,String str12,String url,Context context) throws IOException {
        String body = "";
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .add(str5,str6)
                .add(str7,str8)
                .add(str9,str10)
                .add(str11,str12)
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
    public static String doPost(String str1,String str2,String url) throws IOException{
        String body = "";
        MyApplication application = MyApplication.getInstance();
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .build();
        Request request = new Request.Builder()
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
    public static String doSessionPost(String str1,String str2,String url,Context context) throws IOException{
        String body = "";
        MyApplication application = MyApplication.getInstance();
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1, str2)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try{
//                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
//                    Log.e("ss","sss");
//                }else {
                    Log.e("header",response.header("Set-Cookie")+"");
                    SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
//                }
            }catch (NullPointerException e){
                Log.e("sjoije","session不存在");
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }

    public static String doSessionPost(String str1,String str2,String str3,String str4,String url,Context context) throws IOException{
        String body = "";
        MyApplication application = MyApplication.getInstance();
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
//                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
//                    Log.e("ss","sss");
//                }else {
                Log.e("header",response.header("Set-Cookie")+"");
                SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
//                }
            }catch (NullPointerException e){
                Log.e("sjoije","session不存在");
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doLoginPost(String str1,String str2,String str3,String str4,String url,Context context) throws IOException{
        String body = "";
        MyApplication application = MyApplication.getInstance();
        RequestBody formBody = new FormEncodingBuilder()
                .add(str1,str2)
                .add(str3, str4)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", SharePreferencesUtils.getSession(context))
                .url(url)
                .post(formBody)
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
            try{
                if (StringUtils.isBlank(response.header("Set-Cookie").toString())){
                    UiHelp.ToastMessageShort(context, "sessionbuzunzai");
                }else {
                    Log.e("header",response.header("Set-Cookie").toString().split(";")[0]);
                    SharePreferencesUtils.saveSession(context, response.header("Set-Cookie").toString().split(";")[0]);
                }
            }catch (NullPointerException e){
                Log.e("sjoije","sessionbuzunzai");
                e.printStackTrace();
            }
        }else {
            throw new IOException("Unexpected code " + response);
        }
        return body;
    }
    public static String doUploadPost(String file_name,String file_location,String url,Context context) throws IOException{
        String body = "";
        String end = "\r\n";
            // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("file", file_name,
                            RequestBody.create(MEDIA_TYPE_PNG, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/invest.jpg")))
//                    .addPart(
//                            Headers.of("Content-Disposition", "form-data; name=\"file\""),
//                            RequestBody.create(MEDIA_TYPE_PNG, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/invest.jpg")))
                    .build();

        Request request = new Request.Builder()
                    .addHeader("Cookie", SharePreferencesUtils.getSession(context))
//                    .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                    .url(url)
                    .post(requestBody)
                    .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            body = response.body().string();
        }else {
            throw new IOException("Unexpected code " + response);
        }
     return body;
    }

    public static  String doOnePhotoPost(String paths,String url,Context activity)throws IOException{

        String body = "";
        //创建OkHttpClient实例
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //遍历map中所有参数到builder
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        builder.addFormDataPart("file","file", RequestBody.create(MultipartBuilder.FORM, new File(paths)));
        //构建请求体
        RequestBody requestBody = builder.
                build();
        //构建请求
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .addHeader("Cookie", SharePreferencesUtils.getSession(activity))
                .build();
        Response response =  MyApplication.getInstance().okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        body = response.body().string();
        return body;
    }

}
