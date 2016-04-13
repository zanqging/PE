package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CircleBean;

import java.util.List;

public class CircleHeadCommentGridViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<CircleBean.DataEntity.LikeEntity> lover_lists;
	public CircleHeadCommentGridViewAdapter(Context context,List<CircleBean.DataEntity.LikeEntity> lover_list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		lover_lists = lover_list;
	}

	@Override
	public int getCount() {

		return lover_lists.size();
	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	public class ViewHolder {

		private ImageView mImageView;
		private TextView tv_love;

	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.circle_gridview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.iv_head_img);
			viewHolder.tv_love = (TextView) convertView.findViewById(R.id.tv_love);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (arg0==0){
			viewHolder.mImageView.setVisibility(View.VISIBLE);
		}else {
			viewHolder.mImageView.setVisibility(View.GONE);
		}
		viewHolder.tv_love.setText(lover_lists.get(arg0).getName()+" ");
		return convertView;
	}
}
