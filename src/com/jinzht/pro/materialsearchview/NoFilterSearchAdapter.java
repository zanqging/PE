package com.jinzht.pro.materialsearchview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jinzht.pro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Catalan Bañuls
 */
public class NoFilterSearchAdapter extends BaseAdapter {

    private List<String> data;

//    private String[] typeAheadData;

    private Drawable suggestionIcon;

    LayoutInflater inflater;

    public NoFilterSearchAdapter(Context context,  List<String> datas) {
        inflater = LayoutInflater.from(context);
        data = datas;
    }

    public NoFilterSearchAdapter(Context context,  List<String> datas, Drawable suggestionIcon) {
        inflater = LayoutInflater.from(context);
        data = datas;
        this.suggestionIcon = suggestionIcon;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.suggest_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        String currentListData = (String) getItem(position);

        mViewHolder.textView.setText(currentListData);


        return convertView;
    }

    private class MyViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.suggestion_text);
            if (suggestionIcon != null) {
                imageView = (ImageView) convertView.findViewById(R.id.suggestion_icon);
                imageView.setImageDrawable(suggestionIcon);
            }
        }
    }
}