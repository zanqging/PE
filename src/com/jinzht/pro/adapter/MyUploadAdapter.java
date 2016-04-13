package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.R;
import com.jinzht.pro.model.MyUploadBean;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MyUploadAdapter extends BaseAdapter {
    Context context;
    List<MyUploadBean.DataEntity> list;
    ViewHolder viewHolder;

    public MyUploadAdapter(Context context, List<MyUploadBean.DataEntity> list) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wait_financing, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.textViewList.get(2).setVisibility(View.GONE);
        GlideUtils.loadImg(context,list.get(i).getImg(), viewHolder.iv_company);
        viewHolder.textViewList.get(0).setText(list.get(i).getAbbrevcompany());
        viewHolder.textViewList.get(1).setText(list.get(i).getCompany());
        viewHolder.textViewList.get(3).setText(context.getResources().getString(R.string.upload_time)+list.get(i).getDate());
//        viewHolder.textViewList.get(2).setText(list.get(i).getAddr());
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.iv_company) ImageView iv_company;
        @Bind({R.id.tv_company,R.id.tv_company_all,R.id.tv_addr,R.id.tv_time}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
