package com.jinzht.pro.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.jinzht.pro.R;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/4/8.
 */
public class ShareSdkUtils implements PlatformActionListener {
    public String imageurl = "http://soft.cgcgcg.cn/statics/share2.png";
    private String title = "";
    private String text = "";
    private String url = "http://www.baidu.com";
    private Context context;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(context.getApplicationContext(), context.getString(R.string.share_success), Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(context.getApplicationContext(), context.getString(R.string.share_cancel), Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(context.getApplicationContext(), context.getString(R.string.share_error) + msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    };
    public ShareSdkUtils(final Context context){
        super();
        this.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                text = context.getString(R.string.share_text);
                title = context.getResources().getString(R.string.app_name);

            }
        }).start();
    }

    public void qq(){
        QQ.ShareParams sp = new QQ.ShareParams();
        /*一般就是这四个参数，一个不能少*/
        sp.setTitle(title);
        sp.setImageUrl(imageurl);
        sp.setText(text);
        sp.setTitleUrl(url);
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(this);
        platform.share(sp);
    }
    public void wechat(){
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        Wechat.ShareParams sp = new Wechat.ShareParams();

        sp.setShareType(platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setText(context.getString(R.string.share_text));
//        sp.setComment(context.getString(R.string.share_text)+url);
        sp.setImageUrl(imageurl);
        sp.setUrl(url);

        platform.setPlatformActionListener(this);
        platform.share(sp);
    }
    public void wechatMement(){
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setTitle(title);
        sp.setText(context.getString(R.string.share_text));
        sp.setImageUrl("http://soft.cgcgcg.cn/statics/share2.png");
        sp.setUrl(url);
        sp.setComment(text);
        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
        platform.setPlatformActionListener(this);
        sp.setShareType(platform.SHARE_WEBPAGE);
        platform.share(sp);
    }
    public void message(){
        ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
//        sp.setTitle(title);
        sp.setAddress("");
        sp.setText(context.getString(R.string.share_text)+url);
        Platform platform = ShareSDK.getPlatform(ShortMessage.NAME);
        platform.setPlatformActionListener(this);
        sp.setShareType(platform.SHARE_TEXT);
        platform.share(sp);
//        sp.setImageUrl(imageurl);
    }
//    public void sina(){
//        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//        sp.setText(text);
//        sp.setTitle(title);
//        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
//        platform.setPlatformActionListener(this);
//        sp.setShareType(platform.SHARE_TEXT);
//        platform.share(sp);
//    }
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (platform.getName().equals(QQ.NAME)) {// 判断成功的平台是不是QQ
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.what = 3;
        msg.obj = throwable.getMessage();
        handler.sendMessage(msg);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(2);
    }
}
