package com.jinzht.pro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.R;
import com.jinzht.pro.callback.ExpandReplyInterface;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/29
 * @time 15:14
 */

public class InteractsAdapter extends RecyclerView.Adapter<InteractsAdapter.SimpleViewHolder>{
    Context context;
    List<InteractBean.DataEntity> list;
    private ExpandReplyInterface expandReplyInterface;
    public void setOnReplyBtnOnclick( ExpandReplyInterface expandReplyInterface){
        this.expandReplyInterface = expandReplyInterface;
    }

    public InteractsAdapter(Context context, List<InteractBean.DataEntity> list) {
        this.context = context;
        this.list = list;
    }


    static class SimpleViewHolder extends RecyclerView.ViewHolder {
       @Bind(R.id.header_image)
       PolygonImageView header_image;
       @Bind({R.id.name,R.id.header_context}) List<TextView> header_list;
       @Bind(R.id.header_reply_btn) TextView header_reply_btn;
        public SimpleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
    @Override
    public InteractsAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_header_interact, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InteractsAdapter.SimpleViewHolder simpleViewHolder, int position) {
        UpLoadImageUtils.getUserImage(list.get(position).getPhoto(), simpleViewHolder.header_image);
        if (list.get(position).getAt_name()==null){
            simpleViewHolder.header_list.get(0).setText(list.get(position).getName());
        }else {
            simpleViewHolder.header_list.get(0).setText(list.get(position).getName()+"@"+list.get(position).getAt_name());
        }
        simpleViewHolder.header_list.get(1).setText(list.get(position).getContent());
        simpleViewHolder.itemView.setTag(list.get(position));
        simpleViewHolder.header_reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandReplyInterface!=null){
                    expandReplyInterface.HeaderReplyOnclick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
