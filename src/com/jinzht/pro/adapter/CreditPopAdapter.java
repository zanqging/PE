package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CreditPopAdapter extends BaseAdapter {

    Context context;

    ViewHolder viewHolder;
    List<String> list;

    public CreditPopAdapter(Context context, List<String> list) {
        this.context = context;
        this.list  = list;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pop_credit, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.tv_name.setText(list.get(i));
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.tv_name) TextView tv_name;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
