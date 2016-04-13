package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CircleBean;
import com.jinzht.pro.model.CircleSomeOneLikeDeailBean;
import com.jinzht.pro.pagelistview.PagingBaseAdapter;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public class CircleLikeDetailAdapter extends PagingBaseAdapter<CircleSomeOneLikeDeailBean.DataEntity> {
    private Context context;
    private LayoutInflater inflater;
    public CircleLikeDetailAdapter(Context context, List<CircleSomeOneLikeDeailBean.DataEntity> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items= list;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        private TextView tv_name,tv_position;
        private ImageView iv_image;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_circle_like, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_love);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(items.get(i).getName());
        UpLoadImageUtils.getRoundImage(items.get(i).getPhoto(),viewHolder.iv_image);
        return convertView;
    }
}
