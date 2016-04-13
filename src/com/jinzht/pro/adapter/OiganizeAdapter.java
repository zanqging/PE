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
import com.jinzht.pro.model.InvestBean;
import com.jinzht.pro.model.InvestorOriBean;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/11/15.
 */
public class OiganizeAdapter extends BaseAdapter {
    Context context;

    ViewHolder viewHolder;
    List<InvestorOriBean.DataEntity> list;

    public OiganizeAdapter(Context context, List<InvestorOriBean.DataEntity> list) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organ_investor, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        UpLoadImageUtils.getRoundImage(list.get(i).getLogo(), viewHolder.iv_company);
        viewHolder.textViewList.get(0).setText(list.get(i).getName());
        viewHolder.textViewList.get(1).setText(list.get(i).getAbbrevname());
        viewHolder.textViewList.get(2).setText(list.get(i).getAddr());
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.iv_investor)
        ImageView iv_company;
        @Bind({R.id.tv_investor,R.id.tv_intro,R.id.tv_addr}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
