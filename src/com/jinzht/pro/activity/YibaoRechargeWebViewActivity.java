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
import com.jinzht.pro.model.RechargeCallbackBean;
import com.jinzht.pro.model.ToRechargeBean;
import com.jinzht.pro.model.VerifySignBean;
import com.jinzht.pro.utils.DialogUtils;
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
 * Created by pc on 2016/3/15.
 * �ױ���ֵ����
 */
public class YibaoRechargeWebViewActivity extends BaseActivity {

    private RechargeCallbackBean rechargeCallbackBean;
    private String request;
    private String sign;
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
        DialogUtils.goonCompleteInvestDialog(YibaoRechargeWebViewActivity.this);
    }

    // ����ʱ��ʾ�û�Ͷ�껹û������
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogUtils.goonCompleteInvestDialog(YibaoRechargeWebViewActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText("�ױ���ֵ");

        yibao_webview_progress = (ProgressBar) findViewById(R.id.yibao_webview_progress);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// ֧��JavaScript
        webSettings.setSaveFormData(false);// �����������
        webSettings.setAppCacheEnabled(false);// �����û���
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.addJavascriptInterface(getHtmlObject(), "fromJS");// fromJS�Ǹ�JSʶ�������

        xStream = new XStream();

        getRequest();
    }

    private void getRequest() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
        String requestNo = time + getIntent().getStringExtra("uid");

        double amount_totle = Double.parseDouble(getIntent().getStringExtra("amount")) * 10000.00;

        ToRechargeBean toRechargeBean = new ToRechargeBean();
        toRechargeBean.setPlatformNo("10013200657");
        toRechargeBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid"));
//        toRechargeBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid") + "33");
        toRechargeBean.setRequestNo(requestNo);
        toRechargeBean.setAmount(String.valueOf(amount_totle));
        toRechargeBean.setFeeMode("PLATFORM");
        toRechargeBean.setCallbackUrl(Constant.BASE_URL + Constant.PHONE + Constant.YIBAOCALLBACK);
        toRechargeBean.setNotifyUrl(Constant.YIBAONOTIFY);

        xStream.alias("request", ToRechargeBean.class);// ���������ڵ�
        xStream.useAttributeFor(ToRechargeBean.class, "platformNo");// ��platformNo����Ϊ����
        String xml = xStream.toXML(toRechargeBean);
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

    private void loadUrl() {
//        sign = URLEncoder.encode(sign);
        String postData = "req=" + request + "&sign=" + sign;
        Log.i("�������", request);
        Log.i("����ǩ��", sign);
        webView.postUrl(Constant.YIBAOGATEWAY + Constant.YIBAORECHARGE, postData.getBytes());
    }

    class MyWebViewClient extends WebViewClient {
        // ��WebView�Լ���������URL
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

    // ����JS�ķ������õ�����ֵ
    private Object getHtmlObject() {

        Object insertObj = new Object() {

            @JavascriptInterface
            public void getResp(String respResult, String signResult) {
                Log.i("����XML", respResult);

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

    // ���淵�ؽ��
    private void saveResult(String respResult) {
        xStream.processAnnotations(RechargeCallbackBean.class);// ָ����Ӧ��Bean
        if (respResult != null) {
            respResult = respResult.replaceAll("&lt;", "<" + "");
            respResult = respResult.replaceAll("&quot;", "\"" + "");
            respResult = respResult.replaceAll("&gt;", ">" + "");
            Log.i("����XML2", respResult);
            rechargeCallbackBean = (RechargeCallbackBean) xStream.fromXML(respResult);
        }
        if (rechargeCallbackBean != null) {
            Log.i("���ؽ��Bean", rechargeCallbackBean.toString());
        }
    }

    // ���淵��ǩ��
    private void saveSign(String signResult) {
        Log.i("����ǩ��", signResult);
        backSign = signResult;

//        VerifySignTask verifySignTask = new VerifySignTask();
//        verifySignTask.execute();

        if (rechargeCallbackBean != null && "1".equals(rechargeCallbackBean.getCode())) {
            // �����ױ�Ͷ��ҳ��
            Intent cpTransactionIntent = new Intent(mContext, YibaoCpTransactionWebViewActivity.class);
            cpTransactionIntent.putExtra("id", getIntent().getStringExtra("id"));
            cpTransactionIntent.putExtra("amount", getIntent().getStringExtra("amount"));
            cpTransactionIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
            cpTransactionIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
            cpTransactionIntent.putExtra("uid", getIntent().getStringExtra("uid"));
            cpTransactionIntent.putExtra("flag", getIntent().getStringExtra("flag"));
            cpTransactionIntent.putExtra("profile", getIntent().getStringExtra("profile"));
            cpTransactionIntent.putExtra("image", getIntent().getStringExtra("image"));
            cpTransactionIntent.putExtra("brrow_user_no", getIntent().getStringExtra("brrow_user_no"));
            cpTransactionIntent.putExtra("profit", getIntent().getDoubleExtra("profit", 0));
            cpTransactionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            mContext.startActivity(cpTransactionIntent);
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
                if ("SUCCESS".equals(body.getData().getVerify()) && "1".equals(rechargeCallbackBean.getCode())) {
                    // ����
                } else {
                    // ��ǩʧ��
                    if (rechargeCallbackBean != null) {
                        // �����ױ�Ͷ��ҳ��
                        Intent cpTransactionIntent = new Intent(mContext, YibaoCpTransactionWebViewActivity.class);
                        cpTransactionIntent.putExtra("id", getIntent().getStringExtra("id"));
                        cpTransactionIntent.putExtra("amount", getIntent().getStringExtra("amount"));
                        cpTransactionIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                        cpTransactionIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                        cpTransactionIntent.putExtra("uid", getIntent().getStringExtra("uid"));
                        cpTransactionIntent.putExtra("flag", getIntent().getStringExtra("flag"));
                        cpTransactionIntent.putExtra("profile", getIntent().getStringExtra("profile"));
                        cpTransactionIntent.putExtra("image", getIntent().getStringExtra("image"));
                        cpTransactionIntent.putExtra("brrow_user_no", getIntent().getStringExtra("brrow_user_no"));
                        cpTransactionIntent.putExtra("profit", getIntent().getDoubleExtra("profit", 0));
                        cpTransactionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        mContext.startActivity(cpTransactionIntent);
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
}
