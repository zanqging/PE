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
import com.jinzht.pro.model.InvestConfirmBean;
import com.jinzht.pro.model.TenderCallbackBean;
import com.jinzht.pro.model.ToCpTransactionBean;
import com.jinzht.pro.model.ToCpTransactionDetailBean;
import com.jinzht.pro.model.ToCpTransactionPropertyBean;
import com.jinzht.pro.model.VerifySignBean;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/3/16.
 * 易宝投标页面
 */
public class YibaoCpTransactionWebViewActivity extends BaseActivity {

    private TenderCallbackBean tenderCallbackBean;
    private String request;
    private String sign;
    private String backSign;// 返回签名
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
        DialogUtils.goonCompleteInvestDialog(YibaoCpTransactionWebViewActivity.this);
    }

    // 返回时提示用户投标还没进行完
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogUtils.goonCompleteInvestDialog(YibaoCpTransactionWebViewActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText("投标");

        yibao_webview_progress = (ProgressBar) findViewById(R.id.yibao_webview_progress);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 支持JavaScript
        webSettings.setSaveFormData(false);// 不保存表单
        webSettings.setAppCacheEnabled(false);// 不启用缓存
        webView.setWebViewClient(new MyWebViewCClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.addJavascriptInterface(getHtmlObject(), "fromJS");// fromJS是给JS识别的名字

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

        double amount_totle = Double.parseDouble(getIntent().getStringExtra("amount")) * 10000.00;
        double profit = getIntent().getDoubleExtra("profit", 0);// 分润比率
        double amount_platform = amount_totle * profit;// 平台分润金额
        double amount_user = amount_totle - amount_platform;// 借款人得到金额

        ToCpTransactionDetailBean detailBean1 = new ToCpTransactionDetailBean("MEMBER", getIntent().getStringExtra("brrow_user_no"), String.valueOf(amount_user), "TENDER");
//        ToCpTransactionDetailBean detailBean1 = new ToCpTransactionDetailBean("MEMBER", "030", String.valueOf(amount_user), "TENDER");
        ToCpTransactionDetailBean detailBean2 = new ToCpTransactionDetailBean("MERCHANT", "10013200657", String.valueOf(amount_platform), "COMMISSION");
        List<ToCpTransactionDetailBean> details = new ArrayList<ToCpTransactionDetailBean>();
        details.add(detailBean1);
        details.add(detailBean2);
        toCpTransactionBean.setDetails(details);

        ToCpTransactionPropertyBean propertyBean1 = new ToCpTransactionPropertyBean("tenderOrderNo", "jinzht_project_" + getIntent().getStringExtra("id"));// 项目编号，jinzht_project_id
        ToCpTransactionPropertyBean propertyBean2 = new ToCpTransactionPropertyBean("tenderName", getIntent().getStringExtra("abbrevName"));// 项目名称，公司简称
        ToCpTransactionPropertyBean propertyBean3 = new ToCpTransactionPropertyBean("tenderAmount", String.valueOf(amount_totle));// 项目金额
        ToCpTransactionPropertyBean propertyBean4 = new ToCpTransactionPropertyBean("tenderDescription", getIntent().getStringExtra("companyName"));// 项目描述信息，公司全称
        ToCpTransactionPropertyBean propertyBean5 = new ToCpTransactionPropertyBean("borrowerPlatformUserNo", getIntent().getStringExtra("brrow_user_no"));// 项目的借款人平台用户编号
//        ToCpTransactionPropertyBean propertyBean5 = new ToCpTransactionPropertyBean("borrowerPlatformUserNo", "030");// 项目的借款人平台用户编号
        List<ToCpTransactionPropertyBean> extend = new ArrayList<ToCpTransactionPropertyBean>();
        extend.add(propertyBean1);
        extend.add(propertyBean2);
        extend.add(propertyBean3);
        extend.add(propertyBean4);
        extend.add(propertyBean5);
        toCpTransactionBean.setExtend(extend);

        xStream.alias("request", ToCpTransactionBean.class);// 重命名标签
        xStream.useAttributeFor(ToCpTransactionBean.class, "platformNo");// 将platformNo设置为属性
        xStream.alias("detail", ToCpTransactionDetailBean.class);// 重命名标签
        xStream.alias("property", ToCpTransactionPropertyBean.class);// 重命名标签
        xStream.useAttributeFor(ToCpTransactionPropertyBean.class, "name");// 将name设置为属性
        xStream.useAttributeFor(ToCpTransactionPropertyBean.class, "value");// 将value设置为属性

        String xml = xStream.toXML(toCpTransactionBean);
        xml = xml.replaceAll("\\r", "");
        xml = xml.replaceAll("\\n", "");
        xml = xml.replaceAll(" <", "<");
        xml = xml.replaceAll("> ", ">");

        request = xml;

        GetSignTask getSignTask = new GetSignTask();
        getSignTask.execute();
    }

    // 获取签名
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
        Log.i("请求xml", request);
        Log.i("请求签名", sign);
        webView.postUrl(Constant.YIBAOGATEWAY + Constant.YIBAOTRANSACTION, postData.getBytes());
    }

    class MyWebViewCClient extends WebViewClient {
        // 让WebView自己处理后面的URL
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

    // 调用JS的方法，得到返回值
    private Object getHtmlObject() {

        Object insertObj = new Object() {

            @JavascriptInterface
            public void getResp(String respResult, String signResult) {
                Log.i("返回XML", respResult);

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

    // 保存返回结果
    private void saveResult(String respResult) {
        xStream.processAnnotations(TenderCallbackBean.class);// 指定对应的Bean
        if (respResult != null) {
            respResult = respResult.replaceAll("&lt;", "<" + "");
            respResult = respResult.replaceAll("&quot;", "\"" + "");
            respResult = respResult.replaceAll("&gt;", ">" + "");
            Log.i("返回XML2", respResult);
            tenderCallbackBean = (TenderCallbackBean) xStream.fromXML(respResult);
        }
        if (tenderCallbackBean != null) {
            Log.i("返回Bean", tenderCallbackBean.toString());
        }
    }

    // 保存返回签名
    private void saveSign(String signResult) {
        Log.i("返回签名", signResult);
        backSign = signResult;

//        VerifySignTask verifySignTask = new VerifySignTask();
//        verifySignTask.execute();

        if (tenderCallbackBean != null && "1".equals(tenderCallbackBean.getCode())) {
            // 将结果提交到服务器，跳转到付款成功页面
            InvestorSubmitTask task = new InvestorSubmitTask();
            task.execute();
        }
    }

    // 验签
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
                Log.i("验签", body.getData().getVerify());
                if ("SUCCESS".equals(body.getData().getVerify()) && "1".equals(tenderCallbackBean.getCode())) {
                    // 继续
                } else {
                    // 验签失败
                    if (tenderCallbackBean != null) {
                        // 将结果提交到服务器，跳转到付款成功页面
                        InvestorSubmitTask task = new InvestorSubmitTask();
                        task.execute();
                    }
                }
            }
        }
    }

    // 点击确认提交后，将金额和传递的id提交到服务器，弹出确定按钮，跳转至我的投融资页面
    private class InvestorSubmitTask extends AsyncTask<Void, Void, InvestConfirmBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected InvestConfirmBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("amount", getIntent().getStringExtra("amount"), "investCode", tenderCallbackBean.getRequestNo(), Constant.BASE_URL + Constant.PHONE + Constant.WANTINCEST + getIntent().getStringExtra("id") + "/" + getIntent().getStringExtra("flag") + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("支付成功后返回的确认信息", body);
                return FastJsonTools.getBean(body, InvestConfirmBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InvestConfirmBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common != null) {
                if (common.getCode() == -1) {
                    if (SharePreferencesUtils.getTelephone(YibaoCpTransactionWebViewActivity.this).equals("") || SharePreferencesUtils.getPassWd(YibaoCpTransactionWebViewActivity.this).equals("")) {
                        loginAgain();
                    } else {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    return;
                } else {
                    if (common.getCode() == 0) {
                        // 进入付款成功界面
                        Intent successIntent = new Intent(mContext, PaySucceedActivity.class);
                        successIntent.putExtra("id", getIntent().getStringExtra("id"));
                        successIntent.putExtra("amount", getIntent().getStringExtra("amount"));
                        successIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                        successIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                        successIntent.putExtra("image", getIntent().getStringExtra("image"));
                        finish();
                        startActivity(successIntent);

                        //DialogUtils.investPassDialog(WantInvestActivity.this, common.getMsg(), getResources().getString(R.string.submit), getIntent().getStringExtra("id"));
                    } else {
                        if (common.getData() == null) {
                            DialogUtils.isYoursDialog(YibaoCpTransactionWebViewActivity.this, common.getMsg(), getResources().getString(R.string.submit));
                        } else {
//                            if (!UiHelp.isFastClick()){
                            SuperToastUtils.showSuperToast(YibaoCpTransactionWebViewActivity.this, 1, common.getMsg());
//                            }
                        }
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
