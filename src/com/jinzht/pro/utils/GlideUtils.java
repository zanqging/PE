package com.jinzht.pro.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.jinzht.pro.R;

/**
 * Created by Administrator on 2016/1/7.
 */
public class GlideUtils {
    public static void loadImg(Context context,String path,ImageView ivPhoto){
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.user_loading)
                .error(R.drawable.user_loadingfail)
                .into(ivPhoto);
    }
    public static void circleLoadImg(Context context,String path,ImageView ivPhoto){
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.user_loading)
                .error(R.drawable.user_loadingfail)
                .override(300,300)
                .into(ivPhoto);
    }
    public static void smaillLoadImg(Context context,String path,ImageView ivPhoto){
        Glide.with(context)
                .load(path).thumbnail(0.3f)
                .placeholder(R.drawable.user_loading)
                .error(R.drawable.user_loadingfail)
                .override(300,300)
                .into(ivPhoto);
    }
    public static void loadImg(Context context, Uri path, ImageView ivPhoto){
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.user_loading)
                .error(R.drawable.user_loadingfail)
                .into(ivPhoto);
    }
    public static void loadRoundImg(Context context,String path,ImageView ivPhoto){

    }

}
