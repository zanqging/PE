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
import com.jinzht.pro.model.ThinkTaskBean;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public class ThinkTaskAdapter extends BaseAdapter {
    Context context;

    ViewHolder viewHolder;
    List<ThinkTaskBean.DataEntity> list;

    public ThinkTaskAdapter(Context context, List<ThinkTaskBean.DataEntity> list) {
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
        viewHolder.textViewList.get(0).setText(list.get(i).getName());
        UiHelp.textBold(viewHolder.textViewList.get(0));
        viewHolder.textViewList.get(1).setText(list.get(i).getCompany());
        viewHolder.textViewList.get(2).setText(list.get(i).getTitle());
        UpLoadImageUtils.getRoundImage(list.get(i).getThumbnail(),viewHolder.company_icon);
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.company_icon)
        ImageView company_icon;
        @Bind({R.id.name,R.id.company_name,R.id.project_summary}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
