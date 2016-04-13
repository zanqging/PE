package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CircleCommentDetailBean;
import com.jinzht.pro.model.CircleSomeOneLikeDeailBean;
import com.jinzht.pro.pagelistview.PagingBaseAdapter;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class CircleCommentDetailAdapter extends PagingBaseAdapter<CircleCommentDetailBean.DataEntity> {

    private Context context;
    private LayoutInflater inflater;
    public CircleCommentDetailAdapter(Context context, List<CircleCommentDetailBean.DataEntity> list) {
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
        private TextView tv_name,tv_content;
        private ImageView iv_image;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_circle_detail_comment, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(items.get(i).getName());
        UpLoadImageUtils.getRoundImage(items.get(i).getPhoto(), viewHolder.iv_image);
        return convertView;
    }
}
