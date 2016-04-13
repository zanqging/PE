package com.jinzht.pro.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.CoreTeamDetailActivity;
import com.jinzht.pro.activity.InvestorDetailActivity;
import com.jinzht.pro.model.InvestorBean;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/27
 * @time 16:42
 */

public class InvestorAdapter extends RecyclerView.Adapter<InvestorAdapter.SimpleViewHolder>  {
    private Context mContext;
    private  RecyclerView mRecyclerView;
    private List<InvestorBean> investorBeanList;
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView postion;
        public ImageView imageView;
        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            postion = (TextView) view.findViewById(R.id.position);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
    public InvestorAdapter(Context mContext, RecyclerView mRecyclerView,List<InvestorBean> investorBeanList) {
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;
        this.investorBeanList = investorBeanList;
    }

    @Override
    public InvestorAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_core_team, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int i) {
        final View itemView = holder.itemView;
        holder.title.setText(investorBeanList.get(i).getName());
        holder.postion.setText(investorBeanList.get(i).getCompany() + investorBeanList.get(i).getTitle());
        UpLoadImageUtils.getUserImage(investorBeanList.get(i).getThinktank_img(), holder.imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (investorBeanList==null||investorBeanList.size()==0){
                }else {
                    Intent intent = new Intent(mContext, InvestorDetailActivity.class);
                    intent.putExtra("id",investorBeanList.get(i).getId()+"");
                    intent.putExtra("title",investorBeanList.get(i).getCompany());
                    intent.putExtra("name",investorBeanList.get(i).getName());
                    intent.putExtra("img",investorBeanList.get(i).getThinktank_img());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return investorBeanList.size();
    }
}
