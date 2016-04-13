package com.jinzht.pro.circleviewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.jinzht.pro.R;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;

	private int size;
	private List<String> ids;

	private boolean isInfiniteLoop;

	public ImageAdapter(Context context) {
		this.mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public ImageAdapter(Context context,List<String> ids) {

		if (ids != null) {
			this.size = ids.size();
		}
		isInfiniteLoop = false;
		mContext = context;
		this.ids = ids;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : ids.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}
	private int getPosition(int position) {
		return isInfiniteLoop ? position % size : position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	private static class ViewHolder {

		ImageView imageView;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.image_item, null);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView = (ImageView) convertView.findViewById(R.id.imgView);
		UpLoadImageUtils.getImage(ids.get(position%ids.size()), holder.imageView);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(mContext,DetailActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putInt("image_id", ids[position%ids.length]);
				// intent.putExtras(bundle);
				// mContext.startActivity(intent);
				Toast.makeText(mContext, "点击了第" + (position % ids.size() + 1) + "张图片",
						Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImageAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}
}
