package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;

import butterknife.OnClick;

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
 * ��������ҳ�棬��¼�ɹ�����ת����ҳ
 *
 * @auther Mr.Jobs
 * @date 2015/7/7
 * @time 10:19
 */

public class ForgetPasswdActivity extends BaseActivity {
    private Intent intent;
    List<String> list = new ArrayList<>();

    @Bind(R.id.back)
    LinearLayout back;
    private int timer_s = 60;

    private boolean isloop = true;
    private final int TIMERS = 1;
    private final int TIMES_AGAIN = 2;
    private final int TIMER_CANCEL = 4;
    Timer timer = new Timer();
    @Bind(R.id.title)
    TextView title;

    @OnClick(R.id.back)
    void finsh() {
        finish();
    }

    @Bind(R.id.codes_btn)
    Button codes_btn;

    // ��ȡ��֤��
    @OnClick(R.id.codes_btn)// 获取验证码
    void codes_btn() {
        if (UiHelp.isLongFastClick()) {
            return;
        }
        if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.telephone_hint);
        } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.number_wrong);
        } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.number_wrong);
        } else {
            timer = new Timer();
            timerTask();
            SendCodeTask task = new SendCodeTask();
            task.execute();
        }
    }

    @OnClick(R.id.register)// 找回密码
    void register() {//ע��
        if (UiHelp.isLongFastClick()) {
            return;
        }
        if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.telephone_hint);
        } else if (StringUtils.isBlank(editTextList.get(1).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.forget_passwd_hint);
        } else if (StringUtils.length(editTextList.get(0).getText().toString()) != 11) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.number_wrong);
        } else if (!UiHelp.IsNumber(editTextList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.number_wrong);
        } else if (StringUtils.isBlank(editTextList.get(2).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.forget_passwd_second_hint);
        } else if (StringUtils.isBlank(editTextList.get(3).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.code_hint);
        } else if (!editTextList.get(1).getText().toString().equals(editTextList.get(2).getText().toString())) {
            SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, R.string.passwd_different);
        } else {
            ForgetTask registerTask2 = new ForgetTask();
            registerTask2.execute();
        }
    }

    @Bind({R.id.telephone, R.id.first_passwd, R.id.second_passwd, R.id.codes})
    List<EditText> editTextList;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_forget_passwd;
    }

    @Override
    protected void init() {

        RippleUtils.rippleNormal(back);
        title.setText(mContext.getResources().getString(R.string.get_passwd));
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

    // 发送验证码
    private class SendCodeTask extends AsyncTask<Void, Void, CommonBean> {

        // ������ʱ�������粢�������ݵ�CommonBean
        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doSessionPost("tel", editTextList.get(0).getText().toString(), "sign", MD5Utils.md5("jinzht_verify"), "version", UiHelp.getVersionName(ForgetPasswdActivity.this), Constant.BASE_URL + Constant.PHONE + Constant.SENDCODE + "1/" + "0/", mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("验证码返回信息", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        // �ٴη�����֤��
        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                return;
            } else {
                if (common.getCode() == 0) {
                } else {
                    handler.sendEmptyMessage(TIMES_AGAIN);
                }
                SuperToastUtils.showSuperToast(ForgetPasswdActivity.this, 1, common.getMsg());
            }
        }
    }

    // 登录，跳到MainActivity
    private class ForgetTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        // ������ʱ�������粢�������ݵ�CommonBean
        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    Log.i("获取Session", SharePreferencesUtils.getSession(mContext));
                    md5 = MD5Utils.md5(editTextList.get(1).getText().toString() + editTextList.get(0).getText().toString().trim() + "lindyang");
                    body = OkHttpUtils.doSessionPost("tel", editTextList.get(0).getText().toString(), "passwd", md5, "code",
                            editTextList.get(3).getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.RESET, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        // ��ת����ҳ
        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                dismissProgressDialog();
            } else {
                dismissProgressDialog();
                if (common.getCode() == 0) {
                    SharePreferencesUtils.setIsLogin(mContext, true);
                    String msd5 = MD5Utils.md5(editTextList.get(1).getText().toString() + editTextList.get(0).getText().toString().trim() + "lindyang");
                    SharePreferencesUtils.saveInformation(mContext, editTextList.get(0).getText().toString(), msd5);
                    intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                }
                UiHelp.printMsg(common.getCode(), common.getMsg(), mContext);
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
}