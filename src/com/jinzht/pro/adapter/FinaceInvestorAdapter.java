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
import com.jinzht.pro.model.ThinkTeamBean;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class FinaceInvestorAdapter extends BaseAdapter {
    Context context;

    ViewHolder viewHolder;
    List<InvestBean.DataEntity> list;

    public FinaceInvestorAdapter(Context context, List<InvestBean.DataEntity> list) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_think_task, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        GlideUtils.loadImg(context,list.get(i).getPhoto(), viewHolder.iv_company);
//        UpLoadImageUtils.getRoundImage(list.get(i).getPhoto(), viewHolder.iv_company);
        viewHolder.textViewList.get(0).setText("NAME:"+list.get(i).getName());
        viewHolder.textViewList.get(1).setText("COMPANY:"+list.get(i).getCompany());
        viewHolder.textViewList.get(2).setText("DUTIES:"+list.get(i).getPosition());
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.company_icon)
        ImageView iv_company;
        @Bind({R.id.name,R.id.company_name,R.id.project_summary}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
