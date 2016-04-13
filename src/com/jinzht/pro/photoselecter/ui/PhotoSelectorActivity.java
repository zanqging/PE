package com.jinzht.pro.photoselecter.ui;
/**
 * @author Aizaz AZ
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.PublishCircleActivity;
import com.jinzht.pro.photoselecter.domain.PhotoSelectorDomain;
import com.jinzht.pro.photoselecter.model.AlbumModel;
import com.jinzht.pro.photoselecter.model.PhotoModel;
import com.jinzht.pro.photoselecter.util.AnimationUtil;
import com.jinzht.pro.photoselecter.util.CommonUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aizaz AZ
 *
 * 选择照片页面
 *
 */
public class PhotoSelectorActivity extends Activity implements
        PhotoItem.onItemClickListener, PhotoItem.onPhotoItemCheckedListener, OnItemClickListener,
        OnClickListener {

    public static final int SINGLE_IMAGE = 1;
    public static final String KEY_MAX = "key_max";
//	public static int MAX_IMAGE=9;

    public static final int REQUEST_PHOTO = 0;
    private static final int REQUEST_CAMERA = 1;

    public static String RECCENT_PHOTO = null;

    private GridView gvPhotos;
    private ListView lvAblum;
    private Button btnOk;
    private TextView tvAlbum, tvPreview, tvTitle;
    private PhotoSelectorDomain photoSelectorDomain;
    private PhotoSelectorAdapter photoAdapter;
    private AlbumAdapter albumAdapter;
    private RelativeLayout layoutAlbum;
    private ArrayList<PhotoModel> selected;
    private TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RECCENT_PHOTO = getResources().getString(R.string.recent_photos);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_photoselector);
//		if (getIntent().getExtras() != null) {
//			MAX_IMAGE = getIntent().getIntExtra(KEY_MAX, 9);
//		}
//		initImageLoader();
        photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());
        selected = new ArrayList<PhotoModel>();
        tvTitle = (TextView) findViewById(R.id.tv_title_lh);
        gvPhotos = (GridView) findViewById(R.id.gv_photos_ar);
        lvAblum = (ListView) findViewById(R.id.lv_ablum_ar);
        btnOk = (Button) findViewById(R.id.btn_right_lh);
        tvAlbum = (TextView) findViewById(R.id.tv_album_ar);
        tvPreview = (TextView) findViewById(R.id.tv_preview_ar);
        layoutAlbum = (RelativeLayout) findViewById(R.id.layout_album_ar);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        btnOk.setOnClickListener(this);
        tvAlbum.setOnClickListener(this);
        tvPreview.setOnClickListener(this);
        photoAdapter = new PhotoSelectorAdapter(getApplicationContext(),
                new ArrayList<PhotoModel>(), CommonUtils.getWidthPixels(this),
                this, this, this);
        gvPhotos.setAdapter(photoAdapter);
//		ImageLoader.getInstance().clearDiskCache();
        albumAdapter = new AlbumAdapter(getApplicationContext(),
                new ArrayList<AlbumModel>());
        lvAblum.setAdapter(albumAdapter);
        lvAblum.setOnItemClickListener(this);

        findViewById(R.id.bv_back_lh).setOnClickListener(this); // 返回

        photoSelectorDomain.getReccent(reccentListener); // 更新最近照片
        photoSelectorDomain.updateAlbum(albumListener); // 跟新相册信息
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_right_lh)
            ok(); // 选完照片
        else if (v.getId() == R.id.tv_album_ar)
            album();
        else if (v.getId() == R.id.tv_preview_ar)
            priview();
        else if (v.getId() == R.id.tv_camera_vc)
            catchPicture();
        else if (v.getId() == R.id.bv_back_lh)
            finish();
    }

    /** 拍照 */
    private void catchPicture() {
        CommonUtils.launchActivityForResult(this, new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("photo", "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (requestCode == REQUEST_PHOTO && resultCode == RESULT_OK) {
            PhotoModel photoModel = new PhotoModel(CommonUtils.query(
                    getApplicationContext(), data.getData()));
            // selected.clear();
            // //--keep all
            // selected photos
            // tvNumber.setText("(0)");
            // //--keep all
            // selected photos
            // ///////////////////////////////////////////////////////////////////////////////////////////
            if (selected.size() >= Constant.MAX_IMAGE) {
                SuperToastUtils.showSuperToast(PhotoSelectorActivity.this, 1, R.string.max_img_limit_reached);
                photoModel.setChecked(false);
                photoAdapter.notifyDataSetChanged();
            } else {
                if (!selected.contains(photoModel)) {
                    selected.add(photoModel);
                }
            }
            ok();
        }
    }

    /** 完成 */
    private void ok() {
        if (selected.isEmpty()) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            if (selected.size() > Constant.MAX_IMAGE) {
                if (!UiHelp.isFastClick()) {
                    SuperToastUtils.showSuperToast(PhotoSelectorActivity.this, 1, R.string.max_img_limit_reached);
                }
            } else {
                Intent data = new Intent(PhotoSelectorActivity.this, PublishCircleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("photos", selected);
                data.putExtras(bundle);
                setResult(RESULT_OK, data);
                PhotoSelectorActivity.this.finish();
            }
        }
        Log.e("memery", "DiskCache" + ImageLoader.getInstance().getDiskCache() + "MemoryCache" + ImageLoader.getInstance().getMemoryCache());
    }

    /** 预览照片 */
    private void priview() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", selected);
        bundle.putInt("flag", 0);
        CommonUtils.launchActivity(this, PhotoPreviewActivity.class, bundle);
    }

    private void album() {
        if (layoutAlbum.getVisibility() == View.GONE) {
            popAlbum();
        } else {
            hideAlbum();
        }
    }

    /** 弹出相册列表 */
    private void popAlbum() {
        layoutAlbum.setVisibility(View.VISIBLE);
        new AnimationUtil(getApplicationContext(), R.anim.translate_up_current)
                .setLinearInterpolator().startAnimation(layoutAlbum);
    }

    /** 隐藏相册列表 */
    private void hideAlbum() {
        new AnimationUtil(getApplicationContext(), R.anim.translate_down)
                .setLinearInterpolator().startAnimation(layoutAlbum);
        layoutAlbum.setVisibility(View.GONE);
    }

    /** 清空选中的图片 */
    private void reset() {
        selected.clear();
        tvNumber.setText("(0)");
        tvPreview.setEnabled(false);
    }

    @Override
    /** 点击查看照片 */
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        if (tvAlbum.getText().toString().equals(RECCENT_PHOTO))
            bundle.putInt("position", position - 1);
        else
            bundle.putInt("position", position);
        bundle.putString("album", tvAlbum.getText().toString());
        CommonUtils.launchActivity(this, PhotoPreviewActivity.class, bundle);
    }

    @Override
    /** 照片选中状态改变之后 */
    public void onCheckedChanged(PhotoModel photoModel,
                                 CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (!selected.contains(photoModel))
                selected.add(photoModel);
            tvPreview.setEnabled(true);
        } else {
            selected.remove(photoModel);
        }
        tvNumber.setText("(" + selected.size() + ")");
        if (selected.isEmpty()) {
            tvPreview.setEnabled(false);
            tvPreview.setText(getString(R.string.preview));
        }
    }

    @Override
    public void onBackPressed() {
        if (layoutAlbum.getVisibility() == View.VISIBLE) {
            hideAlbum();
        } else
            super.onBackPressed();
    }

    @Override
    /** 相册列表点击事件 */
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        AlbumModel current = (AlbumModel) parent.getItemAtPosition(position);
        for (int i = 0; i < parent.getCount(); i++) {
            AlbumModel album = (AlbumModel) parent.getItemAtPosition(i);
            if (i == position)
                album.setCheck(true);
            else
                album.setCheck(false);
        }
        albumAdapter.notifyDataSetChanged();
        hideAlbum();
        tvAlbum.setText(current.getName());
        // tvTitle.setText(current.getName());
        // 更新照片列表
        if (current.getName().equals(RECCENT_PHOTO))
            photoSelectorDomain.getReccent(reccentListener);
        else
            photoSelectorDomain.getAlbum(current.getName(), reccentListener); // 获取选中相册的照片
    }

    /** 获取本地图库照片回调 */
    public interface OnLocalReccentListener {
        public void onPhotoLoaded(List<PhotoModel> photos);
    }

    /** 获取本地相册信息回调 */
    public interface OnLocalAlbumListener {
        public void onAlbumLoaded(List<AlbumModel> albums);
    }

    private OnLocalAlbumListener albumListener = new OnLocalAlbumListener() {
        @Override
        public void onAlbumLoaded(List<AlbumModel> albums) {
            albumAdapter.update(albums);
        }
    };

    private OnLocalReccentListener reccentListener = new OnLocalReccentListener() {
        @Override
        public void onPhotoLoaded(List<PhotoModel> photos) {
            for (PhotoModel model : photos) {
                if (selected.contains(model)) {
                    model.setChecked(true);
                }
            }
            photoAdapter.update(photos);
            gvPhotos.smoothScrollToPosition(0); // 滚动到顶端
            // reset(); //--keep selected photos

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            setContentView(R.layout.activity_null);
            Log.e("select", "destory");
            System.gc();
            selected = null;
            photoAdapter = null;
            if (ImageLoader.getInstance() != null) {
//				ImageLoader.getInstance().clearMemoryCache();
//				ImageLoader.getInstance().clearDiskCache();
            }
        } catch (Exception e) {
            Log.e("select", e.getMessage());
            e.printStackTrace();
        }

    }
}
