package com.jinzht.pro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Bind;


import com.jinzht.pro.R;
import com.jinzht.pro.model.RoadShowBean;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/16
 * @time 17:35
 */

public class MyRecyleView extends RecyclerView.Adapter<MyRecyleView.MyViewHolder> implements View.OnClickListener{
    private Context mCtx;
    private List<RoadShowBean> list;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public MyRecyleView(Context mCtx, List<RoadShowBean> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView road_image;
        public TextView company_name,road_time,stage;
        public MyViewHolder(View view) {
            super(view);
            company_name = (TextView) view.findViewById(R.id.company_name);
            road_time = (TextView) view.findViewById(R.id.road_time);
            stage = (TextView) view.findViewById(R.id.stage);
            road_image = (ImageView) view.findViewById(R.id.road_image);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_homepage, viewGroup, false);
        MyViewHolder simpleViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
//        simpleViewHolder.setIsRecyclable(true);
        return simpleViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.itemView.setTag(list.get(position));
        UpLoadImageUtils.getImage(list.get(position).getCompany_img(), myViewHolder.road_image);
//        double positionHeight = getPositionRatio(position);
//        viewHolder.road_image.setHeightRatio(positionHeight);
        myViewHolder.company_name.setText(list.get(position).getCompany_name());
        myViewHolder.road_time.setText(list.get(position).getRoadshow_date());
        myViewHolder.stage.setText(list.get(position).getStage());
        myViewHolder.stage.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(+list.get(position).getColor())));
    }

//    @Override
//    public void onViewDetachedFromWindow(MyRecyleView.MyViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        holder.itemView.clearAnimation();
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(view,(RoadShowBean)view.getTag());
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , RoadShowBean data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
