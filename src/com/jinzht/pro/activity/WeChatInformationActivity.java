package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.LoginBean;
import com.jinzht.pro.model.WechatCodeBean;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.utils.*;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * ΢�Ű��û���Ϣ���棬�ɹ�����ת����ҳMainActivity
 *
 * @auther Mr Jobs
 * @date 2015/11/14
 * @time 13:09
 */
public class WeChatInformationActivity extends BaseActivity {
    private Intent intent;
    List<String> list = new ArrayList<>();
    private int timer_s = 60;
    private boolean isloop = true;
    private final int TIMERS = 1;
    private final int TIMES_AGAIN = 2;
    private final int TIMER_CANCEL = 4;
    private boolean is_auth = false;
    Timer timer = new Timer();
    @Bind({R.id.ll_one, R.id.ll_two})
    List<LinearLayout> linearLayouts;
    @Bind({R.id.telephone, R.id.first_passwd, R.id.second_passwd, R.id.codes})
    List<EditText> editTextList;
    @Bind(R.id.title)
    TextView title;

    @OnClick(R.id.back)
    void finsh() {
        finish();
    }

    @Bind(R.id.codes_btn)
    Button codes_btn;
    @Bind(R.id.user_iamge)
    PolygonImageView user_iamge;

    // 获取验证码
    @OnClick(R.id.codes_btn)
    void codes_btn() {
        if (!UiHelp.isLongFastClick()) {
            if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.telephone_hint);
            } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.number_wrong);
            } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.number_wrong);
            } else {
                timer = new Timer();
                timerTask();
                RegisterTask task = new RegisterTask();
                task.execute();
            }
        }
    }

    // 确认绑定
    @OnClick(R.id.register)
    void register() {
        if (UiHelp.isLongFastClick()) {
            return;
        }
        if (is_auth) {
            if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.telephone_hint);
            } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.number_wrong);
            } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.number_wrong);
            } else if (StringUtils.isBlank(editTextList.get(3).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.code_hint);
            } else if (!editTextList.get(1).getText().toString().equals(editTextList.get(2).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.passwd_different);
            } else {
                RegisterTask2 registerTask2 = new RegisterTask2();
                registerTask2.execute();
            }
        } else {
            if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.telephone_hint);
            } else if (StringUtils.isBlank(editTextList.get(1).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.login_passwd_hint);
            } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.number_wrong);
            } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.number_wrong);
            } else if (StringUtils.isBlank(editTextList.get(2).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.login_second_passwd_hint);
            } else if (StringUtils.isBlank(editTextList.get(3).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.code_hint);
            } else if (!editTextList.get(1).getText().toString().equals(editTextList.get(2).getText().toString())) {
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.passwd_different);
            } else {
                RegisterTask2 registerTask2 = new RegisterTask2();
                registerTask2.execute();
            }
        }


    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_wechat_information;
    }

    @Override
    protected void init() {
        linearLayouts.get(0).setVisibility(View.GONE);
        linearLayouts.get(1).setVisibility(View.GONE);
        title.setText(mContext.getResources().getString(R.string.register_num));
        UpLoadImageUtils.getImage(getIntent().getStringExtra("photo"), user_iamge);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMERS:
                    //��ֹ���
//                    timer_s--;
                    codes_btn.setText(timer_s + getResources().getString(R.string.get_codeagain));
                    codes_btn.setTextColor(getResources().getColor(R.color.gray1));
                    codes_btn.setBackgroundResource(R.drawable.gray_stroke_btn);
                    codes_btn.setEnabled(false);
                    break;
                case TIMES_AGAIN:
                    timer_s = 60;
                    isloop = false;
                    timer.cancel();
                    codes_btn.setText(getResources().getString(R.string.getcode));
                    codes_btn.setTextColor(getResources().getColor(R.color.login_btn));
                    codes_btn.setBackgroundResource(R.drawable.login_btn);
                    codes_btn.setEnabled(true);
                    break;
                case TIMER_CANCEL:
                    timer_s = 60;
                    isloop = false;
                    timer.cancel();
                    codes_btn.setText(getResources().getString(R.string.getcode));
                    codes_btn.setTextColor(getResources().getColor(R.color.login_btn));
                    codes_btn.setBackgroundResource(R.drawable.login_btn);
                    codes_btn.setEnabled(true);
                    break;
            }
        }
    };

    // 倒计时
    private void timerTask() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timer_s != 0) {
                    //��handler���͸�����Ϣ��Message message = new Message(); messge.what = 1; message.sendmessage(message);
                    handler.sendEmptyMessage(TIMERS);
                } else {
                    //����ֹͣ��Ϣ
                    handler.sendEmptyMessage(TIMES_AGAIN);
                }
                timer_s--;
            }
        }, 1000, 1000);
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    @Override
    public void successRefresh() {
        RegisterTask2 registerTask2 = new RegisterTask2();
        registerTask2.execute();
    }

    // 获取验证码
    private class RegisterTask extends AsyncTask<Void, Void, WechatCodeBean> {

        @Override
        protected WechatCodeBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doSessionPost("tel", editTextList.get(0).getText().toString(), "openid", getIntent().getStringExtra("id"), "sign", MD5Utils.md5("jinzht_verify"), "version", UiHelp.getVersionName(WeChatInformationActivity.this),
                            Constant.BASE_URL + Constant.PHONE + Constant.SENDCODE + "0/" + "1/", mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("微信绑定验证码", body);
                return FastJsonTools.getBean(body, WechatCodeBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(WechatCodeBean common) {
            super.onPostExecute(common);
            if (common == null) {
                return;
            } else {
                if (common.getCode() == 0) {
                    if (common.getData().isFlag()) {
                        /**ȫ�µ�*/
                        linearLayouts.get(0).setVisibility(View.VISIBLE);
                        linearLayouts.get(1).setVisibility(View.VISIBLE);
                        is_auth = false;
                    } else {
                        /**�Ѿ�ע����������ڰ��ֻ�*/
                        linearLayouts.get(0).setVisibility(View.GONE);
                        linearLayouts.get(1).setVisibility(View.GONE);
                        is_auth = true;
                    }
                    SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, common.getMsg());
                } else {
                    handler.sendEmptyMessage(TIMES_AGAIN);
                    UiHelp.printMsg(common.getCode(), common.getMsg(), mContext);
                }
            }
        }
    }

    // 绑定后跳转至MainActivity
    private class RegisterTask2 extends AsyncTask<Void, Void, LoginBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected LoginBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    /**2����˼����android����������������ô��*/
                    if (linearLayouts.get(0).isShown()) {
                        md5 = MD5Utils.md5(editTextList.get(1).getText().toString() + editTextList.get(0).getText().toString().trim() + "lindyang");
                        body = AsynOkUtils.dowechatLogin("tel", editTextList.get(0).getText().toString(), "passwd", md5, "code", editTextList.get(3).getText().toString(), "regid",
                                JPushInterface.getRegistrationID(WeChatInformationActivity.this), "openid", getIntent().getStringExtra("id"), "nickname", getIntent().getStringExtra("name"),
                                ImageLoader.getInstance().getDiskCache().get(getIntent().getStringExtra("photo")).getPath(),
                                Constant.BASE_URL + Constant.PHONE + Constant.REGISTER + "2/", mContext);
                    } else {
                        Log.i("头像", getIntent().getStringExtra("photo"));
                        body = AsynOkUtils.dowechatLogin("tel", editTextList.get(0).getText().toString(), "code", editTextList.get(3).getText().toString(), "regid",
                                JPushInterface.getRegistrationID(WeChatInformationActivity.this), "openid", getIntent().getStringExtra("id"), "nickname", getIntent().getStringExtra("name"),
                                ImageLoader.getInstance().getDiskCache().get(getIntent().getStringExtra("photo")).getPath(),
                                Constant.BASE_URL + Constant.PHONE + Constant.REGISTER + "2/", mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("登录信息", body);
                return FastJsonTools.getBean(body, LoginBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(LoginBean common) {
            super.onPostExecute(common);
            if (common != null) {
                dismissProgressDialog();
                if (common.getCode() == 0) {
                    if (!common.getMsg().equals("")) {
                        SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, common.getMsg());
                    }
                    String md5s = MD5Utils.md5(editTextList.get(1).getText().toString() + editTextList.get(0).getText().toString().trim() + "lindyang");
                    SharePreferencesUtils.saveInformation(mContext, editTextList.get(0).getText().toString(), md5s);
                    SharePreferencesUtils.setPerfectInformation(mContext, common.getData().getInfo());
                    SharePreferencesUtils.setAuth(mContext, common.getData().getAuth() + "");
                    SharePreferencesUtils.setIsLogin(mContext, true);
                    intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (common.getCode() == -1) {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    UiHelp.printMsg(common.getCode(), common.getMsg(), mContext);
                    handler.sendEmptyMessage(4);
                    SharePreferencesUtils.setIsLogin(mContext, false);
                }
            } else {
                dismissProgressDialog();
                SharePreferencesUtils.setIsLogin(mContext, false);
                SuperToastUtils.showSuperToast(WeChatInformationActivity.this, 1, R.string.service_error);
            }
        }
    }
}