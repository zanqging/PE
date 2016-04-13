package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.model.ReplyMessageBean;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.swipe.SwipeLayout;
import com.jinzht.pro.swipe.adapters.BaseSwipeAdapter;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/7
 * @time 13:47
 */

public class SwipeListAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private List<ReplyMessageBean.DataEntity> list;
    private SwipeOnClick swipeOnClick;
    public interface SwipeOnClick{
        public void onClicks(View view,int position);
    }
    public SwipeListAdapter(Context mContext,List<ReplyMessageBean.DataEntity> list,SwipeOnClick swipeOnClick) {
        this.mContext = mContext;
        this.list= list;
        this.swipeOnClick = swipeOnClick;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_reply_message, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
//        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//            @Override
//            public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//            }
//        });
//        swipeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                swipeOnClick.onClicks(view,position);
//            }
//        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeOnClick.onClicks(view,position);
            }
        });
        v.findViewById(R.id.reply_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeOnClick.onClicks(view,position);
            }
        });
        PolygonImageView user_iamge = (PolygonImageView) v.findViewById(R.id.user_iamge);
        UpLoadImageUtils.getImage(list.get(position).getPhoto(),user_iamge);
        TextView reply_context = (TextView) v.findViewById(R.id.reply_context);
        TextView reply_time = (TextView) v.findViewById(R.id.reply_time);
        TextView reply_name = (TextView) v.findViewById(R.id.reply_name);
        reply_context.setText(list.get(position).getContent());
        reply_name.setText(list.get(position).getName());
        reply_time.setText(list.get(position).getDate());
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
//        TextView t = (TextView)convertView.findViewById(R.id.position);
//        t.setText((position + 1) + ".");
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
    public long getItemId(int position) {
        return position;
    }
}
