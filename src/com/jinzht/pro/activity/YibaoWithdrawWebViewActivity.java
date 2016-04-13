package com.jinzht.pro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.GetSignBean;
import com.jinzht.pro.model.ToWithdrawBean;
import com.jinzht.pro.model.VerifySignBean;
import com.jinzht.pro.model.WithdrawCallbackBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.thoughtworks.xstream.XStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/3/11.
 * �ױ�����ҳ��
 */
public class YibaoWithdrawWebViewActivity extends BaseActivity {

    private WithdrawCallbackBean withdrawCallbackBean;
    private String request;// �������
    private String sign;// ǩ��
    private String backSign;// ����ǩ��
    private ProgressBar yibao_webview_progress;

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.my_webview)
    WebView webView;

    private XStream xStream;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_webview;
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    // ����
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText("����");

        yibao_webview_progress = (ProgressBar) findViewById(R.id.yibao_webview_progress);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// ֧��JavaScript
        webSettings.setSaveFormData(false);// �������
        webSettings.setAppCacheEnabled(false);// �����û���
        webView.setWebViewClient(new MyWebViewCClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.addJavascriptInterface(getHtmlObject(), "fromJS");// fromJS�Ǹ�jsʶ�������

        xStream = new XStream();

        getRequest();

    }

    private void getRequest() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
        String requestNo = time + getIntent().getStringExtra("uid");

        ToWithdrawBean toWithdrawBean = new ToWithdrawBean();
        toWithdrawBean.setPlatformNo("10013200657");
        toWithdrawBean.setRequestNo(requestNo);
        toWithdrawBean.setCallbackUrl(Constant.BASE_URL + Constant.PHONE + Constant.YIBAOCALLBACK);
        toWithdrawBean.setNotifyUrl(Constant.YIBAONOTIFY);
        toWithdrawBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid"));
        toWithdrawBean.setFeeMode("PLATFORM");// PLATFORM����USER

        xStream.alias("request", ToWithdrawBean.class);// ��������ǩ
        xStream.useAttributeFor(ToWithdrawBean.class, "platformNo");// ���Ϊ����
        String xml = xStream.toXML(toWithdrawBean);
        xml = xml.replaceAll("\\r", "");
        xml = xml.replaceAll("\\n", "");
        xml = xml.replaceAll(" <", "<");
        xml = xml.replaceAll("> ", ">");

        request = xml;

        GetSignTask getSignTask = new GetSignTask();
        getSignTask.execute();
    }

    // ��ȡǩ��
    private class GetSignTask extends AsyncTask<Void, Void, GetSignBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected GetSignBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SIGNVERIFY + "?method=sign&req=" + request, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, GetSignBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(GetSignBean body) {
            super.onPostExecute(body);
            dismissProgressDialog();
            if (body == null) {
                SuperToastUtils.showSuperToast(mContext, 1, R.string.no_wifi);
            } else {
                sign = body.getData().getSign();
                loadUrl();
            }
        }
    }

    // Webview�����ױ��󿨵�ҳ��
    private void loadUrl() {
//        sign = URLEncoder.encode(sign);
        String postData = "req=" + request + "&sign=" + sign;
        Log.i("����xml", request);
        Log.i("����ǩ��", sign);
        webView.postUrl(Constant.YIBAOGATEWAY + Constant.YIBAOWITHDRAW, postData.getBytes());
    }

    class MyWebViewCClient extends WebViewClient {
        // ��webview�Լ���������URL
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoadingProgressDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dismissProgressDialog();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            String imgPath = "file:///android_asset/error_pages.png";
            String data = "<HTML><Div align=\"center\"  margin=\"0px\"><IMG src=\"" + imgPath + "\" margin=\"0px\"/></Div>";
            view.loadDataWithBaseURL(imgPath, data, "text/html", "utf-8", null);
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                yibao_webview_progress.setVisibility(View.GONE);
            } else {
                if (View.INVISIBLE == yibao_webview_progress.getVisibility()) {
                    yibao_webview_progress.setVisibility(View.VISIBLE);
                }
                yibao_webview_progress.setProgress(newProgress);
            }
        }
    }

    // ��JS����Java����
    private Object getHtmlObject() {

        Object insertObj = new Object() {

            @JavascriptInterface
            public void getResp(String respResult, String signResult) {
                Log.i("���ص�XML", respResult);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saveResult(respResult);
                        saveSign(signResult);
                    }
                });
            }
        };

        return insertObj;
    }

    // ���淵�ز���
    private void saveResult(String respResult) {
        xStream.processAnnotations(WithdrawCallbackBean.class);// ָ��Ҫ��������Bean
        if (respResult != null) {
            respResult = respResult.replaceAll("&lt;", "<" + "");
            respResult = respResult.replaceAll("&quot;", "\"" + "");
            respResult = respResult.replaceAll("&gt;", ">" + "");
            Log.i("����XML2", respResult);
            withdrawCallbackBean = (WithdrawCallbackBean) xStream.fromXML(respResult);
        }
        if (withdrawCallbackBean != null) {
            Log.i("���ز���Bean", withdrawCallbackBean.toString());
        }
    }

    // ���淵�ص�ǩ������ǩ
    private void saveSign(String signResult) {
        Log.i("����ǩ��", signResult);
        backSign = signResult;
        if (withdrawCallbackBean != null && "1".equals(withdrawCallbackBean.getCode())) {
            finish();
        }
    }

    // ��ǩ
    private class VerifySignTask extends AsyncTask<Void, Void, VerifySignBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected VerifySignBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SIGNVERIFY + "?method=verify&req=" + request + "&sign=" + backSign, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, VerifySignBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(VerifySignBean body) {
            super.onPostExecute(body);
            dismissProgressDialog();
            if (body != null) {
                Log.i("��ǩ", body.getData().getVerify());
                if ("SUCCESS".equals(body.getData().getVerify()) && "1".equals(withdrawCallbackBean.getCode())) {
                    // ����
                } else {
                    // ��ǩʧ��
                    if (withdrawCallbackBean != null) {
                        // ��ת���ʲ��˻�2����YibaoAccount2Activity
                        Intent account2Intent = new Intent(mContext, YibaoAccount2Activity.class);
                        account2Intent.putExtra("uid", getIntent().getStringExtra("uid"));
                        account2Intent.putExtra("bindWebView", "bindWebView");
                        account2Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        mContext.startActivity(account2Intent);
                    }
                }
            }
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
