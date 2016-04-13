package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Bind;

import com.jinzht.pro.R;
import com.jinzht.pro.model.KeyWordBean;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/27
 * @time 18:16
 */

public class SearchAdapter extends BaseAdapter {

    ViewHolder viewHolder;
    Context context;
    List<String> list;
    public SearchAdapter(Context context,List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view!=null){
        viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_word, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.key_word.setText(list.get(i));
        return view;
    }
    static class ViewHolder {
         @Bind(R.id.key_word) TextView key_word;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
