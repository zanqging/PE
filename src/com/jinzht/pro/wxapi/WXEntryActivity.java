package com.jinzht.pro.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import cn.sharesdk.wechat.utils.*;
import com.jinzht.pro.R;
import com.tencent.mm.sdk.openapi.*;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/6/30
 * @time 17:33
 */

public class WXEntryActivity extends WechatHandlerActivity {

    @Override
    public void onGetMessageFromWXReq(cn.sharesdk.wechat.utils.WXMediaMessage msg) {
        super.onGetMessageFromWXReq(msg);
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    @Override
    public void onShowMessageFromWXReq(cn.sharesdk.wechat.utils.WXMediaMessage msg) {
        super.onShowMessageFromWXReq(msg);
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */

}