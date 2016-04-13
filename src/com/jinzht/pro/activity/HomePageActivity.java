package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.SuperToastUtils;

import java.util.HashMap;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 注册、登录、微信登录页面，貌似没有用到
 *
 * @auther Mr.Jobs
 * @date 2015/6/27
 * @time 16:32
 */

public class HomePageActivity extends BaseActivity implements PlatformActionListener {
    private Intent intent;

    //短信验证的对话框
    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;

    //    intent = new Intent(mContext,WeChatInformationActivity.class);
//    intent.putExtra("id",id);
//    intent.putExtra("name",name);
//    intent.putExtra("photo",profile_image_url);
//    startActivity(intent);
    @OnClick({R.id.register_num, R.id.login_btn, R.id.wechat_btn})
    void register(TextView textView) {
        switch (textView.getId()) {
            case R.id.register_num:
                intent = new Intent(HomePageActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.wechat_btn:
//                Log.e(TAG,"wechat");
//                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//                authorize(wechat);
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_AUTH_CANCEL:
                    //取消授权
                    Log.e(TAG, "cancle");
                    SuperToastUtils.showSuperToast(HomePageActivity.this, 1, "cancel");
                    break;
                case MSG_AUTH_ERROR:
                    //授权失败
                    Log.e(TAG, "fail");
                    SuperToastUtils.showSuperToast(HomePageActivity.this, 1, "fail");
                    break;
                case MSG_AUTH_COMPLETE:
                    //授权成功
                    Log.e(TAG, "susss");
                    Object[] objs = (Object[]) msg.obj;
                    String platform = (String) objs[0];
                    HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
                    String id = res.get("id").toString();//ID
                    String name = res.get("name").toString();//用户名
                    String description = res.get("description").toString();//描述
                    String profile_image_url = res.get("profile_image_url").toString();//头像链接
                    SuperToastUtils.showSuperToast(HomePageActivity.this, 1, name + description + profile_image_url);
                    Log.e(TAG, name + description + profile_image_url);
                    break;
                case MSG_SMSSDK_CALLBACK:
                    break;
            }
        }
    };

    @Override
    protected int getResourcesId() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void init() {
        if (SharePreferencesUtils.getIsLogin(mContext)) {
            //自动登录
            intent = new Intent(HomePageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //执行授权,获取用户信息
    //文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
    private void authorize(Platform plat) {
        if (plat == null) {
//            popupOthers();
            Log.e(TAG, "null,ihuh");
            return;
        }
        plat.setPlatformActionListener(this);
        //关闭SSO授权
        plat.SSOSetting(true);
        plat.showUser(null);//执行登录，登录后在回调里面获取用户资料
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @SuppressWarnings("unchecked")
//    public boolean handleMessage(Message msg) {
//        switch(msg.what) {
//            case MSG_AUTH_CANCEL:
//                //取消授权
//                Log.e(TAG,"cancle");
//                SuperToastUtils.showSuperToast(HomePageActivity.this, 1, "cancel");
//             break;
//            case MSG_AUTH_ERROR:
//                //授权失败
//                Log.e(TAG,"fail");
//                SuperToastUtils.showSuperToast(HomePageActivity.this, 1, "fail");
//             break;
//            case MSG_AUTH_COMPLETE:
//                //授权成功
//                Log.e(TAG, "susss");
//                Object[] objs = (Object[]) msg.obj;
//                String platform = (String) objs[0];
//                HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
//                String  id=res.get("id").toString();//ID
//                String name=res.get("name").toString();//用户名
//                String description=res.get("description").toString();//描述
//                String profile_image_url=res.get("profile_image_url").toString();//头像链接
//                SuperToastUtils.showSuperToast(HomePageActivity.this,1,name+description+profile_image_url);
//                intent = new Intent(mContext,WeChatInformationActivity.class);
//                intent.putExtra("id",id);
//                intent.putExtra("name",name);
//                intent.putExtra("photo",profile_image_url);
//                startActivity(intent);
//                Log.e(TAG,name+description+profile_image_url);
//             break;
//            case MSG_SMSSDK_CALLBACK:
//             break;
//        }
//        return false;
//    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            msg.obj = new Object[]{platform.getName(), res};
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);

        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
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