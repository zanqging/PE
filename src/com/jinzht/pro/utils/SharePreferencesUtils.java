package com.jinzht.pro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/4/17
 * @time 18:59
 */
public class SharePreferencesUtils {
    public static boolean isAuto = false;

    /**
     * 检查key对应的数据是否存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("userconfs", context.MODE_PRIVATE);
        return sp.contains(key);
    }
    public static void saveUser(Context context, String username, String uid) {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("username", username);
        editor.putString("uid", uid);
        //提交当前数据
        editor.commit();
    }
    public static void saveSession(Context context,String session){
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("session", session);
        //提交当前数据
        editor.commit();
    }
    public static void saveToken(Context context,String token){
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("token", token);
        //提交当前数据
        editor.commit();
    }
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("token","");
    }
    public static String getSession(Context context) {
        SharedPreferences sharedPreferences=null;
        try {
             sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
            return sharedPreferences.getString("session","");
        }catch (NullPointerException e){
            return "";
        }
        // 使用getString方法获得value，注意第2个参数是value的默认值
    }
    public static void saveInformation(Context context,String telephone,String passwd){
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("telephone", telephone);
        editor.putString("passwd", passwd);
        //提交当前数据
        editor.commit();
    }
    public static String getTelephone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("telephone","");
    }
    public static String getPassWd(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("passwd","");
    }
    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("username","");
    }
    public static String getUid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("uid", "");
    }
    public static String getFid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("fid", "");
    }
    /**
     * 保存是否登录参数
     */
    public static void setIsLogin(Context context, boolean isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("islogin", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static void setIsFirst(Context context, boolean isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("isfirst", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static boolean getIsfirst(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("isfirst", false);
    }
    /**
     * 获取是否登录参数
     */
    public static boolean getIsLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("islogin", false);
    }

    public static void setPerfectInformation(Context context, boolean isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("isPerfect", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static boolean getPerfectInformation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("isPerfect", false);
    }
    public static void setIsToastMenu(Context context, boolean isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("IsToastMenu", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static boolean getIsToastMenu(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("IsToastMenu", false);
    }
    public static void setIsToastMenuOther(Context context, boolean isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("IsToastMenuOther", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static void setCircleInformation(Context context, int id,String image,String city,String position_type,String name) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putInt("id", id);
        editor.putString("image", image);
        editor.putString("city",city);
        editor.putString("position", position_type);
        editor.putString("name", name);
        //提交当前数据
        editor.commit();
    }
    public static String getCircleInformation(Context context,String param) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString(param, "");
    }
    public static int getCircleInformations(Context context,String param) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getInt(param, 0);
    }
    public static boolean getIsToastMenuOther(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("IsToastMenuOther", false);
    }
    public static void setIsBottomToast(Context context, boolean isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("IsBottomToast", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static boolean getIsBottomToast(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("IsBottomToast", false);
    }

    public static void setAuth(Context context, String isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("auth", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static String getAuth(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("auth", "");
    }

    public static void setCoustomTelephone(Context context, String isAuto) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("CoustomTelephone", isAuto);
        //提交当前数据
        editor.commit();
    }
    public static String getCoustomTelephot(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getString("CoustomTelephone", "");
    }

    public static boolean getLightModel(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("lightModel", false);
    }
    /**
     * 设置夜间模式
     */
    public static void setLightModel(Context context,boolean is_light) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("userconfs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("lightModel", is_light);
        //提交当前数据
        editor.commit();
    }

}
