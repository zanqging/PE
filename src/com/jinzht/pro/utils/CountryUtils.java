package com.jinzht.pro.utils;

import android.app.Activity;
import com.jinzht.pro.wheelview.AppJsonFileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/8/17
 * @time 23:37
 */
public class CountryUtils {
    public static HashMap countryThread(Activity activity,String fileName) throws Exception{
        List <String> country_list = new ArrayList<>();
        List<String> ss = null;
        List<List<String>> city_list = new ArrayList<>();
        HashMap hashMap = new HashMap();
        AppJsonFileReader appJsonFileReader = new AppJsonFileReader();
        String countryJson =  appJsonFileReader.getJson(activity,fileName);
        JSONArray jsonArray = new JSONArray(countryJson);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            country_list.add(jsonObject.optString("province"));
            JSONArray jsonArray1 = jsonObject.getJSONArray("cities");
            ss= new ArrayList<>();
            for (int j = 0; j <jsonArray1.length(); j++) {
                ss.add(jsonArray1.optString(j));
            }
            city_list.add(i,ss);
        }
        hashMap.put("country_list",country_list);
        hashMap.put("city_list",city_list);
        return hashMap;
    }
}
