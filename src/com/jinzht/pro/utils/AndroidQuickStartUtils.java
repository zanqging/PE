package com.jinzht.pro.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2015/11/23.
 */
public class AndroidQuickStartUtils {
    public static void toTelephone(Context context,String tel){
        Uri uri = Uri.parse("tel:"+tel);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
