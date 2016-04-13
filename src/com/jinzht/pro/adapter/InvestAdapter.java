package com.jinzht.pro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Bind;


import com.jinzht.pro.R;
import com.jinzht.pro.model.InvestorBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/5/21
 * @time 11:03
 */

public class InvestAdapter extends BaseAdapter {

    ViewHolder viewHolder;
    Context context;
    List<InvestorBean> investorBeanList;

    public InvestAdapter(Context context, List<InvestorBean> investorBeanList) {
        this.context = context;
        this.investorBeanList = investorBeanList;
    }

    @Override
    public int getCount() {
        return investorBeanList.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_core_team, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.textViewList.get(0).setText(investorBeanList.get(i).getName());
        viewHolder.textViewList.get(0).setText(investorBeanList.get(i).getCompany()+investorBeanList.get(i).getTitle());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.sale_loadfail)
//                .showImageOnFail(R.drawable.small_fail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(5))
                .build();
        ImageLoader.getInstance().displayImage(investorBeanList.get(i).getThinktank_img(), viewHolder.image, options);
        return view;
    }
    static class ViewHolder {
         @Bind({R.id.title,R.id.position})
        List<TextView> textViewList;
         @Bind(R.id.image)
        ImageView image;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
