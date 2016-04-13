package com.jinzht.pro.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Bind;


import com.jinzht.pro.R;
import com.jinzht.pro.model.NewsBean;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/17
 * @time 16:29
 */

public class ThreeBoardAdapter extends BaseAdapter {
    Context context;

    ViewHolder viewHolder;
    private List<NewsBean.DataEntity> list_news;
    public ThreeBoardAdapter(Context context,List<NewsBean.DataEntity> list_news) {
        this.context = context;
        this.list_news = list_news;
    }

    @Override
    public int getCount() {
        return list_news.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_three_board, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.textViewList.get(0).setText(list_news.get(i).getTitle());
        UiHelp.textBold(viewHolder.textViewList.get(0));
        viewHolder.textViewList.get(1).setText(list_news.get(i).getSrc());
        viewHolder.textViewList.get(2).setText(list_news.get(i).getContent());
        viewHolder.textViewList.get(3).setText(list_news.get(i).getShare()+"");
        viewHolder.textViewList.get(4).setText(list_news.get(i).getRead()+"");
        viewHolder.create_time.setText(list_news.get(i).getCreate_datetime());
        UpLoadImageUtils.getRoundImage(list_news.get(i).getImg(), viewHolder.company_icon);
        Log.e("sss",list_news.get(i).getContent());
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.company_icon) ImageView company_icon;
        @Bind({R.id.title,R.id.resource,R.id.context,R.id.tv_love,R.id.tv_collect}) List<TextView> textViewList;
        @Bind(R.id.create_time) TextView create_time;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
