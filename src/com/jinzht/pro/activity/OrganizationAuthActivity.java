package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.ConditionAdapter;
import com.jinzht.pro.model.AuthInformation;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.PersonAuthBean;
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
 * 机构投资者认证页面
 */
public class OrganizationAuthActivity extends BaseActivity implements ConditionAdapter.ConditionChanged {

    private ConditionAdapter adapter;// 认证条件数据适配器
    private List<PersonAuthBean.DataEntity.QualificationEntity> condition_list = new ArrayList<>();// 认证条件数据
    private HashMap<Integer, Boolean> map = new HashMap<>();// 符合条件的集合
    private String condition_id = "";
    private int image_flag = 0;
    private String image_path = "";
    private Intent intent;
    private Uri imageUri;
    private String file_name = "";
    private final String FILE_PATH = Environment.getExternalStorageDirectory() + "/" + "ori_auth.jpg";// 照相保存地址

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_condition)
    MyListview lv_condition;// 认证条件列表
    @Bind({R.id.ed_organization_name, R.id.ed_order_name, R.id.ed_scale_auth})
    List<EditText> editTexts;// 机构名、法人名、机构规模
    @Bind(R.id.tv_identity)
    TextView tv_identity;// 上传身份证的地方
    @Bind(R.id.iv_identity)
    ImageView iv_identity;// 上传的身份证

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.invest_toast)
    void toasht() {
        intent = new Intent(mContext, NoticeActivity.class);
        intent.putExtra("title", getResources().getString(R.string.invest_toast));
        intent.putExtra("flag", 2);
        startActivity(intent);
    }

    @OnClick(R.id.rl_identity)
    void text() {
        showImageDialog();
    }

    // 上传身份证时拍照或打开相册选择
    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_image_title))
//                .setIcon(R.drawable.ic_launcher)
//                .setAdapter();//�Զ����б�������image,,,
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
//                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"invest.jpg"));
//                            //ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
//                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                            startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
                        } else {
                            Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            openAlbumIntent.setType("image/*");
                            startActivityForResult(openAlbumIntent, Constant.CHOOSE_PICTURE);
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
                SuperToastUtils.showSuperToast(OrganizationAuthActivity.this, 1, R.string.invest_contextss);
        } else if (image_path.equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(OrganizationAuthActivity.this, 1, R.string.upload_people);
        } else if (editTexts.get(0).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(OrganizationAuthActivity.this, 1, R.string.organization_name_hint);
        } else if (editTexts.get(1).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(OrganizationAuthActivity.this, 1, R.string.order_name_hint);
        } else if (editTexts.get(2).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(OrganizationAuthActivity.this, 1, R.string.money_scale_hint);
        } else {
            condition_id = "";
            for (Integer key : map.keySet()) {
                if (map.get(key)) {
                    condition_id = condition_id + condition_list.get(key).getKey() + ",";
                }
            }
            condition_id = condition_id.substring(0, condition_id.length() - 1);
            PostTask postTask = new PostTask();
            postTask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_organization_auth;
    }

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.orag_title));
        ConditionTask task = new ConditionTask();
        task.execute();
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

    // 拍摄或从相册选择身份证照片并展示
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
                Log.i("imgUri", imgUri + "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    image_path = CommonUtils.getPath(mContext, imgUri);
                } else {
                    image_path = CommonUtils.query(mContext, imgUri);
                }
                Log.i("image_path", image_path + "path");
                UpLoadImageUtils.getAuthImage(OrganizationAuthActivity.this, "file://" + image_path, iv_identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            image_flag = 1;
        } else if (resultCode == RESULT_OK && requestCode == Constant.CHOOSE_PICTURE) {
            tv_identity.setVisibility(View.GONE);
            iv_identity.setVisibility(View.VISIBLE);
            try {
                Uri imgUri = data.getData();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    image_path = CommonUtils.getPath(mContext, imgUri);
                } else {
                    image_path = CommonUtils.query(mContext, imgUri);
                }
                GlideUtils.loadImg(mContext, imgUri, iv_identity);
                image_flag = 1;
//                image_path= PathConvertUri.getImagePathFromUri(imgUri, mContext);
                Log.i("image_path", image_path);
//                UpLoadImageUtils.getAuthImage(OrganizationAuthActivity.this, "file://" + image_path, iv_identity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void errorPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SuperToastUtils.showSuperToast(OrganizationAuthActivity.this, 1, R.string.connection_error);
            }
        });
    }

    @Override
    public void blankPage() {
    }

    @Override
    public void successRefresh() {
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
            if (!NetWorkUtils.getNetWorkType(OrganizationAuthActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
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
                adapter = new ConditionAdapter(mContext, condition_list, OrganizationAuthActivity.this);
                lv_condition.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    // 将资料上传至服务端
    private class PostTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(OrganizationAuthActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doAuthPost("qualification", condition_id, "institute", editTexts.get(0).getText().toString(),
                            "legalperson", editTexts.get(1).getText().toString(), "fundsize", editTexts.get(2).getText().toString(), image_path,
                            Constant.BASE_URL + Constant.PHONE + Constant.INVEST_CONDITION, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                }
                Log.i("上传认证资料后的返回信息", body);
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
                /**�˴��ɹ��˸ĸ�ʲô*/
//                SharePreferencesUtils.setPerfectInformation(mContext,true);
                /**��һ��ĶԻ��򵽵׵���������ʱ��û�ж�*/
                DialogUtils.authSuccessDialog(OrganizationAuthActivity.this, aVoid.getMsg());
            } else {
                UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
            }
        }
    }

    private class GetInforTask extends AsyncTask<Void, Void, AuthInformation> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthInformation doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(OrganizationAuthActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVEST_CONDITION, mContext);
                    if (FastJsonTools.getBean(body, PersonAuthBean.class) != null && FastJsonTools.getBean(body, PersonAuthBean.class).getData() != null) {
                        condition_list.addAll(FastJsonTools.getBean(body, PersonAuthBean.class).getData().getQualification());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, body);
                return FastJsonTools.getBean(body, AuthInformation.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthInformation aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    if (condition_list.size() != 0) {
                        String[] item = aVoid.getData().getQua().split(",");
                        Log.e(TAG, item.length + "length");
                        for (int i = 0; i < item.length; i++) {
                            map.put(Integer.parseInt(item[i]), true);
                        }
                        lv_condition.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}