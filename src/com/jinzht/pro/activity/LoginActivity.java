package com.jinzht.pro.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.LoginBean;
import com.jinzht.pro.model.WechatCodeBean;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.MD5Utils;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.InputMethodRelativeLayout;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * 登录页面，包括登录、注册、忘记密码、微信登录。
 *
 * @auther Mr.Jobs
 * @date 2015/5/16
 * @time 14:17
 */

public class LoginActivity extends BaseActivity implements InputMethodRelativeLayout.OnSizeChangedListenner, Handler.Callback,
        View.OnClickListener, PlatformActionListener {
    private Intent intent;
    private LoginTask loginTask;
    private static final int MSG_USERID_FOUND = 1;// 用户信息已存在
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    @Bind(R.id.scrollview)
    ScrollView scrollview;
    @Bind(R.id.keyboardRelativeLayout)
    InputMethodRelativeLayout inputMethodRelativeLayout;// 自定义的最外层控件
    @Bind({R.id.login_name, R.id.login_passwd})
    List<EditText> login_edit;

    // 跳转至忘记密码页面
    @OnClick(R.id.forget_passwd)
    void forget() {
        intent = new Intent(LoginActivity.this, ForgetPasswdActivity.class);
        startActivity(intent);
    }

    // 微信登录
    @OnClick(R.id.tv_wechat)
    void ss() {
        authorize(new Wechat(this));
//        authorize(new QQ(this));

    }

    @Bind(R.id.login_btn)
    Button login_btn;

    // 点击登录
    @OnClick({R.id.login_btn})
    void login(Button button) {
        switch (button.getId()) {
            case R.id.login_btn:
                if (UiHelp.isLongFastClick()) {
                    return;
                }
                if (StringUtils.isBlank(login_edit.get(0).getText().toString())) {
                    SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.user_null);
                } else if (StringUtils.isBlank(login_edit.get(1).getText().toString())) {
                    SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.passwd_null);
                } else if (!UiHelp.IsNumber(login_edit.get(0).getText().toString().trim())) {
                    SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.number_wrong);
                } else if (login_edit.get(0).getText().toString().trim().length() != 11) {
                    SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.number_wrong);
                } else {
                    loginTask = new LoginTask();
                    loginTask.execute();
                    SharePreferencesUtils.setIsLogin(mContext, true);
                }
                break;
        }
    }

    // 点击没有账号，跳转至注册页面
    @OnClick({R.id.new_user})
    void new_user() {
        intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_login;
    }

    // 微信授权登录
    private void authorize(Platform plat) {
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                logins(plat, userId, null);
                return;
            }
        }
        //若本地没有授权过就请求用户数据
        plat.setPlatformActionListener(this);
        plat.SSOSetting(true);//此处设置为false，则在优先采用客户端授权的方法，设置true会采用网页方式
        plat.showUser(null);//获得用户数据
    }

    // 发送登录信息
    private void logins(Platform plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    // 保存登录状态
    @Override
    protected void init() {
        inputMethodRelativeLayout.setOnSizeChangedListenner(this);
        if ((!SharePreferencesUtils.getIsLogin(mContext)) && (!SharePreferencesUtils.getTelephone(mContext).equals(""))) {
            login_edit.get(0).setText(SharePreferencesUtils.getTelephone(mContext));
        }

        SharePreferencesUtils.setIsLogin(mContext, false);
    }

    @Override
    public void onSizeChange(boolean paramBoolean, int w, int h) {
        if (paramBoolean) {
            //???
        } else {
            scrollview.fullScroll(View.FOCUS_DOWN);// 滚动到最底部后消费掉触摸事件
            scrollview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
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

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {
//                String text = getString(R.string.logining, msg.obj);
                System.out.println("---------------");
                Platform platform = (Platform) msg.obj;
//                String  id=res.get("id").toString();//ID
//                Toast.makeText(this, platform.getDb().getUserName()+"name"+"id"+platform.getDb().getUserId()+"url"+platform.getDb().getUserIcon(), Toast.LENGTH_SHORT).show();
//				Builder builder = new Builder(this);
//				builder.setTitle(R.string.if_register_needed);
//				builder.setMessage(R.string.after_auth);
//				builder.setPositiveButton(R.string.ok, null);
//				builder.create().show();
                OpenIdTask openIdTask = new OpenIdTask(platform);
                openIdTask.execute();
//                startActivity(intent);
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_CANCEL--------");
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_ERROR--------");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
                System.out.println("--------MSG_AUTH_COMPLETE-------");
            }
            break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            logins(platform, platform.getDb().getUserId(), res);
            Log.i("微信授权成功", "name:" + platform.getName() + platform.getDb().getUserId() + "icon" + platform.getDb().getUserIcon() + "res" + res);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(LoginActivity.this, platform.getDb().getUserName(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        System.out.println(res);
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    // 点击登录执行，
    private class LoginTask extends AsyncTask<Void, Void, LoginBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        // 网络断开时，返回null；其他有网情况在后台从服务端获取用户数据并解析保存到LoginBean
        @Override
        protected LoginBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    md5 = MD5Utils.md5(login_edit.get(1).getText().toString() + login_edit.get(0).getText().toString().trim() + "lindyang");
                    Log.i("登录接口请求参数", login_edit.get(1).getText().toString() + login_edit.get(0).getText().toString().trim() + "lindyang");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.i("极光推送注册ID", JPushInterface.getRegistrationID(LoginActivity.this));
                    body = AsynOkUtils.doNewLoginPost("tel", login_edit.get(0).getText().toString().trim(),
                            "passwd", md5, "regid", JPushInterface.getRegistrationID(LoginActivity.this), "version", getResources().getString(R.string.verson_name),
                            Constant.BASE_URL + Constant.PHONE + Constant.LOGIN, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("登录信息", body);
                return FastJsonTools.getBean(body, LoginBean.class);
            } else {
                return null;
            }
        }

        // 从后台拿到用户数据后登录并保存相关数据
        @Override
        protected void onPostExecute(LoginBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                dismissProgressDialog();
                SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.no_wifi);
                return;
            } else {
                if (aVoid.getCode() == 0) {

                    if (!aVoid.getMsg().equals("")) {
                        SuperToastUtils.showSuperToast(LoginActivity.this, 1, aVoid.getMsg());
                    }
                    dismissProgressDialog();
                    String md51 = MD5Utils.md5(login_edit.get(1).getText().toString() + login_edit.get(0).getText().toString().trim() + "lindyang");
                    SharePreferencesUtils.setIsLogin(mContext, true);
                    SharePreferencesUtils.saveInformation(mContext, login_edit.get(0).getText().toString(), md51);
                    SharePreferencesUtils.setPerfectInformation(mContext, aVoid.getData().getInfo());
                    SharePreferencesUtils.setAuth(mContext, aVoid.getData().getAuth() + "");
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    SharePreferencesUtils.setIsLogin(mContext, false);
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
                    dismissProgressDialog();
                }
            }
        }
    }

    // 微信授权
    private class OpenIdTask extends AsyncTask<Void, Void, WechatCodeBean> {
        private Platform platform;

        public OpenIdTask(Platform platform) {
            this.platform = platform;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        // 网络断开时，返回null；其他有网情况在后台从服务端获取用户数据并解析保存到LoginBean
        @Override
        protected WechatCodeBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("openid", platform.getDb().getUserId(),
                            Constant.BASE_URL + Constant.PHONE + Constant.OPEN_ID, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("微信授权信息", body);
                return FastJsonTools.getBean(body, WechatCodeBean.class);
            } else {
                return null;
            }
        }

        // 跳转至于微信绑定页面WeChatInformationActivity
        @Override
        protected void onPostExecute(WechatCodeBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common == null) {
                SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.no_wifi);
                return;
            }
            if (common.getCode() == -1) {
                return;
            }
            if (common.getCode() == 0) {

                if (common.getData().isFlag()) {
                    /**true������*/
                    WechatTask wechatTask = new WechatTask(platform);
                    wechatTask.execute();
                } else {
                    intent = new Intent(mContext, WeChatInformationActivity.class);
                    intent.putExtra("id", platform.getDb().getUserId());
                    intent.putExtra("name", platform.getDb().getUserName());
                    intent.putExtra("photo", platform.getDb().getUserIcon());
                    startActivity(intent);

                }
            }
        }
    }

    // 微信登录
    private class WechatTask extends AsyncTask<Void, Void, LoginBean> {

        private Platform platform;

        public WechatTask(Platform platform) {
            this.platform = platform;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        // 网络断开时，返回null；其他有网情况在后台从服务端获取用户数据并解析保存到LoginBean
        @Override
        protected LoginBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    Log.i("极光推送注册ID", JPushInterface.getRegistrationID(LoginActivity.this));
                    body = AsynOkUtils.doWechatLoginPost("regid", JPushInterface.getRegistrationID(LoginActivity.this), "openid", platform.getDb().getUserId(),
                            Constant.BASE_URL + Constant.PHONE + Constant.LOGIN, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("登录信息", body);
                return FastJsonTools.getBean(body, LoginBean.class);
            } else {
                return null;
            }
        }

        // 从后台拿到用户数据后登录并保存相关数据，跳转至主页面MainActivity
        @Override
        protected void onPostExecute(LoginBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                dismissProgressDialog();
                SuperToastUtils.showSuperToast(LoginActivity.this, 1, R.string.no_wifi);
                return;
            } else {
                if (aVoid.getCode() == 0) {
                    if (!aVoid.getMsg().equals("")) {
                        SuperToastUtils.showSuperToast(LoginActivity.this, 1, aVoid.getMsg());
                    }
                    dismissProgressDialog();
                    String md51 = MD5Utils.md5(login_edit.get(1).getText().toString() + login_edit.get(0).getText().toString().trim() + "lindyang");
                    SharePreferencesUtils.setIsLogin(mContext, true);
                    SharePreferencesUtils.saveInformation(mContext, login_edit.get(0).getText().toString(), md51);
                    SharePreferencesUtils.setPerfectInformation(mContext, aVoid.getData().getInfo());
                    SharePreferencesUtils.setAuth(mContext, aVoid.getData().getAuth() + "");
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
                    dismissProgressDialog();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}