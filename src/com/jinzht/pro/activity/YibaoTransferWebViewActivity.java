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
import com.jinzht.pro.model.TenderCallbackBean;
import com.jinzht.pro.model.ToCpTransactionBean;
import com.jinzht.pro.model.ToCpTransactionDetailBean;
import com.jinzht.pro.model.ToCpTransactionPropertyBean;
import com.jinzht.pro.model.VerifySignBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.thoughtworks.xstream.XStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/3/16.
 * �ױ�ת��ҳ��
 */
public class YibaoTransferWebViewActivity extends BaseActivity {

    private TenderCallbackBean tenderCallbackBean;
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
        finish();
    }

    // ����ʱ��ʾ�û�Ͷ�껹û������
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText("���ת��");

        yibao_webview_progress = (ProgressBar) findViewById(R.id.yibao_webview_progress);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// ֧��JavaScript
        webSettings.setSaveFormData(false);// �������
        webSettings.setAppCacheEnabled(false);// �����û���
        webView.setWebViewClient(new MyWebViewCClient());
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

        ToCpTransactionBean toCpTransactionBean = new ToCpTransactionBean();
        toCpTransactionBean.setPlatformNo("10013200657");
        toCpTransactionBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid"));
//        toCpTransactionBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid") + "33");
        toCpTransactionBean.setRequestNo(requestNo);
        toCpTransactionBean.setUserType("MEMBER");
        toCpTransactionBean.setBizType("TENDER");
        toCpTransactionBean.setCallbackUrl(Constant.BASE_URL + Constant.PHONE + Constant.YIBAOCALLBACK);
        toCpTransactionBean.setNotifyUrl(Constant.YIBAONOTIFY);

        double amount_totle = Double.parseDouble(getIntent().getStringExtra("amount"));

        ToCpTransactionDetailBean detailBean1 = new ToCpTransactionDetailBean("MEMBER", "jinzht_0000_645", String.valueOf(amount_totle), "TENDER");
        List<ToCpTransactionDetailBean> details = new ArrayList<ToCpTransactionDetailBean>();
        details.add(detailBean1);
        toCpTransactionBean.setDetails(details);

        ToCpTransactionPropertyBean propertyBean1 = new ToCpTransactionPropertyBean("tenderOrderNo", "jinzht_project_24");// ��Ŀ��ţ�jinzht_project_id
        ToCpTransactionPropertyBean propertyBean2 = new ToCpTransactionPropertyBean("tenderName", "���Ͼ�Ӣ");// ��Ŀ���ƣ���˾���
        ToCpTransactionPropertyBean propertyBean3 = new ToCpTransactionPropertyBean("tenderAmount", String.valueOf(amount_totle));// ��Ŀ���
        ToCpTransactionPropertyBean propertyBean4 = new ToCpTransactionPropertyBean("tenderDescription", "���Ͼ�Ӣ");// ��Ŀ������Ϣ����˾ȫ��
        ToCpTransactionPropertyBean propertyBean5 = new ToCpTransactionPropertyBean("borrowerPlatformUserNo", "jinzht_0000_645");// ��Ŀ�Ľ����ƽ̨�û����
        List<ToCpTransactionPropertyBean> extend = new ArrayList<ToCpTransactionPropertyBean>();
        extend.add(propertyBean1);
        extend.add(propertyBean2);
        extend.add(propertyBean3);
        extend.add(propertyBean4);
        extend.add(propertyBean5);
        toCpTransactionBean.setExtend(extend);

        xStream.alias("request", ToCpTransactionBean.class);// ��������ǩ
        xStream.useAttributeFor(ToCpTransactionBean.class, "platformNo");// ��platformNo����Ϊ����
        xStream.alias("detail", ToCpTransactionDetailBean.class);// ��������ǩ
        xStream.alias("property", ToCpTransactionPropertyBean.class);// ��������ǩ
        xStream.useAttributeFor(ToCpTransactionPropertyBean.class, "name");// ��name����Ϊ����
        xStream.useAttributeFor(ToCpTransactionPropertyBean.class, "value");// ��value����Ϊ����

        String xml = xStream.toXML(toCpTransactionBean);
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
        Log.i("����xml", request);
        Log.i("����ǩ��", sign);
        webView.postUrl(Constant.YIBAOGATEWAY + Constant.YIBAOTRANSACTION, postData.getBytes());
    }

    class MyWebViewCClient extends WebViewClient {
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
        xStream.processAnnotations(TenderCallbackBean.class);// ָ����Ӧ��Bean
        if (respResult != null) {
            respResult = respResult.replaceAll("&lt;", "<" + "");
            respResult = respResult.replaceAll("&quot;", "\"" + "");
            respResult = respResult.replaceAll("&gt;", ">" + "");
            Log.i("����XML2", respResult);
            tenderCallbackBean = (TenderCallbackBean) xStream.fromXML(respResult);
        }
        if (tenderCallbackBean != null) {
            Log.i("����Bean", tenderCallbackBean.toString());
        }
    }

    // ���淵��ǩ��
    private void saveSign(String signResult) {
        Log.i("����ǩ��", signResult);
        backSign = signResult;

//        VerifySignTask verifySignTask = new VerifySignTask();
//        verifySignTask.execute();

        if (tenderCallbackBean != null && "1".equals(tenderCallbackBean.getCode())) {
            // ���븶��ɹ�����
            Intent successIntent = new Intent(mContext, TransferSucceedActivity.class);
            successIntent.putExtra("id", getIntent().getStringExtra("id"));
            successIntent.putExtra("amount", getIntent().getStringExtra("amount"));
            finish();
            startActivity(successIntent);
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
                if ("SUCCESS".equals(body.getData().getVerify()) && "1".equals(tenderCallbackBean.getCode())) {
                    // ����
                } else {
                    // ��ǩʧ��
                    if (tenderCallbackBean != null) {
                        // ���븶��ɹ�����
                        Intent successIntent = new Intent(mContext, PaySucceedActivity.class);
                        successIntent.putExtra("id", getIntent().getStringExtra("id"));
                        successIntent.putExtra("amount", getIntent().getStringExtra("amount"));
                        successIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                        successIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                        successIntent.putExtra("image", getIntent().getStringExtra("image"));
                        finish();
                        startActivity(successIntent);
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
