package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.jinzht.pro.R;
import com.jinzht.pro.lookpictrue.PhotoView;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.ArrayList;
import java.util.List;

public class CircleGridViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private int arg;
	private List<String> lists;
	public CircleGridViewAdapter(Context context, int arg,List<String> list) {
		this.arg = arg;
		this.context = context;
		inflater = LayoutInflater.from(context);
		lists = list;
	}
	@Override
	public int getCount() {
//		if (arg > 9) {
//			return 9;
//		} else {
//			return list.size();
//		}
		return lists.size();
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

		public  ImageView mImageView;

	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.img_gridview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.iv_gridview_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (lists.get(arg0).contains("http")){
			UpLoadImageUtils.getImage(lists.get(arg0),viewHolder.mImageView);
//			GlideUtils.loadImg(context,lists.get(arg0),viewHolder.mImageView);
		}else {
//			UpLoadImageUtils.getImage("file://"+lists.get(arg0),viewHolder.mImageView);
			GlideUtils.loadImg(context, lists.get(arg0), viewHolder.mImageView);
		}
		convertView.setTag(R.id.tag_second, lists.get(arg0));
		return convertView;
	}

}
