package com.jinzht.pro.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.jinzht.pro.R;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/11/27.
 */
public class ShareUtils implements PlatformActionListener,Handler.Callback {


    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    private Activity activity;

    public ShareUtils(Activity activity) {
        this.activity = activity;
    }

    public void wechat(String title,String context,String image_url,String url){
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(title);
        wechat.setText(context);
        wechat.setImageUrl(image_url);
        wechat.setUrl(url);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(activity, Wechat.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }
    public void wechatMement(String title,String context,String image_url,String url){
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(title);
        wechat.setText(context);
        wechat.setImageUrl(image_url);
        wechat.setUrl(url);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(activity, WechatMoments.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }
    public void qq(String title,String context,String image_url,String url){
        Platform.ShareParams sq = new Platform.ShareParams();
        sq.setTitle(title);
        sq.setText(context);
        sq.setImageUrl(image_url);
        sq.setTitleUrl(url);
        sq.setShareType(Platform.SHARE_WEBPAGE);
        Platform platform = ShareSDK.getPlatform(activity, QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.share(sq);
    }
    public void message(String title,String context,String image_url,String url){
        ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
//        sp.setTitle(title);
        sp.setAddress("");
        sp.setText(activity.getResources().getString(R.string.share_text) + url);
        Platform platform = ShareSDK.getPlatform(ShortMessage.NAME);
        platform.setPlatformActionListener(this);
        sp.setShareType(platform.SHARE_TEXT);
        platform.share(sp);
//        sp.setImageUrl(imageurl);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        // 失敗
        //打印错误信息,print the error msg
        t.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int action) {
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                SuperToastUtils.showSuperToast(activity, 1, R.string.share_success);
//                ShareNumTask shareNumTask = new ShareNumTask();
//                shareNumTask.execute();
            }
            break;
            case 2: {
                // 失败=
//            SuperToastUtils.showSuperToast(WantShareActivity.this, 1, msg.obj.toString());
                SuperToastUtils.showSuperToast(activity, 1, R.string.share_fail);
            }
            break;
            case 3: {
                // 取消
                SuperToastUtils.showSuperToast(activity, 1, R.string.share_cancel);
            }
            break;
        }

        return false;

    }
}
