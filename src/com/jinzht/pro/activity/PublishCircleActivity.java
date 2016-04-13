package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.CircleNineGridAdapter;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.UserBean;
import com.jinzht.pro.photoselecter.model.PhotoModel;
import com.jinzht.pro.photoselecter.ui.PhotoPreviewActivity;
import com.jinzht.pro.photoselecter.ui.PhotoSelectorActivity;
import com.jinzht.pro.photoselecter.util.CommonUtils;
import com.jinzht.pro.utils.*;
import com.jinzht.pro.view.CopyFile;
import com.jinzht.pro.view.InputMethodRelativeLayout;
import com.jinzht.pro.view.MyGridview;
import com.mob.tools.utils.UIHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/12.
 *
 * 点击圈子右上角发表内容界面
 */
public class PublishCircleActivity extends BaseActivity implements InputMethodRelativeLayout.OnSizeChangedListenner {
    private Intent intent;
    @Bind(R.id.rl_publish)
    RelativeLayout rl_publish;
    @Bind(R.id.gv_preview)
    MyGridview gv_preview;
    @Bind(R.id.ed_publish)
    EditText ed_publish;
    private CircleNineGridAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ArrayList<String> camera_list = new ArrayList<>();
    private ArrayList<String> final_list = new ArrayList<>();

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    private int count = 0;
    private int album_count = 0;
    protected List<PhotoModel> photos;
    @Bind(R.id.scroll_view)
    ScrollView scroll_view;
    Bitmap bitmap = null;
    private UserBean userBean;
    private ArrayList<PhotoModel> selected = new ArrayList<>();
    @Bind(R.id.keyboardRelativeLayouts)
    InputMethodRelativeLayout inputMethodRelativeLayout;

    @OnItemClick(R.id.gv_preview)
    void on(int pos) {
        Log.e(TAG, pos + "");
        if (pos == final_list.size()) {
            if (9 - final_list.size() <= 0) {
                SuperToastUtils.showSuperToast(PublishCircleActivity.this, 1, R.string.max_img_limit_reached);
            } else {
                showImageDialog();
                Constant.MAX_IMAGE = 9 - final_list.size();
            }
        } else {
            selected.clear();
            for (int i = 0; i < final_list.size(); i++) {
                PhotoModel photoModel = new PhotoModel(final_list.get(i), true);
                selected.add(photoModel);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("photos", selected);
            bundle.putInt("flag", 0);
            bundle.putInt("pos", pos);
            CommonUtils.launchActivity(mContext, PhotoPreviewActivity.class, bundle);
        }
    }

    private void showDeleteDialog(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.submit_delete))
                .setPositiveButton(getResources().getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        final_list.remove(pos);
                        adapter = new CircleNineGridAdapter(PublishCircleActivity.this, final_list);
                        gv_preview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.crop__cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    @OnClick({R.id.rl_publish})
    void publish(RelativeLayout view) {
        switch (view.getId()) {
            case R.id.rl_publish:
                if (ed_publish.getText().toString().equals("") && final_list.size() == 0) {
                    if (!UiHelp.isFastClick()) {
                        SuperToastUtils.showSuperToast(PublishCircleActivity.this, 1, R.string.no_conte);
                    }
                } else {
                    if (NetWorkUtils.getNetWorkType(PublishCircleActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                        if (!UiHelp.isFastClick()) {
                            SuperToastUtils.showSuperToast(PublishCircleActivity.this, 1, R.string.no_wifi);
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("list", final_list);
                        intent.putExtra("edit", ed_publish.getText().toString());
                        setResult(119, intent);
                        finish();
                        MainActivity.vpFragment.setCurrentItem(3);
                    }
                }
                break;
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_publish_circle;
    }

    @Override
    protected void init() {
        if (!SharePreferencesUtils.contains(mContext, "id")) {
            GetInformationTask task = new GetInformationTask();
            task.execute();
        }
        adapter = new CircleNineGridAdapter(PublishCircleActivity.this, list);
        gv_preview.setAdapter(adapter);
        inputMethodRelativeLayout.setOnSizeChangedListenner(this);
        gv_preview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos != final_list.size()) {
                    //����ɾ��
                    showDeleteDialog(pos);
                }
                return true;
            }
        });
        ed_publish.requestFocus();
        UiHelp.openOrHide(PublishCircleActivity.this);
    }

    //ͷ���ѡ��Ի���
    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_image_title))
//                .setIcon(R.drawable.ic_launcher)
//                .setAdapter();//�Զ����б�������image,,,
                .setItems(R.array.image_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            judgeFolder();
                            takePhoto();
                        } else {
                            judgeFolder();
                            intent = new Intent(mContext, PhotoSelectorActivity.class);
                            startActivityForResult(intent, 134);
                        }
                    }
                });
        builder.create().show();
    }

    private void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Constant.FILE_PATH + "/JinZht" + "/CircleImage", "circle.png"));
        //ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
    }

    private void judgeFolder() {
        if (FileUtil.isFolderExist(Constant.FILE_PATH + "/JinZht")) {
            Log.e(TAG, "cunzai");
            if (!FileUtil.isFolderExist(Constant.FILE_PATH + "/JinZht" + "/CircleImage")) {
                FileUtil.createFolder(Constant.FILE_PATH + "/JinZht" + "/CircleImage");
            }
        } else {
            Log.e(TAG, "nozai");
            FileUtil.createFolder(Constant.FILE_PATH + "/JinZht");
            if (!FileUtil.isFolderExist(Constant.FILE_PATH + "/JinZht")) {
                FileUtil.createFolder(Constant.FILE_PATH + "/JinZht" + "/CircleImage");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ImageLoader.getInstance().clearDiskCache();
//        ImageLoader.getInstance().clearMemoryCache();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.TAKE_PICTURE:/**�����������*/
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            File files = new File(Constant.FILE_PATH + "/JinZht" + "/CircleImage/", "circle.png");
                            Bitmap bitmaps = ImageLoader.getInstance().loadImageSync("file://" + Constant.FILE_PATH + "/JinZht" + "/CircleImage/" + "circle.png");
                            Log.e(TAG, files.length() / 1024 + "oran");
                            if (files.length() / 1024 > 3000) {
                                ImageHandleUtils.saveBmpToSd(Constant.FILE_PATH + "/JinZht/" + "/CircleImage/", bitmaps, "zoom" + count + ".png", 70, true);
                                final_list.add(Constant.FILE_PATH + "/JinZht/" + "/CircleImage/" + "zoom" + count + ".png");
                            } else if (files.length() / 1024 > 2000) {
                                ImageHandleUtils.saveBmpToSd(Constant.FILE_PATH + "/JinZht/" + "/CircleImage/", bitmaps, "zoom" + count + ".png", 80, true);
                                final_list.add(Constant.FILE_PATH + "/JinZht/" + "/CircleImage/" + "zoom" + count + ".png");
                            } else {
                                Log.e(TAG, "uncompress");
                                final_list.add(Constant.FILE_PATH + "/JinZht" + "/CircleImage/" + "circle.png");
                            }
                            bitmaps.recycle();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new CircleNineGridAdapter(PublishCircleActivity.this, final_list);
                                    gv_preview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            count++;
                        }
                    }).start();
                    break;
                case 134:/**���������*/
                    Log.e(TAG, data.getExtras() + "");
                    photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
                    Log.e(TAG, "ss" + photos.size());
                    if (camera_list.size() != 0) {
                        camera_list.clear();
                    }
                    for (int i = 0; i < photos.size(); i++) {
                        camera_list.add(photos.get(i).getOriginalPath());
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < camera_list.size(); i++) {
                                    File file = new File(camera_list.get(i));
//                                    CopyFile.copyFile(camera_list.get(i), Constant.FILE_PATH + "/JinZht/" + "cirlce" + i + ".png");
                                    try {
                                        ImageSize mImageSize = new ImageSize(100, 100);
                                        //��ʾͼƬ������
                                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                                .cacheInMemory(false)
                                                .cacheOnDisk(false)
                                                .bitmapConfig(Bitmap.Config.RGB_565)
                                                .build();
                                        bitmap = ImageLoader.getInstance().loadImageSync("file://" + camera_list.get(i), options);
                                        if (file.length() / 1024 > 3000) {
                                            Log.e(TAG, "3000");
                                            ImageHandleUtils.saveBmpToSd(Constant.FILE_PATH + "/JinZht/CircleImage/", bitmap, "cirlce" + i + ".png", 70, true);
                                            final_list.add(Constant.FILE_PATH + "/JinZht/CircleImage/" + "cirlce" + i + ".png");
                                        } else if (file.length() / 1024 > 2000) {
                                            Log.e(TAG, "2000");
                                            ImageHandleUtils.saveBmpToSd(Constant.FILE_PATH + "/JinZht/CircleImage/", bitmap, "cirlce" + i + ".png", 80, true);
                                            final_list.add(Constant.FILE_PATH + "/JinZht/CircleImage/" + "cirlce" + i + ".png");
                                        } else if (file.length() / 1024 > 1000) {
                                            Log.e(TAG, "1000");
                                            ImageHandleUtils.saveBmpToSd(Constant.FILE_PATH + "/JinZht/CircleImage/", bitmap, "cirlce" + i + ".png", 90, true);
                                            final_list.add(Constant.FILE_PATH + "/JinZht/CircleImage/" + "cirlce" + i + ".png");
                                        } else if (file.length() / 1024 > 500) {
                                            ImageHandleUtils.saveBmpToSd(Constant.FILE_PATH + "/JinZht/CircleImage/", bitmap, "cirlce" + i + ".png", 95, true);
                                            Log.e(TAG, "500");
                                            final_list.add(Constant.FILE_PATH + "/JinZht/CircleImage/" + "cirlce" + i + ".png");
                                        } else {
                                            Log.e(TAG, "uncompress");
                                            final_list.add(camera_list.get(i));
                                        }
                                        if (bitmap != null) {
                                            bitmap.recycle();
                                            bitmap = null;
                                            System.gc();
                                        }
                                    } catch (OutOfMemoryError error) {
                                        if (bitmap != null) {
                                            bitmap.recycle();
                                            bitmap = null;
                                            System.gc();
                                        }
                                        final_list.add(camera_list.get(i));
                                    }
                                    Log.e(TAG, file.length() / 1024 + "oran");
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter = new CircleNineGridAdapter(PublishCircleActivity.this, final_list);
                                        gv_preview.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } catch (OutOfMemoryError e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter = new CircleNineGridAdapter(PublishCircleActivity.this, final_list);
                                        gv_preview.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } catch (Exception e) {

                            }
                        }
                    }).start();
                    break;
            }
        }
    }

    @Override
    public void onSizeChange(boolean paramBoolean, int w, int h) {

    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {
    }

    @Override
    public void successRefresh() {

    }

    private class PhotoTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(PublishCircleActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doPhotoPost(final_list, Constant.BASE_URL + Constant.PHONE + Constant.PUBLISH_STATES, ed_publish.getText().toString(), PublishCircleActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == 0) {
                    Log.e(TAG, "ssss");
                    Intent intent = new Intent();
                    setResult(119, intent);
                    finish();
                    MainActivity.vpFragment.setCurrentItem(3);
                }
            }
            try {
                SuperToastUtils.showSuperToast(PublishCircleActivity.this, 1, aVoid.getMsg());
            } catch (Exception e) {

            }
        }
    }

    private class GetInformationTask extends AsyncTask<Void, Void, UserBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected UserBean doInBackground(Void... voids) {

            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, UserBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    SharePreferencesUtils.setCircleInformation(mContext, aVoid.getData().getUid(), aVoid.getData().getPhoto(), aVoid.getData().getAddr(), aVoid.getData().getPosition(), aVoid.getData().getName());
                } else {

                }
            }
        }
    }
}