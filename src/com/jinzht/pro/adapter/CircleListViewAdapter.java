package com.jinzht.pro.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.CirclePersonActivity;
import com.jinzht.pro.activity.PersonActivity;
import com.jinzht.pro.model.CircleBean;
import com.jinzht.pro.multiactiontextview.InputObject;
import com.jinzht.pro.multiactiontextview.MultiActionTextView;
import com.jinzht.pro.multiactiontextview.MultiActionTextviewClickListener;
import com.jinzht.pro.utils.StringUtils;

import java.util.List;

public class CircleListViewAdapter extends BaseAdapter {

	private OnBlueTextClick mOnItemClickListener = null;
	private static final String TAG = "CommentListViewAdapter";
	private LayoutInflater inflater;
	private Context context;
	private String text;
	private int number;
	private String str;
	private int up_ids;
	private final int NAME_CLICKED = 1;
	private final int ACTION_CLICKED = 2;
	private final int CONTENT_CLICKED = 3;
	private List<CircleBean.DataEntity.CommentEntity> lists;
	public CircleListViewAdapter( OnBlueTextClick mOnItemClickListener,Context context,List<CircleBean.DataEntity.CommentEntity> list,int up_id) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		lists = list;
		up_ids = up_id;
		this.mOnItemClickListener=mOnItemClickListener;
	}
	public void addComment(int pos,int pos_second, List<CircleBean.DataEntity.CommentEntity> newItems) {
		this.lists.addAll(pos_second, newItems);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
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
		private TextView tv_name;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.item_circle_comment, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!StringUtils.isEmpty( lists.get(arg0).getAt_name())){
//			viewHolder.tv_name.setText(lists.get(arg0).getName()+lists.get(arg0).getAt_label()+lists.get(arg0).getAt_name()+lists.get(arg0).getLabel_suffix());
			multiActionTextViewWithoutLinkFewClickable(lists.get(arg0).getName(),
					context.getResources().getString(R.string.reply),lists.get(arg0).getAt_name(),lists.get(arg0).getContent(),viewHolder.tv_name,arg0);
		} else {
			multiActionTextViewWithoutLinkFewClickable(lists.get(arg0).getName(),
					"","",lists.get(arg0).getContent(),viewHolder.tv_name,arg0);
		}
		return convertView;
	}
	private void multiActionTextViewWithoutLinkFewClickable(String name,String action,String contentName,String reply_content,TextView tv,int arg) {
		SpannableStringBuilder reply_builder = new SpannableStringBuilder();
		reply_builder.append(reply_content);
		SpannableStringBuilder name_builder = new SpannableStringBuilder();
		name_builder.append(name);
		SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
		stringBuilder.append(name);
		stringBuilder.append("");
		stringBuilder.append(action);
		stringBuilder.append("");
		stringBuilder.append(contentName);
		stringBuilder.append(":");
		stringBuilder.append(reply_content);
		int startSpan = 0;
		int endSpan = name.length();
		Log.d(TAG, " start : endspan::" + startSpan + " : " + endSpan);
		// name click
		InputObject nameClick = new InputObject();
		nameClick.setStartSpan(startSpan);
		nameClick.setEndSpan(endSpan);
		nameClick.setStringBuilder(stringBuilder);
		nameClick.setOperationType(NAME_CLICKED);
		nameClick.setMultiActionTextviewClickListener(new MultiActionTextviewClickListener() {
			@Override
			public void onTextClick(InputObject inputObject) {
//				mOnItemClickListener.blueTextClick(inputObject.getOperationType(), lists.get(arg).getUid());
//				Intent intent = new Intent(context, CirclePersonActivity.class);
//				intent.putExtra("id",lists.get(arg).getUid());
//				context.startActivity(intent);
			}
		});
		MultiActionTextView.addActionOnTextViewWithoutLink(nameClick);
		ForegroundColorSpan Span1 = new ForegroundColorSpan(context.getResources().getColor(R.color.yellow));
		stringBuilder.setSpan(Span1, startSpan, endSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// content name click
		endSpan = endSpan + action.length();
		startSpan = endSpan;
		endSpan = endSpan + contentName.length();
		Log.d(TAG, " start : endspan::" + startSpan + " : " + endSpan);
		if (startSpan==endSpan){

		}else {
			InputObject contentClick = new InputObject();
			contentClick.setStartSpan(startSpan);
			contentClick.setEndSpan(endSpan);
			contentClick.setStringBuilder(stringBuilder);
			contentClick.setOperationType(CONTENT_CLICKED);
			contentClick.setMultiActionTextviewClickListener(new MultiActionTextviewClickListener() {
				@Override
				public void onTextClick(InputObject inputObject) {
//					Intent intent = new Intent(context, CirclePersonActivity.class);
//					intent.putExtra("id", lists.get(arg).getAt_uid());
//					context.startActivity(intent);
				}
			});
			MultiActionTextView.addActionOnTextViewWithoutLink(contentClick);
			ForegroundColorSpan Span2 = new ForegroundColorSpan(context.getResources().getColor(R.color.yellow));
			stringBuilder.setSpan(Span2, startSpan, endSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		// replay click
		endSpan = endSpan +1;
		startSpan = endSpan;
		endSpan = endSpan + reply_content.length();
		InputObject replyClick = new InputObject();
		replyClick.setStartSpan(startSpan);
		replyClick.setEndSpan(endSpan);
		replyClick.setStringBuilder(stringBuilder);
		replyClick.setOperationType(ACTION_CLICKED);
		replyClick.setMultiActionTextviewClickListener(new MultiActionTextviewClickListener() {
			@Override
			public void onTextClick(InputObject inputObject) {
//				operationType = "Action Clicked";
				if (mOnItemClickListener!=null){
					mOnItemClickListener.blueTextClick(tv,inputObject.getOperationType(),up_ids,arg);
				}
			}
		});
		MultiActionTextView.addActionOnTextViewWithoutLink(replyClick);
		ForegroundColorSpan yellowSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.main_black));
		stringBuilder.setSpan(yellowSpan,startSpan,endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// final step
		MultiActionTextView.setSpannableText(tv,
				stringBuilder, context.getResources().getColor(R.color.yellow));
		stringBuilder.setSpan(yellowSpan, startSpan, endSpan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	class MyMultiActionClickListener implements
			MultiActionTextviewClickListener {
		private int id;

		public MyMultiActionClickListener(int id) {
			this.id = id;
		}

		@Override
		public void onTextClick(InputObject inputObject) {
			int operation = inputObject.getOperationType();
			String operationType = "";
			switch (operation) {
				case NAME_CLICKED:
					operationType = "Name Clicked";
					break;
				case ACTION_CLICKED:
					operationType = "Action Clicked";
					break;
				case CONTENT_CLICKED:
					operationType = "Content Clicked";
					break;
			}
			Log.e(TAG,operationType);
		}
	}

	public static interface OnBlueTextClick {
		void blueTextClick(TextView view,int operationType,int position,int pos);
	}
	public void setOnBlueTextClick(OnBlueTextClick listener) {
		this.mOnItemClickListener = listener;
	}
}
