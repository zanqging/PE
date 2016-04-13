package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.*;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.AuthenticationBean;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.numberprogressbar.NumberProgressBar;
import com.jinzht.pro.qiniu.http.ResponseInfo;
import com.jinzht.pro.qiniu.storage.UpCompletionHandler;
import com.jinzht.pro.qiniu.storage.UpProgressHandler;
import com.jinzht.pro.qiniu.storage.UploadManager;
import com.jinzht.pro.qiniu.storage.UploadOptions;
import com.jinzht.pro.utils.*;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * 上传项目页面
 *
 * @auther Mr.Jobs
 * @date 2015/5/19
 * @time 13:56
 */

public class WantRoadShowActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private Intent intent;
    private int mResid;
    private AnimationDrawable mAnimation;
    private List<String> list = new ArrayList<>();
    private int width = 0;
    private int company_id = 0;
    private String file_folder = Environment.getExternalStorageDirectory().getPath() + "/JinZht";
    private static String ss = UiHelp.getUUID().substring(26);
    private static String file_video = Environment.getExternalStorageDirectory().getPath() + "/JinZht" + "/wantroadshow" + ss + ".mp4";
    private static String uuid = "wantroadshow" + ss + ".mp4";// 视频文件名
    private static String paths = "";
    private AlertDialog dialog;
    private TextView dialog_title, submit_btn;// 弹框描述，确认按钮
    private static double progress = 0;
    private NumberProgressBar numberProgressBar;
    private String token = "";
    private String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "jztlogo.mp4";

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.loadingIv)
    ImageView mImageView;// 小人跑动的进度等待图
    @Bind(R.id.ed_intro)
    EditText ed_intro;// 项目介绍输入框
    @Bind(R.id.ll_detail)
    LinearLayout ll_detail;// GONE掉了
    @Bind({R.id.ll_prepare, R.id.ll_uploading, R.id.ll_video_successful})
    List<LinearLayout> ll_video_list;// 上传前、上传中、已上传视频布局
    @Bind(R.id.rl_delete)
    RelativeLayout rl_delete;// 删除视频
    @Bind(R.id.select_company)
    EditText select_company;// 公司名输入框
    @Bind({R.id.type, R.id.company_register, R.id.company_states})
    List<TextView> textViewList;

    @OnClick(R.id.iv_email)
// 发送邮件
    void onss() {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:kf@jinzht.com"));
        startActivity(data);
    }

    @OnClick(R.id.about_road_show)
// 查看项目发起协议
    void tt() {
        intent = new Intent(mContext, NoticeActivity.class);
        intent.putExtra("title", getResources().getString(R.string.project_xieyi));
        intent.putExtra("flag", 3);
        startActivity(intent);
    }

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @OnClick(R.id.rl_delete)
// 删除视频
    void de() {
        rl_delete.setVisibility(View.GONE);
        ll_video_list.get(0).setVisibility(View.VISIBLE);
        ll_video_list.get(2).setVisibility(View.GONE);
    }

    @OnClick(R.id.select_company)
    void select() {
//         if (companyListBeanList==null||companyListBeanList.size()==0){
//             SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, R.string.no_company);
//         }else
//            showPopupWindow();
    }

    @OnClick(R.id.ll_prepare)
// 上传视频
    void ss() {
//        data = <File���󡢻� �ļ�·������ �ֽ�����>
//        String key = <ָ����ţ�����ϵ��ļ������� null>;
//        String token = <�ӷ����SDK��ȡ>;
        GetTokenTask task = new GetTokenTask();
        task.execute();
    }

    @OnClick(R.id.post_information)
// 提交资料
    void textview() {
        if (StringUtils.isBlank(ed_intro.getText().toString())) {
            if (!UiHelp.isFastClick()) {
                SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, R.string.project_intro);
            }
        } else if (StringUtils.isBlank(select_company.getText().toString())) {
            if (!UiHelp.isFastClick()) {
                SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, R.string.company_name_hint);
            }
        } else if (StringUtils.isBlank(paths)) {
            if (!UiHelp.isFastClick()) {
                SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, R.string.upload_video);
            }
        } else {
            SubmitTask submitTask = new SubmitTask();
            submitTask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_wantroad_show;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.want_road_show));
        numberProgressBar = (NumberProgressBar) findViewById(R.id.numberPro);
        ll_detail.setVisibility(View.GONE);
        ViewTreeObserver vto2 = select_company.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                select_company.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                width = select_company.getWidth();
            }
        });
        initData();
    }

    // 七牛云存储
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.WANT_ADD_COMPANY && resultCode == Constant.RESULT_WANT_ROAD_SHOW) {
            if (data.getExtras() == null) {
            } else {
                company_id = data.getIntExtra("company_id", 0);
                select_company.setText(data.getStringExtra("company_name"));
                Log.i("公司ID", "company" + company_id);
            }
        } else if (requestCode == 0 && resultCode == RESULT_OK) {
            TokenTask task = new TokenTask();
            task.execute();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mAnimation.start();
    }

    // 小人跑动的动画
    private void initData() {
        mImageView.setBackgroundResource(R.drawable.people_run);
        // ͨ��ImageView�����õ�������ʾ��AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // Ϊ�˷�ֹ��onCreate������ֻ��ʾ��һ֡�Ľ������֮һ
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }

    // 提交资料到服务端
    private class SubmitTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(WantRoadShowActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doSubmitPost("vcr", paths, "desc", ed_intro.getText().toString(),
                            "company", select_company.getText().toString(),
                            Constant.BASE_URL + Constant.PHONE + Constant.WANTROADSHOW, WantRoadShowActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("上传项目返回信息", body);
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
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    tipDialog();
                    dialog_title.setText(aVoid.getMsg());
                    submit_btn.setText(getResources().getString(R.string.submit));
                } else {
                    if (!UiHelp.isFastClick()) {
                        SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, aVoid.getMsg());
                    }
                }
            }
        }
    }

    // 提交资料成功后弹框提示，并跳转至我的投融资界面中我上传的项目
    private void tipDialog() {
        dialog = new AlertDialog.Builder(WantRoadShowActivity.this, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(true);
        dialog_title = (TextView) view.findViewById(R.id.msg);
        submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                intent = new Intent(WantRoadShowActivity.this, MyFinacingInvestActivity.class);
                intent.putExtra("flag", "flag");
                startActivity(intent);
                finish();
            }
        });
    }

    // 获取七牛Token
    private class TokenTask extends AsyncTask<Void, Void, AuthenticationBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthenticationBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(WantRoadShowActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("key", uuid, Constant.BASE_URL + Constant.PHONE + Constant.TOKEN, WantRoadShowActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("七牛返回值", body);
                return FastJsonTools.getBean(body, AuthenticationBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthenticationBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, R.string.no_wifi);
            } else {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    ll_video_list.get(0).setVisibility(View.GONE);
                    ll_video_list.get(1).setVisibility(View.VISIBLE);
                    token = aVoid.getData().getToken();
                    SharePreferencesUtils.saveToken(WantRoadShowActivity.this, token);
                    Log.i("视频文件名", uuid);
                    upload(file_video, uuid, token);
                } else {
                    SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, aVoid.getMsg());
                }
            }
        }
    }

    // 显示上传视频的进度
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                numberProgressBar.setProgress((int) progress);
            } else if (msg.what == 2) {
                numberProgressBar.setProgress(100);
//                SDCardUtils.getVideoThumbnail(file_video);
                ll_video_list.get(2).setVisibility(View.VISIBLE);
                ll_video_list.get(1).setVisibility(View.GONE);
                rl_delete.setVisibility(View.VISIBLE);
            }
        }
    };

    // 上传视频到七牛
    private void upload(String data, String key, String token) {
        UploadManager uploadManager = new UploadManager();
//        data = <File���󡢻� �ļ�·������ �ֽ�����>
//        String key = <ָ����ţ�����ϵ��ļ������� null>;
//        String token = <�ӷ����SDK��ȡ>;
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        paths = uuid;
                        handler.sendEmptyMessage(2);
                    }
                }, new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        Log.i("qiniu", key + ": " + percent);
                        handler.sendEmptyMessage(1);
                        progress = percent * 100;
                    }
                }, null));
    }

    // 录制视频
    private class GetTokenTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(WantRoadShowActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOKEN, WantRoadShowActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("七牛", body);
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
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    File mydir = null;
                    mydir = new File(file_folder);
                    if (!mydir.exists()) {
                        mydir.mkdir();
                        if (!mydir.mkdir()) {
                            mydir.mkdir();
                            Log.i("创建目录", "Cannot create the dir!");
                        }
                    }
                    if (mydir.exists()) {
                        Intent intent = new Intent();
                        intent.setAction("android.media.action.VIDEO_CAPTURE");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//����0.5
                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 70000);//70s
                        File file = new File(file_video);
                        if (file.exists()) {
                            file.delete();
                        }
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 0);
                    }
                } else {
                    SuperToastUtils.showSuperToast(WantRoadShowActivity.this, 1, aVoid.getMsg());
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
        }
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
}