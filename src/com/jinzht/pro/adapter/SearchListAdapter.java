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
import com.jinzht.pro.model.SearchBean;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/11
 * @time 16:38
 */

public class SearchListAdapter extends BaseAdapter {
    Context context;
    List<SearchBean.DataEntity> list;
    ViewHolder viewHolder;

    public SearchListAdapter(Context context, List<SearchBean.DataEntity> list) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.textViewList.get(0).setText(list.get(i).getCompany());
//        viewHolder.textViewList.get(1).setText(list.get(i).get());
        viewHolder.textViewList.get(1).setText(list.get(i).getProfile());
//        viewHolder.textViewList.get(4).setText(list.get(i).getStage());
        UpLoadImageUtils.getImage(list.get(i).getImg(),viewHolder.company_icon);
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.company_icon)
        ImageView company_icon;
         @Bind({R.id.company_name,R.id.project_summary}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
