package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.model.HomePageBean;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/10/30
 * @time 22:28
 */
public class HomePageAdapter extends BaseAdapter {

    Context context;

    ViewHolder viewHolder;
    List<HomePageBean.DataEntity.ProjectEntity> list;

    public HomePageAdapter(Context context, List<HomePageBean.DataEntity.ProjectEntity> list) {
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
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_good, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        UpLoadImageUtils.getImage(list.get(i).getImg(), viewHolder.result_iamge);
        viewHolder.textViewList.get(0).setText(list.get(i).getAbbrevcompany());
//        viewHolder.textViewList.get(1).setText(list.get(i).getProcess().getKey()+":");
        viewHolder.textViewList.get(1).setText(context.getString(R.string.has_money) + list.get(i).getInvest() + context.getResources().getString(R.string.wan));
        viewHolder.textViewList.get(2).setText(context.getResources().getString(R.string.invest_time) + list.get(i).getDate());
        viewHolder.tv_invest_status.setText(context.getString(R.string.below_industry) + list.get(i).getTag());
        return view;
    }

    static class ViewHolder {
        @Bind({R.id.tv_company, R.id.tv_money, R.id.tv_time})
        List<TextView> textViewList;
        @Bind(R.id.tv_invest_status)
        TextView tv_invest_status;
        @Bind(R.id.result_iamge)
        ImageView result_iamge;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
