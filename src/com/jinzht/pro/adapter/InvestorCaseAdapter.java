package com.jinzht.pro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.model.OriInvestorDetailBean;
import com.jinzht.pro.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public class InvestorCaseAdapter extends RecyclerView.Adapter<InvestorCaseAdapter.InvestorCaseViewHolder> {
    private List<OriInvestorDetailBean.DataEntity.InvestcaseEntity> list;
    private Context context;
    private LayoutInflater mInflater;
    public InvestorCaseAdapter(List<OriInvestorDetailBean.DataEntity.InvestcaseEntity> list,Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public InvestorCaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_invest_case,
                viewGroup, false);
        InvestorCaseViewHolder viewHolder = new InvestorCaseViewHolder(view);
        viewHolder.tv_case = (TextView) view.findViewById(R.id.tv_case);
        viewHolder.iv_case = (ImageView) view.findViewById(R.id.iv_case);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InvestorCaseViewHolder investorCaseViewHolder, int i) {
        GlideUtils.loadImg(context,list.get(i).getLogo(),investorCaseViewHolder.iv_case);
        investorCaseViewHolder.tv_case.setText(list.get(i).getCompany());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InvestorCaseViewHolder extends RecyclerView.ViewHolder {
        public InvestorCaseViewHolder(View itemView) {
            super(itemView);
        }
        private TextView tv_case;
        private ImageView iv_case;

    }
}
