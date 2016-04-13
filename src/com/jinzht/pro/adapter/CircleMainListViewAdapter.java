package com.jinzht.pro.adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.CirclePersonActivity;
import com.jinzht.pro.activity.ThreeWebviewActivity;
import com.jinzht.pro.expandabletextview.ExpandableTextView;
import com.jinzht.pro.fragment.CircleFragment;
import com.jinzht.pro.model.CircleBean;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.view.ExpandTextView;
import com.jinzht.pro.view.MyGridview;
import com.jinzht.pro.view.MyListview;
import com.jinzht.pro.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

public class CircleMainListViewAdapter extends BaseAdapter implements CircleListViewAdapter.OnBlueTextClick{

	private OnPhotoItemClickListener onPhotoItemClickListener = null;
	private OnCommentItemClickListener onCommentItemClickListener = null;
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> img_list;
	private ArrayList<CircleBean.DataEntity> title_list;
	private TextCallBack textsCallBack;
	private List<String> list = new ArrayList<>();
	private List<CircleBean.DataEntity.LikeEntity> lover_list = new ArrayList<>();
	public CircleMainListViewAdapter(Context context,OnPhotoItemClickListener mOnItemClickListener,
									 OnCommentItemClickListener onCommentItemClickListener1,
									 ArrayList<CircleBean.DataEntity> title_list,ArrayList<String> list,TextCallBack textCallBack) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.onPhotoItemClickListener = mOnItemClickListener;
		this.img_list = list;
		this.title_list = title_list;
		textsCallBack = textCallBack;
		this.onCommentItemClickListener= onCommentItemClickListener1;
	}

	@Override
	public int getCount() {
		return title_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public void blueTextClick(TextView view,int operationType, int position, int pos) {
		onCommentItemClickListener.onItemClick(view,operationType,position,pos);
	}

	public class ViewHolder {
		private MyGridview mImgGridView;
		private MyListview mListView;
		private ImageView  iv_user,iv_share;
		private TextView tv_name,tv_time,tv_location,tv_delete,tv_share;
		private ExpandTextView tv_content;
		private TagGroup tag_group;
		private RelativeLayout rl_liker;
		private LinearLayout ll_share;
		private ImageView tv_comment,tv_love;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_circle_main, null);
			viewHolder = new ViewHolder();
			viewHolder.mImgGridView = (MyGridview) convertView
					.findViewById(R.id.gv_listView_main_gridView);
			viewHolder.mListView = (MyListview) convertView
					.findViewById(R.id.lv_item_listView);
			viewHolder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tv_content = (ExpandTextView) convertView.findViewById(R.id.tv_content);
			viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
			viewHolder.tv_comment = (ImageView) convertView.findViewById(R.id.tv_comment);
			viewHolder.tv_love = (ImageView) convertView.findViewById(R.id.tv_love);
			viewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
			viewHolder.tag_group = (TagGroup) convertView.findViewById(R.id.tag_group);
			viewHolder.rl_liker = (RelativeLayout) convertView.findViewById(R.id.rl_liker);
			viewHolder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
			viewHolder.tv_share = (TextView) convertView.findViewById(R.id.tv_share);
			viewHolder.iv_share = (ImageView) convertView.findViewById(R.id.iv_share);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (title_list.get(arg0).getPic()==null){
			viewHolder.mImgGridView.setVisibility(View.GONE);
		}else if (title_list.get(arg0).getPic().size()!=0){
			viewHolder.mImgGridView.setVisibility(View.VISIBLE);
			viewHolder.mImgGridView.setAdapter(new CircleGridViewAdapter(context, arg0, title_list.get(arg0).getPic()));
		}else {
			viewHolder.mImgGridView.setVisibility(View.GONE);
		}
		if (title_list.get(arg0).getFlag()){
			viewHolder.tv_delete.setVisibility(View.VISIBLE);
		}else {
			viewHolder.tv_delete.setVisibility(View.GONE);
		}
//		new CircleListViewAdapter(this,context,title_list.get(arg0).getComment(),arg0).setOnBlueTextClick(new CircleListViewAdapter.OnBlueTextClick() {
//			@Override
//			public void blueTextClick(int operationType, int position, int pos) {
//				onCommentItemClickListener.onItemClick(operationType,position,pos);
//			}
//		});
		viewHolder.tv_location.setText(title_list.get(arg0).getAddr()+"|"+title_list.get(arg0).getPosition());
		if (title_list.get(arg0).getShare()==null||title_list.get(arg0).getShare().equals("[]")){
			viewHolder.ll_share.setVisibility(View.GONE);
		}else {
			viewHolder.ll_share.setVisibility(View.VISIBLE);
			UpLoadImageUtils.getRoundImage(title_list.get(arg0).getShare().getImg(),viewHolder.iv_share);
			viewHolder.tv_share.setText(title_list.get(arg0).getShare().getTitle());
		}
		if (title_list.get(arg0).getComment()==null||title_list.get(arg0).getComment().equals("[]")){
			viewHolder.mListView.setVisibility(View.GONE);
		}else if (title_list.get(arg0).getComment().size()!=0){
			viewHolder.mListView.setVisibility(View.VISIBLE);
			for (int i = 0; i < title_list.get(arg0).getComment().size(); i++) {
				viewHolder.mListView.setAdapter(new CircleListViewAdapter(this,context,title_list.get(arg0).getComment(),arg0));
			}
		}else {
			viewHolder.mListView.setVisibility(View.GONE);
		}
		if (title_list.get(arg0).getLike()==null){
			viewHolder.rl_liker.setVisibility(View.GONE);
		}else if (title_list.get(arg0).getLike().size()!=0){
			viewHolder.rl_liker.setVisibility(View.VISIBLE);
			list.clear();
			for (int i = 0; i < title_list.get(arg0).getLike().size(); i++) {
				if (i==title_list.get(arg0).getLike().size()-1){
					list.add(title_list.get(arg0).getLike().get(i).getName());
				}else
					list.add(title_list.get(arg0).getLike().get(i).getName() + ",");
			}
//			viewHolder.tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
//				@Override
//				public void onTagClick(String tag) {
//					for (int i = 0; i < title_list.get(arg0).getLike().size(); i++) {
//						if (tag.equals(title_list.get(arg0).getLike().get(i).getName()+",")){
//							Intent intent = new Intent(context, CirclePersonActivity.class);
//							intent.putExtra("id",title_list.get(arg0).getLike().get(i).getUid());
//							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							context.startActivity(intent);
//						}
//					}
//				}
//			});
			Log.e("lists",list.size()+"size");
			viewHolder.tag_group.setTags(list);
		}else {
			viewHolder.rl_liker.setVisibility(View.GONE);
		}
		viewHolder.mImgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
				onPhotoItemClickListener.onItemClick(parent,view,arg0,i);
			}
		});
		viewHolder.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
				onPhotoItemClickListener.onItemClick(parent,view,arg0,i);
			}
		});
		viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent  intent = new Intent(context, ThreeWebviewActivity.class);
				intent.putExtra("id",title_list.get(arg0).getShare().getId()+"");
				intent.putExtra("url",title_list.get(arg0).getShare().getUrl());
				context.startActivity(intent);
			}
		});
		UpLoadImageUtils.getRoundImage(title_list.get(arg0).getPhoto(), viewHolder.iv_user);
		viewHolder.tv_name.setText(title_list.get(arg0).getName());
		viewHolder.tv_content.setText(title_list.get(arg0).getContent());
		viewHolder.tv_time.setText(title_list.get(arg0).getDatetime());
		final View finalConvertView = convertView;
		viewHolder.tv_comment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textsCallBack.onTextClicks(view, arg0);
			}
		});
		viewHolder.tv_love.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textsCallBack.onTextClicks(view,arg0);
			}
		});
		viewHolder.iv_user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textsCallBack.onTextClicks(view,arg0);
			}
		});
		viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textsCallBack.onTextClicks(view,arg0);
			}
		});
		return convertView;
	}

	public static interface OnPhotoItemClickListener {
		void onItemClick(AdapterView<?> adapterView,View view , int pos_first,int pos_second);
	}
	public static interface OnCommentItemClickListener {
		void onItemClick(TextView view,int pos1, int pos_first,int pos_second);
	}
	public interface TextCallBack{
		public void onTextClicks(View view,int position);
	}

	public void addComment(int pos ,int pos_sec,List<CircleBean.DataEntity> lists){
		title_list.addAll(pos,lists);
		notifyDataSetChanged();
	}
}
