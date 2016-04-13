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
 * ����Ψ��ƶ����Բ��Ͷ���
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
     * ����΢�ŷ������������Ӧ������app message
     * <p>
     * ��΢�ſͻ����е�����ҳ���С���ӹ��ߡ������Խ���Ӧ�õ�ͼ����ӵ�����
     * �˺���ͼ�꣬����Ĵ���ᱻִ�С�Demo����ֻ�Ǵ��Լ����ѣ������
     * �������������飬�������������κ�ҳ��
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
     * ����΢���������Ӧ�÷������Ϣ
     * <p>
     * �˴��������մ�΢�ŷ��͹�������Ϣ���ȷ�˵��demo��wechatpage�������
     * Ӧ��ʱ���Բ�����Ӧ���ļ���������һ��Ӧ�õ��Զ�����Ϣ�����ܷ���΢��
     * �ͻ��˻�ͨ������������������Ϣ���ͻؽ��շ��ֻ��ϵı�demo�У�����
     * �ص���
     * <p>
     * ��Demoֻ�ǽ���Ϣչʾ������������������������飬��������ֻ��Toast
     */

}