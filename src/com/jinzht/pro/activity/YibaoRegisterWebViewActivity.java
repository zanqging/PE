package com.jinzht.pro.activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.RegisterCallbackBean;
import com.jinzht.pro.model.ToRegisterBean;
import com.jinzht.pro.utils.DialogUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.OnClick;

/**
 * Created by pc on 2016/4/13.
 * 易宝注册界面
 */
public class YibaoRegisterWebViewActivity extends YibaoWebViewActivity {

    private RegisterCallbackBean registerCallbackBean;

    @OnClick(R.id.back)
    void back() {
        backDown();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backDown();
        }
        return super.onKeyDown(keyCode, event);
    }

    // 返回时提示用户投标还没进行完
    private void backDown() {
        switch (getIntent().getStringExtra("activity")) {
            case "WantInvestActivity":
                DialogUtils.goonCompleteInvestDialog(YibaoRegisterWebViewActivity.this);
                break;
            case "YibaoAccountActivity":
                finish();
                break;
        }
    }

    @Override
    protected void setWebtitle() {
        title.setText(getResources().getString(R.string.yibao_register_title));
    }

    @Override
    protected void getRequest() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
        String requestNo = time + getIntent().getStringExtra("uid");

        ToRegisterBean toRegisterBean = new ToRegisterBean();
        toRegisterBean.setPlatformNo("10013200657");
        toRegisterBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid"));
//        toRegisterBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid") + "33");
        toRegisterBean.setRequestNo(requestNo);
        toRegisterBean.setRealName(getIntent().getStringExtra("name"));
        toRegisterBean.setIdCardType("G2_IDCARD");
        toRegisterBean.setIdCardNo(getIntent().getStringExtra("idNo"));
        toRegisterBean.setMobile(getIntent().getStringExtra("tel"));
        toRegisterBean.setEmail(getIntent().getStringExtra("email"));
        toRegisterBean.setCallbackUrl(Constant.BASE_URL + Constant.PHONE + Constant.YIBAOCALLBACK);
        toRegisterBean.setNotifyUrl(Constant.YIBAONOTIFY);

        xStream.alias("request", ToRegisterBean.class);// 重命名标签
        xStream.useAttributeFor(ToRegisterBean.class, "platformNo");// 标记为属性
        String xml = xStream.toXML(toRegisterBean);
        xml = xml.replaceAll("\\r", "");
        xml = xml.replaceAll("\\n", "");
        xml = xml.replaceAll(" <", "<");
        xml = xml.replaceAll("> ", ">");

        request = xml;

        GetSignTask getSignTask = new GetSignTask();
        getSignTask.execute();
    }

    @Override
    protected void loadUrl() {
//        sign = URLEncoder.encode(sign);
        String postData = "req=" + request + "&sign=" + sign;
        Log.i("请求xml", request);
        Log.i("请求签名", sign);
        webView.postUrl(Constant.YIBAOGATEWAY + Constant.YIBAOREGISTER, postData.getBytes());
    }

    @Override
    protected void saveResult(String respResult) {
        xStream.processAnnotations(RegisterCallbackBean.class);// 指定要解析到的Bean
        if (respResult != null) {
            respResult = respResult.replaceAll("&lt;", "<" + "");
            respResult = respResult.replaceAll("&quot;", "\"" + "");
            respResult = respResult.replaceAll("&gt;", ">" + "");
            Log.i("返回XML2", respResult);
            registerCallbackBean = (RegisterCallbackBean) xStream.fromXML(respResult);
        }
        if (registerCallbackBean != null) {
            Log.i("返回参数Bean", registerCallbackBean.toString());
        }
    }

    @Override
    protected void saveSign(String signResult) {
        Log.i("返回签名", signResult);
        backSign = signResult;

        switch (getIntent().getStringExtra("activity")) {
            case "WantInvestActivity":
                if (registerCallbackBean != null && "1".equals(registerCallbackBean.getCode())) {
                    // 跳转到充值界面
                    Intent rechargeIntent = new Intent(mContext, YibaoRechargeWebViewActivity.class);
                    rechargeIntent.putExtra("id", getIntent().getStringExtra("id"));
                    rechargeIntent.putExtra("amount", getIntent().getStringExtra("amount"));
                    rechargeIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                    rechargeIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                    rechargeIntent.putExtra("uid", getIntent().getStringExtra("uid"));
                    rechargeIntent.putExtra("flag", getIntent().getStringExtra("flag"));
                    rechargeIntent.putExtra("profile", getIntent().getStringExtra("profile"));
                    rechargeIntent.putExtra("image", getIntent().getStringExtra("image"));
                    rechargeIntent.putExtra("brrow_user_no", getIntent().getStringExtra("brrow_user_no"));
                    rechargeIntent.putExtra("profit", getIntent().getDoubleExtra("profit", 0));
                    rechargeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    mContext.startActivity(rechargeIntent);
                }
                break;
            case "YibaoAccountActivity":
                if (registerCallbackBean != null && "1".equals(registerCallbackBean.getCode())) {
                    // 跳转到绑卡界面
                    Intent bindCardIntent = new Intent(mContext, YibaoBindCardWebViewActivity.class);
                    bindCardIntent.putExtra("uid", getIntent().getStringExtra("uid"));
                    bindCardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    mContext.startActivity(bindCardIntent);
                }
                break;
        }

    }
}
