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
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * Ӧ����ڡ�ע�����ڼ������͵Ĺ㲥�����ߡ�ͣ��һ�롣
 * �״δ�Ӧ�ý�������ҳGuideActivity�����״δ�ʱ�ж��Ƿ��¼���ѵ�¼������ҳMainActivity����������¼ҳLoginActivity��
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 0:58
 */
public class WelcomeActivity extends FullBaseActivity {


    public static boolean isForeground = false;
    // IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
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
        // ͨ��WXAPIFactory��������ȡIWXAPI��ʵ��
        registerMessageReceiver();// ע����Ϣ�㲥������
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
        MobclickAgent.updateOnlineConfig(mContext);// ����
        Log.i("tag'", JPushInterface.getRegistrationID(WelcomeActivity.this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2500);// �ӳ�2.5��
                if (!SharePreferencesUtils.getIsfirst(mContext)) {
                    //�ǵ�һ�Σ���������ҳGuideActivity
                    intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // TODO: 2016/3/28 ����������Ƿ��������жϣ���Ϊ�ѱ��ػ���ȡ����~~
                    if (SharePreferencesUtils.getIsLogin(mContext) && !NetWorkUtils.getNetWorkType(WelcomeActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
//                    if (SharePreferencesUtils.getIsLogin(mContext)) {
                        // �ǵ�һ�Σ��ѵ�¼��������ҳMainActivity
                        intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // �ǵ�һ�Σ�û�е�¼�������¼ҳ
                        intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }).start();
    }

    private MessageReceiver mMessageReceiver;// �Զ�����Ϣ�㲥�����ߣ����ڼ�������
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    // ע����Ϣ�㲥������
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

    // �Զ�����Ϣ�㲥�����ߣ����ڼ�������
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