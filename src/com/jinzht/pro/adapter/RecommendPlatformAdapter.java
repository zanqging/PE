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
import com.jinzht.pro.model.WaitFinacingBean;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;
import org.json.JSONArray;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/16
 * @time 10:23
 */

public class RecommendPlatformAdapter extends BaseAdapter {

    Context context;

    ViewHolder viewHolder;
    List<WaitFinacingBean.DataEntity> recommendPlatBeanList;

    public RecommendPlatformAdapter(Context context, List<WaitFinacingBean.DataEntity> recommendPlatBeanList) {
        this.context = context;
        this.recommendPlatBeanList = recommendPlatBeanList;
    }

    @Override
    public int getCount() {
        return recommendPlatBeanList.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platform, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        String ss="";
        try {
            JSONArray jsonObject = new JSONArray(recommendPlatBeanList.get(i).getIndustry_type().toString());
            ss = jsonObject.get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.textViewList.get(0).setText(recommendPlatBeanList.get(i).getCompany_name());
        UiHelp.textBold(viewHolder.textViewList.get(0));
        viewHolder.textViewList.get(1).setText(recommendPlatBeanList.get(i).getProject_summary());
        viewHolder.textViewList.get(2).setText(recommendPlatBeanList.get(i).getReason());
        viewHolder.textViewList.get(3).setText(recommendPlatBeanList.get(i).getProvince()+recommendPlatBeanList.get(i).getCity()+"/"+ss);
        viewHolder.textViewList.get(4).setText(recommendPlatBeanList.get(i).getCollect_sum()+"");
        viewHolder.textViewList.get(5).setText(recommendPlatBeanList.get(i).getLike_sum()+"");
        viewHolder.textViewList.get(6).setText(recommendPlatBeanList.get(i).getVote_sum()+"");
        UpLoadImageUtils.getRoundImage(recommendPlatBeanList.get(i).getThumbnail(), viewHolder.company_icon);
        return view;
    }
    static class ViewHolder {
         @Bind(R.id.company_icon) ImageView company_icon;
         @Bind({R.id.company_name,R.id.project_summary,R.id.reson,R.id.pos_location,R.id.collection,R.id.love,R.id.vote_num}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
