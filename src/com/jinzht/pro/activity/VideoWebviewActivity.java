package com.jinzht.pro.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.utils.RippleUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/25.
 * <p>
 * 新手指南和融资播报的网页
 */
public class VideoWebviewActivity extends BaseActivity {
    private final String TAG = "VideoWebviewActivity";

    @OnClick(R.id.back)
    void back() {
        Log.e(TAG, "BACK");
        webView.loadUrl("file:///android_asset/nonexistent.html");
        webView = null;
        finish();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_video_webview;
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.my_webview)
    WebView webView;

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);// 允许访问文件
        ws.setDatabaseEnabled(true);// 启用数据库
        ws.setDomStorageEnabled(true);// 本地存储
        ws.setSaveFormData(false);// 不保存表单数据
        ws.setAppCacheEnabled(true);// 开启缓存
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);// 自动判断是否从网络读取数据
        ws.setLoadWithOverviewMode(false);//<==== һ��Ҫ����Ϊfalse����Ȼ������ûͼ��// 不适应屏幕
        ws.setUseWideViewPort(true);// 可缩放
        ws.setPluginState(WebSettings.PluginState.ON);// 开启插件
        webView.loadUrl(getIntent().getStringExtra("url"));
//        webView.loadUrl("http://www.jinzht.com:8000/app/news/news35657/");
//        webView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=http%3A%2F%2Fwww.jinzht.com%2Fstatic%2Fapp%2Fvideo%2Fgqzc7.docx");
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, url);
                Log.e(TAG, view.getId() + "id");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, url);
            }

            // 重定向
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, url);
                view.loadUrl(url);
                return true;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onrsutl");
//        �ָ�����
        if (webView != null) {
            webView.onResume();
        }
//        try {
//            webView.getClass().getMethod("onResume").invoke(webView, (Object[]) null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onPause() {
        //��ͣ����
        Log.e(TAG, "onpaseu");
        try {
            if (webView != null) {
                webView.onPause();
                webView.stopLoading();
                webView.reload();
            }
        } catch (Exception e) {

        }


//        try {
//            Class.forName("android.webkit.WebView")
//                    .getMethod("onPause", (Class[]) null)
//                    .invoke(webView, (Object[]) null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "destoru");
        if (webView != null) {
            webView.loadUrl("");
            webView.stopLoading();
        }
//        webView.loadUrl("file:///android_asset/nonexistent.html");
//        webView = null;
//        webView.destroy();
        super.onDestroy();
        //һ��Ҫ���٣������޷�ֹͣ����
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            if (webView != null) {
                webView.loadUrl("");
                webView.stopLoading();
            }

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}