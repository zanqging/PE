package com.jinzht.pro.activity;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.BindCardCallbackBean;
import com.jinzht.pro.model.ToBindCardBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.OnClick;

/**
 * Created by pc on 2016/4/13.
 * 易宝绑卡界面
 */
public class YibaoBindCardWebViewActivity extends YibaoWebViewActivity {

    private BindCardCallbackBean bindCardCallbackBean;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void setWebtitle() {
        title.setText(getResources().getString(R.string.yibao_bindcard_title));
    }

    @Override
    protected void getRequest() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
        String requestNo = time + getIntent().getStringExtra("uid");

        ToBindCardBean toBindCardBean = new ToBindCardBean();
        toBindCardBean.setPlatformNo("10013200657");
        toBindCardBean.setRequestNo(requestNo);
        toBindCardBean.setCallbackUrl(Constant.BASE_URL + Constant.PHONE + Constant.YIBAOCALLBACK);
        toBindCardBean.setNotifyUrl(Constant.YIBAONOTIFY);
        toBindCardBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid"));
//        toBindCardBean.setPlatformUserNo("jinzht_0000_" + getIntent().getStringExtra("uid") + "33");

        xStream.alias("request", ToBindCardBean.class);// 重命名标签
        xStream.useAttributeFor(ToBindCardBean.class, "platformNo");// 标记为属性
        String xml = xStream.toXML(toBindCardBean);
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
        webView.postUrl(Constant.YIBAOGATEWAY + Constant.YIBAOBINDCARD, postData.getBytes());
    }

    @Override
    protected void saveResult(String respResult) {
        xStream.processAnnotations(BindCardCallbackBean.class);// 指定要解析到的Bean
        if (respResult != null) {
            respResult = respResult.replaceAll("&lt;", "<" + "");
            respResult = respResult.replaceAll("&quot;", "\"" + "");
            respResult = respResult.replaceAll("&gt;", ">" + "");
            Log.i("返回XML2", respResult);
            bindCardCallbackBean = (BindCardCallbackBean) xStream.fromXML(respResult);
        }
        if (bindCardCallbackBean != null) {
            Log.i("返回参数Bean", bindCardCallbackBean.toString());
        }
    }

    @Override
    protected void saveSign(String signResult) {
        Log.i("返回签名", signResult);
        backSign = signResult;

        if (bindCardCallbackBean != null && "1".equals(bindCardCallbackBean.getCode())) {
            // 跳转到资产账户2界面YibaoAccount2Activity
            Intent account2Intent = new Intent(mContext, YibaoAccount2Activity.class);
            account2Intent.putExtra("uid", getIntent().getStringExtra("uid"));
            account2Intent.putExtra("bindWebView", "bindWebView");
            account2Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            mContext.startActivity(account2Intent);
        }
    }
}
