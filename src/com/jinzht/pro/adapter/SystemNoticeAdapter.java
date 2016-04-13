package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.model.SystemNoticeBean;
import com.jinzht.pro.swipe.SwipeLayout;
import com.jinzht.pro.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/8/7
 * @time 23:58
 */
public class SystemNoticeAdapter extends BaseSwipeAdapter {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private List<SystemNoticeBean.DataEntity> list;
    private SwipeOnClick swipeOnClick;
    public interface SwipeOnClick{
        public void onClicks(View view,int position);
    }

    public SystemNoticeAdapter(Context mContext,List<SystemNoticeBean.DataEntity> list,SwipeOnClick swipeOnClick) {
        this.mContext = mContext;
        this.list  = list;
        this.swipeOnClick = swipeOnClick;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_system_notice, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(view,(SystemNoticeBean.DataEntity.ExtrasEntity)view.getTag(),position);
                }
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeOnClick.onClicks(view,position);
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView time = (TextView)convertView.findViewById(R.id.time);
        TextView content = (TextView)convertView.findViewById(R.id.content);
        title.setText(list.get(position).getTitle());
        time.setText(list.get(position).getCreate_datetime());
        content.setText(list.get(position).getContent());
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
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , SystemNoticeBean.DataEntity.ExtrasEntity data,int pos);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
