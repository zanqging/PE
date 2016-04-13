package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * 注册页面，可跳转至用户协议页面NoticeActivity，主页MainActivity
 *
 * @auther Mr.Jobs
 * @date 2015/5/18
 * @time 11:56
 */

public class RegisterActivity extends BaseActivity {
    private Intent intent;
    List<String> list = new ArrayList<>();

    private int timer_s = 60;

    private boolean isloop = true;
    private final int TIMERS = 1;
    private final int TIMES_AGAIN = 2;
    private final int TIMER_CANCEL = 4;
    Timer timer = new Timer();
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.codes_btn)
    Button codes_btn;

    // 打开金指投用户协议页面NoticeActivity
    @OnClick(R.id.jzt_notice)
    void text() {
        intent = new Intent(mContext, NoticeActivity.class);
        intent.putExtra("flag", 1);
        intent.putExtra("title", getResources().getString(R.string.jzt_notice));
        startActivity(intent);
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    // 获取验证码
    @OnClick(R.id.codes_btn)
    void codes_btn() {
        if (!UiHelp.isLongFastClick()) {
            if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.telephone_hint);
            } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
                SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.number_wrong);
            } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
                SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.number_wrong);
            } else {
                timer = new Timer();
                timerTask();
                RegisterTask task = new RegisterTask();
                task.execute();
            }
        }
    }

    // 注册
    @OnClick(R.id.register)
    void register() {//ע��
        if (UiHelp.isLongFastClick()) {
            return;
        }
        if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.telephone_hint);
        } else if (StringUtils.isBlank(editTextList.get(1).getText().toString())) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.login_passwd_hint);
        } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.number_wrong);
        } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.number_wrong);
        } else if (StringUtils.isBlank(editTextList.get(2).getText().toString())) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.login_second_passwd_hint);
        } else if (StringUtils.isBlank(editTextList.get(3).getText().toString())) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.code_hint);
        } else if (!editTextList.get(1).getText().toString().equals(editTextList.get(2).getText().toString())) {
            SuperToastUtils.showSuperToast(RegisterActivity.this, 1, R.string.passwd_different);
        } else {
            RegisterTask2 registerTask2 = new RegisterTask2();
            registerTask2.execute();
        }
    }

    @Bind({R.id.telephone, R.id.first_passwd, R.id.second_passwd, R.id.codes})
    List<EditText> editTextList;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        title.setText(mContext.getResources().getString(R.string.register_num));
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

    // 获取验证码
    private class RegisterTask extends AsyncTask<Void, Void, CommonBean> {

        // 网络断开时，返回null；其他有网情况在后台从服务端获取用户数据并解析保存到CommonBean
        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doSessionPost("tel", editTextList.get(0).getText().toString(), "sign", MD5Utils.md5("jinzht_verify"), "version", UiHelp.getVersionName(RegisterActivity.this), Constant.BASE_URL + Constant.PHONE + Constant.SENDCODE + "0/" + "0/", mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("验证码信息", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        // 从后台拿到数据提示，获取验证码
        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                return;
            } else {
                if (common.getCode() == 0) {
                    SuperToastUtils.showSuperToast(RegisterActivity.this, 1, common.getMsg());
                } else {
                    handler.sendEmptyMessage(TIMES_AGAIN);
                    UiHelp.printMsg(common.getCode(), common.getMsg(), mContext);
                }
            }
        }
    }

    // 注册后跳转至主页MainActivity
    private class RegisterTask2 extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        // 网络断开时，返回null；其他有网情况在后台从服务端获取用户数据并解析保存到CommonBean
        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    md5 = MD5Utils.md5(editTextList.get(1).getText().toString() + editTextList.get(0).getText().toString().trim() + "lindyang");
                    body = AsynOkUtils.doLoginPost("tel", editTextList.get(0).getText().toString(), "passwd", md5, "code", editTextList.get(3).getText().toString(), "regid",
                            JPushInterface.getRegistrationID(RegisterActivity.this), "version", getResources().getString(R.string.verson_name),
                            Constant.BASE_URL + Constant.PHONE + Constant.REGISTER + "2/", mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("注册返回信息", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        // 从后台拿到用户数据后保存相关数据，并跳转到主页MainActivity
        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                dismissProgressDialog();
                return;
            } else {
                if (common.getCode() == 0) {
                    if (!common.getMsg().equals("")) {
                        SuperToastUtils.showSuperToast(RegisterActivity.this, 1, common.getMsg());
                    }
                    String md5s = MD5Utils.md5(editTextList.get(1).getText().toString() + editTextList.get(0).getText().toString().trim() + "lindyang");
                    SharePreferencesUtils.saveInformation(mContext, editTextList.get(0).getText().toString(), md5s);
                    SharePreferencesUtils.setIsLogin(mContext, true);
                    SharePreferencesUtils.setPerfectInformation(mContext, false);
                    SharePreferencesUtils.setAuth(mContext, "");
                    intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                } else {
                    UiHelp.printMsg(common.getCode(), common.getMsg(), mContext);
                    handler.sendEmptyMessage(4);
                    SharePreferencesUtils.setIsLogin(mContext, false);
                }
                dismissProgressDialog();
            }
        }
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
                    codes_btn.setTextColor(getResources().getColor(R.color.white));
                    codes_btn.setBackgroundResource(R.drawable.regisiter_code_gray_btn);
                    codes_btn.setEnabled(false);
                    break;
                case TIMES_AGAIN:
                    timer_s = 60;
                    isloop = false;
                    timer.cancel();
                    codes_btn.setText(getResources().getString(R.string.getcode));
                    codes_btn.setTextColor(getResources().getColor(R.color.white));
                    codes_btn.setBackgroundResource(R.drawable.register_code_btn);
                    codes_btn.setEnabled(true);
                    break;
                case TIMER_CANCEL:
                    timer_s = 60;
                    isloop = false;
                    timer.cancel();
                    codes_btn.setText(getResources().getString(R.string.getcode));
                    codes_btn.setTextColor(getResources().getColor(R.color.white));
                    codes_btn.setBackgroundResource(R.drawable.register_code_btn);
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
                    handler.sendEmptyMessage(TIMERS);
                } else {
                    handler.sendEmptyMessage(TIMES_AGAIN);
                }
                timer_s--;
            }
        }, 1000, 1000);
    }
}