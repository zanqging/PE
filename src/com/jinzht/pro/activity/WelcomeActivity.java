package com.jinzht.pro.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.utils.JpushUitls;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 * <p>
 * 应用入口。注册用于极光推送的广播接收者。停留一秒。
 * 首次打开应用进入引导页GuideActivity；非首次打开时判断是否登录，已登录进入主页MainActivity，否则进入登录页LoginActivity。
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 0:58
 */
public class WelcomeActivity extends FullBaseActivity {


    public static boolean isForeground = false;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private Intent intent;

    @Override
    protected int getResourcesId() {
        return R.layout.acitivity_welcome;
    }

    Handler hd = new Handler() {
        public void handleMessage(Message msg) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void init() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        registerMessageReceiver();// 注册信息广播接收者
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
        MobclickAgent.updateOnlineConfig(mContext);// 友盟
        Log.i("tag'", JPushInterface.getRegistrationID(WelcomeActivity.this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2500);// 延迟2.5秒
                if (!SharePreferencesUtils.getIsfirst(mContext)) {
                    //是第一次，进入引导页GuideActivity
                    intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // TODO: 2016/3/28 这里加入了是否有网的判断，因为把本地缓存取消了~~
                    if (SharePreferencesUtils.getIsLogin(mContext) && !NetWorkUtils.getNetWorkType(WelcomeActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
//                    if (SharePreferencesUtils.getIsLogin(mContext)) {
                        // 非第一次，已登录，进入主页MainActivity
                        intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 非第一次，没有登录，进入登录页
                        intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }).start();
    }

    private MessageReceiver mMessageReceiver;// 自定义信息广播接收者，用于极光推送
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    // 注册信息广播接收者
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
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

    // 自定义信息广播接收者，用于极光推送
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!JpushUitls.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }


}