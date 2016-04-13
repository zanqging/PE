package com.jinzht.pro.reciver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jinzht.pro.R;
import com.jinzht.pro.activity.InteractActivity;
import com.jinzht.pro.activity.InvestFinacingDetailsActivity;
import com.jinzht.pro.activity.ThreeWebviewActivity;
import com.jinzht.pro.activity.WebViewActivity;
import com.jinzht.pro.eventbus.MainNoticeEvent;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.UiHelp;

import org.json.JSONObject;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        CustomPushNotificationBuilder builder = new
                CustomPushNotificationBuilder(context,
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.text);
        // 指定定制的 Notification Layout
        builder.statusBarDrawable = R.drawable.logo_small;
        // 指定最顶层状态栏小图标
        builder.layoutIconDrawable = R.drawable.logo_small;
        // 指定下拉状态栏时显示的通知图标
        builder.notificationDefaults = Notification.DEFAULT_ALL;
        JPushInterface.setPushNotificationBuilder(3, builder);
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//        	processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiversACTION_NOTIFICATION_RECEIVED] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiversACTION_NOTIFICATION_RECEIVED] 接收到推送下来的通知的ID: " + notifactionId);
            if (bundle.getString(JPushInterface.EXTRA_EXTRA) != null) {
                try {
                    JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    if (!jsonObject.isNull("api")) {
                        if (jsonObject.getString("api").equals("msg")) {
                            EventBus.getDefault().post(new MainNoticeEvent("receiver"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            if (bundle.getString(JPushInterface.EXTRA_EXTRA) != null) {
                try {
                    jsonJudge(context, bundle.getString(JPushInterface.EXTRA_EXTRA));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.d(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void jsonJudge(Context context, String body) throws Exception {
        JSONObject jsonObject = new JSONObject(body);
        if (jsonObject.isNull("api")) {
            return;
        }
        if (jsonObject.getString("api").equals("project")) {
            if (!UiHelp.isTopActivity(context)) {
                Log.d(TAG, "not_top");
                UiHelp.openJinZht(context, "com.jinzht.pro");
            }
            /**progect里头分了俩种，纯web(仅有url)和news，news就是有id和url*/
            if (jsonObject.opt("id") == null || jsonObject.opt("url") == null) {
                return;
            }
            String id = jsonObject.opt("id") + "";
            if (!StringUtils.isEquals(id, "null")) {
                Intent i = new Intent(context, InvestFinacingDetailsActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("id", id);
                context.startActivity(i);
            }
        } else if (jsonObject.getString("api").equals("msg")) {
            if (!UiHelp.isTopActivity(context)) {
                Log.d(TAG, "not_top");
                UiHelp.openJinZht(context, "com.jinzht.pro");
            }
            if (jsonObject.opt("id") == null) {
                return;
            }
            String id = jsonObject.opt("id") + "";
            Intent i = new Intent(context, InteractActivity.class);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("id", id);
            context.startActivity(i);
        } else if (jsonObject.getString("api").equals("web")) {
            if (!UiHelp.isTopActivity(context)) {
                Log.d(TAG, "not_top");
                UiHelp.openJinZht(context, "com.jinzht.pro");
            }
            if (jsonObject.opt("id") == null || jsonObject.opt("url") == null) {
                return;
            }
            String id = jsonObject.opt("id") + "";
            if (StringUtils.isEquals(id, "null") || StringUtils.isEquals(id, "")) {
                Intent i = new Intent(context, WebViewActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("url", jsonObject.optString("url"));
                context.startActivity(i);
            } else if (!StringUtils.isEquals(id, "null") && !jsonObject.optString("url").equals("")) {
                Intent i = new Intent(context, ThreeWebviewActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("id", jsonObject.optString("id"));
                i.putExtra("url", jsonObject.optString("url"));
                context.startActivity(i);
            }
        } else if (jsonObject.getString("api").equals("feeling")) {
            {
//				if (!UiHelp.isTopActivity(context)) {
//					Log.e(TAG, "not_top");
//					UiHelp.openJinZht(context, "com.jinzht.pro");
//				}
//				Intent i = new Intent(context, MainActivity.class);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(i);
//				MainActivity.vpFragment.setCurrentItem(3);
            }
        }
    }
    //send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
//	}
}
