package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CollectWaitFinaceBean;
import com.jinzht.pro.model.RecommendPlatBean;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 22:14
 */
public class CollectNormalAdapter extends BaseAdapter {

    Context context;
    List<CollectWaitFinaceBean.DataEntity> list;
    ViewHolder viewHolder;
    private int flag ;
    public CollectNormalAdapter(Context context, List<CollectWaitFinaceBean.DataEntity> list, int flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
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
//        UpLoadImageUtils.getRoundImage(list.get(i).getImg(), viewHolder.iv_company);
        GlideUtils.loadImg(context,list.get(i).getImg(), viewHolder.iv_company);
        viewHolder.textViewList.get(0).setText(list.get(i).getAbbrevcompany());
        if (flag==0){
            viewHolder.textViewList.get(1).setText(context.getResources().getString(R.string.road_time)+list.get(i).getStart());
        }else if (flag==1){
            viewHolder.textViewList.get(1).setText(context.getResources().getString(R.string.invest_time)+list.get(i).getStart());
        }else {
            viewHolder.textViewList.get(1).setText(context.getResources().getString(R.string.invest_time)+list.get(i).getStart());
        }
        viewHolder.textViewList.get(2).setVisibility(View.GONE);
        viewHolder.textViewList.get(3).setText(list.get(i).getCompany());
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.iv_company) ImageView iv_company;
         @Bind({R.id.tv_company,R.id.tv_time,R.id.tv_addr,R.id.tv_company_all}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
