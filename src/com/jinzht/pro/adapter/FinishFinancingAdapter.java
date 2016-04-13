package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.model.FinishFinacingBean;
import com.jinzht.pro.utils.GlideUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/7/16
 * @time 10:41
 */

public class FinishFinancingAdapter extends BaseAdapter {

    Context context;

    ViewHolder viewHolder;
    List<FinishFinacingBean.DataEntity> finish_list;

    public FinishFinancingAdapter(Context context, List<FinishFinacingBean.DataEntity> finish_list) {
        this.context = context;
        this.finish_list = finish_list;
    }

    @Override
    public int getCount() {
        return finish_list.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finish_financing, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        DecimalFormat df = new DecimalFormat("0.00");
//        UpLoadImageUtils.getRoundImage(finish_list.get(i).getImg(), viewHolder.company_icon);
        GlideUtils.loadImg(context, finish_list.get(i).getImg(), viewHolder.company_icon);
        viewHolder.textViewList.get(0).setText(finish_list.get(i).getAbbrevcompany());
        if (finish_list.get(i).getInvest() == 0) {
            viewHolder.textViewList.get(1).setText(context.getResources().getString(R.string.invest_progress) + "0%");
        } else {
            viewHolder.textViewList.get(1).setText(context.getResources().getString(R.string.invest_progress) + (df.format((finish_list.get(i).getInvest() / finish_list.get(i).getPlanfinance()) * 100)) + "%");
        }
        viewHolder.textViewList.get(2).setText(context.getResources().getString(R.string.company_filed) + finish_list.get(i).getTag());/**֧������*/
        viewHolder.textViewList.get(3).setText(context.getResources().getString(R.string.has_invest) + df.format(finish_list.get(i).getInvest()) + context.getResources().getString(R.string.wan));
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.company_icon)
        ImageView company_icon;
        @Bind({R.id.company_name, R.id.tv_prgress, R.id.tv_support, R.id.tv_have_money})
        List<TextView> textViewList;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
