package com.jinzht.pro.wheelview;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/3/15.
 */
public class AppJsonFileReader {
    public String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName),"gbk"));
            String line ;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("tt",stringBuilder.toString());
        return stringBuilder.toString();
    }
    private static ArrayList<String> setCoutry(String str){
        ArrayList<String> arrayList = new ArrayList<String>();

        return arrayList;
    }
}
