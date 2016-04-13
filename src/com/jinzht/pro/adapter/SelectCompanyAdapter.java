package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import butterknife.ButterKnife;
import butterknife.Bind;

import com.jinzht.pro.R;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/6/4
 * @time 9:23
 */

public class SelectCompanyAdapter extends BaseAdapter {

    Context context;
    public SelectCompanyCallBack selectCompanyCallBack;
    ViewHolder viewHolder;

    public SelectCompanyAdapter(SelectCompanyCallBack selectCompanyCallBack, Context context) {
        this.selectCompanyCallBack = selectCompanyCallBack;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
    public View getView(int position, View view, ViewGroup parent) {
        String[] items = context.getResources().getStringArray(R.array.childvalues2);
        if (view!=null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.radioButton.setText(items[position]);
        viewHolder.radioButton.setChecked(false);
        selectCompanyCallBack.changFocus2(viewHolder.radioButton,position);
        return view;
    }
    public interface SelectCompanyCallBack{
        public void changFocus2(RadioButton radioButton, int position);
    }
    static class ViewHolder {

        @Bind(R.id.select_item)
        RadioButton radioButton;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
