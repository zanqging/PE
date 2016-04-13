package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.ConditionAdapter;
import com.jinzht.pro.cropimageview.Crop;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.PersonAuthBean;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.photoselecter.util.CommonUtils;
import com.jinzht.pro.utils.*;
import com.jinzht.pro.view.MyListview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/11/6.
 * <p>
 * 个人投资者认证界面
 */
public class PersonAuthenticationActivity extends BaseActivity implements ConditionAdapter.ConditionChanged {

    private ConditionAdapter adapter;// 认证条件数据适配器
    private String file_name = "";
    private List<PersonAuthBean.DataEntity.QualificationEntity> condition_list = new ArrayList<>();// 认证条件数据
    private HashMap<Integer, Boolean> map = new HashMap<>();// 符合条件的集合
    private String condition_id = "";
    private Intent intent;
    private int image_flag = 0;
    private String image_path = "";
    private Bitmap bitmap;
    private Uri imageUri;
    private int user_image_flag = 0;
    private final String FILE_PATH = Environment.getExternalStorageDirectory() + "/" + "person_auth.jpg";// 照相保存地址

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_condition)
    MyListview lv_condition;// 认证条件列表
    @Bind(R.id.iv_identity)
    ImageView iv_identity;// 上传的身份证
    @Bind(R.id.tv_identity)
    TextView tv_identity;// 点击上传身份证
    @Bind(R.id.iv_person_auth)
    PolygonImageView iv_person_auth;// 圆形头像
    @Bind({R.id.ed_person_history})
    List<EditText> editTexts;// 个人经历输入框
    @Bind(R.id.tv_person_img)
    TextView tv_person_img;// 点击上传个人照片

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    // 点击上传个人照片
    @OnClick(R.id.tv_person_img)
    void person() {
        Crop.pickImage(PersonAuthenticationActivity.this);
    }

    @OnClick(R.id.invest_toast)
    void toasht() {
        intent = new Intent(mContext, NoticeActivity.class);
        intent.putExtra("title", getResources().getString(R.string.invest_toast));
        intent.putExtra("flag", 2);
        startActivity(intent);
    }

    // 点击上传身份证
    @OnClick(R.id.rl_identity)
    void text() {
        showImageDialog();
    }

    // 上传身份证时拍照或打开相册选择
    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_image_title))
                .setItems(R.array.image_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 指定开启系统相机的Action
                            openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            openCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            // 根据文件地址创建文件
                            File file = new File(FILE_PATH);
                            if (file.exists()) {
                                file.delete();
                            }
                            // 把文件地址转换成Uri格式
                            Uri uri = Uri.fromFile(file);
                            // 设置系统相机拍摄照片完成后图片文件的存放地址
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
//                            imageUri = Uri.fromFile(new File(FILE_PATH));
//                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                            openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                            openCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
//                            startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, Constant.CHOOSE_PICTURE);
                        }
                    }
                });
        builder.create().show();
    }

    // 点击上传资料
    @OnClick(R.id.btn_post)
    void ons() {
        if (map.size() == 0) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(PersonAuthenticationActivity.this, 1, R.string.invest_contexts);
        } else if (image_path.equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(PersonAuthenticationActivity.this, 1, R.string.upload_people);
        } else if (user_image_flag == 0) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(PersonAuthenticationActivity.this, 1, R.string.upload_people_img);
        } else if (editTexts.get(0).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(PersonAuthenticationActivity.this, 1, R.string.person_history_hint);
        } else {
            condition_id = "";
            for (Integer key : map.keySet()) {
                if (map.get(key)) {
                    condition_id = condition_id + condition_list.get(key).getKey() + ",";
                }
            }
            try {
                condition_id = condition_id.substring(0, condition_id.length() - 1);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            PostTask postTask = new PostTask();
            postTask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_person_authentication;
    }

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.person_title));
        ConditionTask task = new ConditionTask();
        task.execute();
        editTexts.get(0).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    UiHelp.openOrHide(mContext);
                }
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /**很简答的一个事，首先触摸时间edit先执行这个函数，若他返回true就证明被消费了，就不会执行ontouch，还有onclick*/
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了,这是整个屏幕的分发事件
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 选择投资条件
    @Override
    public void conditionChanged(CompoundButton compoundButton, boolean b, int postion) {
        if (b) {
            map.put(postion, true);
        } else {
            map.put(postion, false);
        }
    }

    @Override
    public void errorPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SuperToastUtils.showSuperToast(PersonAuthenticationActivity.this, 1, R.string.connection_error);
            }
        });
    }

    @Override
    public void blankPage() {
    }

    @Override
    public void successRefresh() {
    }

    // 拍摄或从相册选择身份证和头像并展示
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        image_flag = 0;
        if (resultCode == RESULT_OK && requestCode == Constant.TAKE_PICTURE) {// 拍照返回
            tv_identity.setVisibility(View.GONE);
            iv_identity.setVisibility(View.VISIBLE);
            try {
                File file = new File(FILE_PATH);
                Uri imgUri = Uri.fromFile(file);
                Log.i("imgUri", imgUri.toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    image_path = CommonUtils.getPath(mContext, imgUri);
                } else {
                    image_path = CommonUtils.query(mContext, imgUri);
                }
                Log.i("image_path", image_path + "path");
                GlideUtils.loadImg(mContext, imgUri, iv_identity);
//                UpLoadImageUtils.getAuthImage(PersonAuthenticationActivity.this,"file://" + image_path, iv_identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            image_flag = 1;
        } else if (resultCode == RESULT_OK && requestCode == Constant.CHOOSE_PICTURE) {
            tv_identity.setVisibility(View.GONE);
            iv_identity.setVisibility(View.VISIBLE);
            try {
                Uri imgUri = data.getData();
                Log.i("imgUri", imgUri + "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    image_path = CommonUtils.getPath(mContext, imgUri);
                } else {
                    image_path = CommonUtils.query(mContext, imgUri);
                }
                GlideUtils.loadImg(mContext, imgUri, iv_identity);
                Log.i("image_path", image_path + "path");
                image_flag = 1;
//                UpLoadImageUtils.getAuthImage(PersonAuthenticationActivity.this,"file://" + image_path, iv_identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**头像裁图*/
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {// 从相册取得
            beginCrop(data.getData());
            Log.i("beginCrop", "begin");
        } else if (requestCode == Crop.REQUEST_CROP) {// 得到剪裁后的图片
            handleCrop(resultCode, data);
            Log.i("handleCrop", "handle");
        }
    }

    // 启动剪裁Activity
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "person_auth.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    // 处理剪裁后的图片
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            iv_person_auth.setImageURI(Crop.getOutput(result));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(PersonAuthenticationActivity.this.getContentResolver(), Crop.getOutput(result));
                tv_person_img.setVisibility(View.GONE);
                iv_person_auth.setVisibility(View.VISIBLE);
                user_image_flag = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            SuperToastUtils.showSuperToast(PersonAuthenticationActivity.this, 1, Crop.getError(result).getMessage());
        }
    }

    // 获取认证条件数据，并展示
    private class ConditionTask extends AsyncTask<Void, Void, PersonAuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected PersonAuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(PersonAuthenticationActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVEST_CONDITION, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (FastJsonTools.getBean(body, PersonAuthBean.class) != null && FastJsonTools.getBean(body, PersonAuthBean.class).getData() != null) {
                    condition_list.addAll(FastJsonTools.getBean(body, PersonAuthBean.class).getData().getQualification());
                }
                Log.i("PersonAuthBean", body);
                return FastJsonTools.getBean(body, PersonAuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(PersonAuthBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                return;
            }
            if (aVoid.getCode() == 0 && condition_list.size() != 0) {
                adapter = new ConditionAdapter(mContext, condition_list, PersonAuthenticationActivity.this);
                lv_condition.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    // 上传资料至服务端
    private class PostTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(PersonAuthenticationActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPersonAuthPost("qualification", condition_id, "profile", editTexts.get(0).getText().toString(),
                            image_path, getCacheDir() + "/person_auth.jpg",
                            Constant.BASE_URL + Constant.PHONE + Constant.INVEST_CONDITION, mContext);
                } catch (Exception e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                }
                Log.i("上传资料的返回值", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                return;
            }
            if (aVoid.getCode() == 0) {
                Intent intent = new Intent(mContext, PersonAuthNextActivity.class);
                startActivity(intent);
                finish();
            } else {
                UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
            }
        }
    }
}