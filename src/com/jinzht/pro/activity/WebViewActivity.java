package com.jinzht.pro.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.utils.RippleUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 就一个浏览器
 *
 * @auther Mr.Jobs
 * @date 2015/8/13
 * @time 15:50
 */

public class WebViewActivity extends BaseActivity {


    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.my_webview)
    WebView webView;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        webView.getSettings().setJavaScriptEnabled(true);// 支持JavaScript
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
                showProgressDialog(getResources().getString(R.string.loading));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgressDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
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