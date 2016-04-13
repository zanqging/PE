package com.jinzht.pro.photoselecter.ui;
/**
 *
 * @author Aizaz AZ
 *
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.photoselecter.model.PhotoModel;
import com.jinzht.pro.photoselecter.util.AnimationUtil;
import com.jinzht.pro.utils.FileUtil;
import com.jinzht.pro.utils.SuperToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class BasePhotoPreviewActivity extends Activity implements OnPageChangeListener, OnClickListener {

	private ViewPager mViewPager;
	private RelativeLayout layoutTop;
	private ImageButton btnBack;
	private TextView tvPercent;
	protected List<PhotoModel> photos;
	protected int current;
	private TextView tv_save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		setContentView(R.layout.activity_photopreview);
		layoutTop = (RelativeLayout) findViewById(R.id.layout_top_app);
		btnBack = (ImageButton) findViewById(R.id.btn_back_app);
		tvPercent = (TextView) findViewById(R.id.tv_percent_app);
		tv_save = (TextView) findViewById(R.id.tv_save);
		mViewPager = (ViewPager) findViewById(R.id.vp_base_app);
		btnBack.setOnClickListener(this);
		tv_save.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);
		overridePendingTransition(R.anim.activity_alpha_action_in, 0); // 渐入效果
	}

	/** 绑定数据，更新界面 */
	protected void bindData() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(current);
	}

	private PagerAdapter mPagerAdapter = new PagerAdapter() {
		@Override
		public int getCount() {
			if (photos == null) {
				return 0;
			} else {
				return photos.size();
			}
		}
		@Override
		public View instantiateItem(final ViewGroup container, final int position) {
			PhotoPreview photoPreview = new PhotoPreview(getApplicationContext());
			((ViewPager) container).addView(photoPreview);
			photoPreview.loadImage(photos.get(position),getIntent().getIntExtra("flag",0));
			photoPreview.setOnClickListener(photoItemClickListener);
			return photoPreview;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	};
	protected boolean isUp;

	private void judgeFolder(){
		if (!FileUtil.isFolderExist(Constant.FILE_PATH+"/JinZht1")){
			FileUtil.createFolder(Constant.FILE_PATH + "/JinZht1");

		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back_app){
			finish();
		}
		if (v.getId()==R.id.tv_save){
			savePhoto();
		}
	}
	private void savePhoto(){
		AlertDialog.Builder builder = new AlertDialog.Builder(BasePhotoPreviewActivity.this);
		builder.setTitle(getResources().getString(R.string.save));
		builder.setPositiveButton(getResources().getString(R.string.crop__done), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				judgeFolder();
				Log.e("bitmap",photos.get(current).getOriginalPath());
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.cacheInMemory(false)
						.cacheOnDisk(false)
						.bitmapConfig(Bitmap.Config.RGB_565)
						.build();
				Bitmap bitmap = ImageLoader.getInstance().loadImageSync("file://" +ImageLoader.getInstance().getDiskCache().get(photos.get(current).getOriginalPath()).getPath(),options);
				try {
					FileUtil.saveFile(BasePhotoPreviewActivity.this,bitmap,
							Constant.FILE_PATH + "/JinZht1/"+FileUtil.getCharacterAndNumber()+".png");
					SuperToastUtils.showSuperToast(BasePhotoPreviewActivity.this,1,getResources().getString(R.string.save_success));
				} catch (Exception e) {
					e.printStackTrace();
					SuperToastUtils.showSuperToast(BasePhotoPreviewActivity.this,1,getResources().getString(R.string.save_fail));
				}
//				FileUtil.copyFile(BasePhotoPreviewActivity.this,
//						ImageLoader.getInstance().getDiskCache().get(photos.get(current).getOriginalPath()).getAbsolutePath(),
//						Constant.FILE_PATH + "/JinZht1/"+FileUtil.getCharacterAndNumber()+".png");
				dialogInterface.dismiss();
			}
		});
		builder.setNegativeButton(getResources().getString(R.string.crop__cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		builder.show();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		current = arg0;
		updatePercent();
	}

	protected void updatePercent() {
		tvPercent.setText((current + 1) + "/" + photos.size());
	}

	/** 图片点击事件回调 */
	private OnClickListener photoItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!isUp) {
				new AnimationUtil(getApplicationContext(), R.anim.translate_up)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutTop);
				isUp = true;
			} else {
				new AnimationUtil(getApplicationContext(), R.anim.translate_down_current)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutTop);
				isUp = false;
			}
		}
	};
}
