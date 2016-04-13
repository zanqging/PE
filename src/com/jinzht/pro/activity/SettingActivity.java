package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.discreteseekbar.DiscreteSeekBar;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.togglebutton.ToggleButton;
import com.jinzht.pro.utils.DisplayUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.SuperToastUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * 设置界面
 *
 * @auther Mr.Jobs
 * @date 2015/5/20
 * @time 11:30
 */

public class SettingActivity extends BaseActivity {

    private Intent intent;
    private TextView tv_setting_ok;
    private DiscreteSeekBar night_seek_bar;// 亮度条

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.push_btn)
    ToggleButton materialAnimatedSwitch;// 新消息推送
    @Bind(R.id.night_model)
    ToggleButton night_model;// 夜间模式

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @OnClick(R.id.exit_login)// 注销登录
    void exit() {
        ExitTask exitTask = new ExitTask();
        exitTask.execute();
    }

    @OnClick({R.id.ll_free_back, R.id.ll_privacy_policy})
    void line(LinearLayout linearLayout) {
        switch (linearLayout.getId()) {
            case R.id.ll_free_back:// 在线反馈
                intent = new Intent(mContext, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_privacy_policy:// 隐私政策
                intent = new Intent(mContext, AboutDetailActivity.class);
                intent.putExtra("title", getResources().getString(R.string.privacy_policy));
                intent.putExtra("flag", 4);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.setting));
        if (SharePreferencesUtils.getLightModel(mContext)) {
            night_model.setToggleOn();
            Log.i(TAG, "on");
        } else {
            night_model.setToggleOff();
            Log.i(TAG, "off");
        }
        night_model.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    if (!DisplayUtils.isAutoBrightness(SettingActivity.this)) {
                        DisplayUtils.startAutoBrightness(SettingActivity.this);
                    }
                    SharePreferencesUtils.setLightModel(SettingActivity.this, true);
                    Log.i(TAG, SharePreferencesUtils.getLightModel(mContext) + "");
                    night_model.setToggleOn();
                    Log.i(TAG, "on");
                } else {
                    night_model.setToggleOff();
                    SharePreferencesUtils.setLightModel(SettingActivity.this, false);
                    Log.i(TAG, SharePreferencesUtils.getLightModel(mContext) + "");
                    Log.i(TAG, "off");
                }
            }
        });
    }

    public void changeAppBrightness(int brightness) {
        Window window = SettingActivity.this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 100f;
        }
        window.setAttributes(lp);
    }

    // 注销登录
    private class ExitTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.EXITLOGIN, mContext);
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SuperToastUtils.showSuperToast(mContext, 1, R.string.timeout);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("退出登录的返回信息", body);
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
                    SharePreferencesUtils.setIsLogin(mContext, false);
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    SuperToastUtils.showSuperToast(SettingActivity.this, 1, aVoid.getMsg());
                    dismissProgressDialog();
                }
            }
        }
    }

    private void choiceSysLight() {
        /**Unable to add window -- token null is not for an application,���������activity�����ģ�context�ǲ����Ե�*/
        AlertDialog dialog = new AlertDialog.Builder(SettingActivity.this, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_setting_night);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //??????
        tv_setting_ok = (TextView) view.findViewById(R.id.tv_setting_ok);
        night_seek_bar = (DiscreteSeekBar) view.findViewById(R.id.discrete1);
        if (DisplayUtils.isAutoBrightness(SettingActivity.this)) {
            DisplayUtils.stopAutoBrightness(SettingActivity.this);
        }
        if (DisplayUtils.getScreenBrightness(SettingActivity.this) < 0) {
            SuperToastUtils.showSuperToast(SettingActivity.this, 1, R.string.get_light_fail);
        } else {
            Log.i(TAG, DisplayUtils.getScreenBrightness(SettingActivity.this) + "getpro");
            night_seek_bar.setProgress(DisplayUtils.getScreenBrightness(SettingActivity.this));
        }
        night_seek_bar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                changeAppBrightness(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                changeAppBrightness(seekBar.getProgress());
            }
        });
    }

    @Override
    public void successRefresh() {
    }

    @Override
    public void errorPage() {
    }

    @Override
    public void blankPage() {
    }
}