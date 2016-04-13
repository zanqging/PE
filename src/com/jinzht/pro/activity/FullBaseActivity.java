package com.jinzht.pro.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.callback.ErrorException;
import com.jinzht.pro.callback.LoginAgainCallBack;
import com.jinzht.pro.callback.ProgressBarCallBack;
import com.jinzht.pro.model.LoginBean;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpException;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.LoadingProssbar;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * 加载进度条、分享、极光推送、友盟、ButterKnife；全屏、无标题、竖屏、透明状态栏的activity；public的未被调用的异步登录任务，登录失败则跳转至登录页面LoginActivity
 *
 * @auther Mr Jobs
 * @date 2015/11/18
 * @time 21:54
 */
public abstract class FullBaseActivity extends Activity implements ProgressBarCallBack, LoginAgainCallBack, ErrorException {

    public String TAG;// 当前activity的tag
    public Context mContext;
    LoadingProssbar dialog;// 加载进度条
    OkHttpException okHttpException = new OkHttpException(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);// 分享
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消应用标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 转场动画
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
        setContentView(getResourcesId());
//        MyApplication.getInstance().addActivity(this);
        JPushInterface.init(getApplicationContext());// 极光推送
        ButterKnife.bind(this);// 替代findviewbyid
        mContext = getApplicationContext();
        TAG = getRunningActivityName();
        MobclickAgent.setSessionContinueMillis(30000l);// 友盟
        UiHelp.setTranslucentStatus(true, this);// 透明状态栏
        init();
    }

    protected abstract int getResourcesId();

    protected abstract void init();

    // 正在运行的activity名字
    private String getRunningActivityName() {
        //ȱ��serview�޷����
        String contextString = this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }


    @Override
    public void onPause() {
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
        dismissProgressDialog();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    // 异步登录任务
    public class LoginTask extends AsyncTask<Void, Void, LoginBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 网络断开时，返回null；其他有网情况在后台从服务端获取用户数据并解析保存到LoginBean
        @Override
        protected LoginBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = AsynOkUtils.doNewLoginPost("tel", SharePreferencesUtils.getTelephone(mContext),
                            "passwd", SharePreferencesUtils.getPassWd(mContext), "regid", JPushInterface.getRegistrationID(mContext), "version", getResources().getString(R.string.verson_name),
                            Constant.BASE_URL + Constant.PHONE + Constant.LOGIN, mContext);
                    Log.e("Main", body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, LoginBean.class);
            } else {
                return null;
            }
        }

        // 从后台拿到用户数据后登录并保存相关数据，登录失败则跳转至登录界面
        @Override
        protected void onPostExecute(LoginBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                return;
            } else {
                if (aVoid.getCode() == 0) {
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
                    SharePreferencesUtils.setIsLogin(mContext, true);
//                    SharePreferencesUtils.saveInformation(MainActivity.this, login_edit.get(0).getText().toString(), md51);
                    SharePreferencesUtils.setPerfectInformation(mContext, aVoid.getData().getInfo());
                    SharePreferencesUtils.setAuth(mContext, aVoid.getData().getAuth() + "");
                    successRefresh();
                } else if (aVoid.getCode() == -1) {// 登录失败，跳转至登录页面
                    SharePreferencesUtils.setIsLogin(mContext, false);
                    loginAgain();
                }
            }
        }
    }

    @Override
    public void successRefresh() {

    }

    // 再次登，跳转至LoginActivity
    private void loginAgain() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        ShareSDK.stopSDK(this);
    }

    @Override
    public void showLoadingProgressDialog() {
        this.showProgressDialog(getResources().getString(R.string.loading));
    }

    // 显示加载进度
    @Override
    public void showProgressDialog(String message) {
        if (dialog == null) {
            dialog = new LoadingProssbar(this, message, R.anim.loading_anim);
            dialog.show();
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (this.dialog != null) {
            dialog.dismiss();
        }
    }
}
