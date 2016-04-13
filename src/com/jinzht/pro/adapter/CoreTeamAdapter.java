package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.model.CoreTeamBean;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/16
 * @time 15:20
 */

public class CoreTeamAdapter extends BaseAdapter {

    Context context;

    ViewHolder viewHolder;
    List<CoreTeamBean.DataEntity> list;

    public CoreTeamAdapter(Context context, List<CoreTeamBean.DataEntity> list) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coreteam, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.textViewList.get(0).setText(list.get(i).getPosition());
        UiHelp.textBold(viewHolder.textViewList.get(0));
        viewHolder.textViewList.get(1).setText(list.get(i).getName());
        viewHolder.textViewList.get(2).setText(list.get(i).getProfile());
        UpLoadImageUtils.getRoundImage(list.get(i).getPhoto(), viewHolder.company_icon);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.company_icon)
        ImageView company_icon;
        @Bind({R.id.name, R.id.company_name, R.id.project_summary})
        List<TextView> textViewList;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
