package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.GetSignBean;
import com.jinzht.pro.model.YibaoUnbindCallbackInfoBean;
import com.jinzht.pro.model.YibaoUserInfoBean;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.thoughtworks.xstream.XStream;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/3/21.
 * 已注册的账户页面
 */
public class YibaoAccount2Activity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_account2;
    }

    private TextView account_bank_name;// 银行名
    private TextView account_bank_no;// 卡号
    private TextView account_yue_no;// 总余额
    private TextView account_keyongyue_no;// 可用余额
    private TextView account_dongjieyue_no;// 冻结余额
    private TextView account_platformUserNo;// 用户编号
    private TextView account_platformPhone;// 手机
    private TextView account_btn_bind_2;// 绑定银行卡2
    private TextView account_btn_unbind;// 解绑银行卡
    private TextView account_btn_withdraw;// 提现
    private TextView account_btn_transfer;// 转账
    private RelativeLayout account_card;// 背景
    private String[] bankNames;

    private String request;// 查询易宝账户请求
    private String sign;// 查询易宝账户签名
    private YibaoUserInfoBean yibaoUserInfoBean;

    private String unbindRequest;// 解绑请求
    private String unbindSign;// 解绑签名
    private YibaoUnbindCallbackInfoBean yibaoUnbindCallbackInfoBean;

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.account));
        bankNames = getResources().getStringArray(R.array.banks);

        account_card = (RelativeLayout) findViewById(R.id.account_card);
        account_card.setOnClickListener(this);
        account_bank_name = (TextView) findViewById(R.id.account_bank_name);
        account_bank_no = (TextView) findViewById(R.id.account_bank_no);
        account_yue_no = (TextView) findViewById(R.id.account_yue_no);
        account_keyongyue_no = (TextView) findViewById(R.id.account_keyongyue_no);
        account_dongjieyue_no = (TextView) findViewById(R.id.account_dongjieyue_no);
        account_platformUserNo = (TextView) findViewById(R.id.account_platformUserNo);
        account_platformPhone = (TextView) findViewById(R.id.account_platformPhone);
        account_btn_bind_2 = (TextView) findViewById(R.id.account_btn_bind_2);
        account_btn_bind_2.setOnClickListener(this);
        account_btn_unbind = (TextView) findViewById(R.id.account_btn_unbind);
        account_btn_unbind.setOnClickListener(this);
        account_btn_withdraw = (TextView) findViewById(R.id.account_btn_withdraw);// 提现
        account_btn_withdraw.setOnClickListener(this);
        account_btn_transfer = (TextView) findViewById(R.id.account_btn_transfer);// 转账
        account_btn_transfer.setOnClickListener(this);

        // 如果有银行卡号码，就不显示绑定银行卡按钮
        if ("main".equals(getIntent().getStringExtra("main"))) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("bankNo"))) {// 主页进入且已经绑卡
                account_btn_bind_2.setVisibility(View.GONE);
                account_card.setClickable(false);
//                setData();
//                getDataFromNet();
            } else {// 主页进入且没有绑卡
                account_btn_bind_2.setVisibility(View.GONE);
                account_card.setClickable(false);
//                setEmptyData();
//                getDataFromNet();
            }
        }
        if ("bindWebView".equals(getIntent().getStringExtra("bindWebView"))) {// 绑卡后跳到这里
            account_btn_bind_2.setVisibility(View.GONE);
            account_card.setClickable(false);
//            getDataFromNet();
        }
//        getDataFromNet();
    }

    // 返回时刷新数据
    @Override
    public void onResume() {
        super.onResume();
        getDataFromNet();
    }

    // 从网络获取数据
    private void getDataFromNet() {
        GetSignTask getSignTask = new GetSignTask();
        getSignTask.execute();
    }

    // 传参赋值
    private void setData() {
        switch (getIntent().getStringExtra("bankName")) {
            case "BOCO":
                account_bank_name.setText(bankNames[0]);
                break;
            case "CEB":
                account_bank_name.setText(bankNames[1]);
                break;
            case "SPDB":
                account_bank_name.setText(bankNames[2]);
                break;
            case "ABC":
                account_bank_name.setText(bankNames[3]);
                break;
            case "ECITIC":
                account_bank_name.setText(bankNames[4]);
                break;
            case "CCB":
                account_bank_name.setText(bankNames[5]);
                break;
            case "CMBC":
                account_bank_name.setText(bankNames[6]);
                break;
            case "SDB":
                account_bank_name.setText(bankNames[7]);
                break;
            case "PSBC":
                account_bank_name.setText(bankNames[8]);
                break;
            case "CMBCHINA":
                account_bank_name.setText(bankNames[9]);
                break;
            case "CIB":
                account_bank_name.setText(bankNames[10]);
                break;
            case "ICBC":
                account_bank_name.setText(bankNames[11]);
                break;
            case "BOC":
                account_bank_name.setText(bankNames[12]);
                break;
            case "BCCB":
                account_bank_name.setText(bankNames[13]);
                break;
            case "GDB":
                account_bank_name.setText(bankNames[14]);
                break;
            case "HX":
                account_bank_name.setText(bankNames[15]);
                break;
            case "XAYH":
                account_bank_name.setText(bankNames[16]);
                break;
            case "SHYH":
                account_bank_name.setText(bankNames[17]);
                break;
            case "TJYH":
                account_bank_name.setText(bankNames[18]);
                break;
            case "SZNCSYYH":
                account_bank_name.setText(bankNames[19]);
                break;
            case "BJNCSYYH":
                account_bank_name.setText(bankNames[20]);
                break;
            case "HZYH":
                account_bank_name.setText(bankNames[21]);
                break;
            case "KLYH":
                account_bank_name.setText(bankNames[22]);
                break;
            case "ZHENGZYH":
                account_bank_name.setText(bankNames[23]);
                break;
            case "WZYH":
                account_bank_name.setText(bankNames[24]);
                break;
            case "HKYH":
                account_bank_name.setText(bankNames[25]);
                break;
            case "NJYH":
                account_bank_name.setText(bankNames[26]);
                break;
            case "XMYH":
                account_bank_name.setText(bankNames[27]);
                break;
            case "NCYH":
                account_bank_name.setText(bankNames[28]);
                break;
            case "JISYH":
                account_bank_name.setText(bankNames[29]);
                break;
            case "HKBEA":
                account_bank_name.setText(bankNames[30]);
                break;
            case "CDYH":
                account_bank_name.setText(bankNames[31]);
                break;
            case "NBYH":
                account_bank_name.setText(bankNames[32]);
                break;
            case "CSYH":
                account_bank_name.setText(bankNames[33]);
                break;
            case "HBYH":
                account_bank_name.setText(bankNames[34]);
                break;
            case "GUAZYH":
                account_bank_name.setText(bankNames[35]);
                break;
        }
        account_bank_no.setText(getIntent().getStringExtra("bankNo"));
        double yue = Double.parseDouble(getIntent().getStringExtra("yue"));
        yue = yue / 10000.00;
        double keyongyue = Double.parseDouble(getIntent().getStringExtra("keyongyue"));
        keyongyue = keyongyue / 10000.00;
        double dongjieyue = Double.parseDouble(getIntent().getStringExtra("dongjieyue"));
        dongjieyue = dongjieyue / 10000.00;
        DecimalFormat df = new DecimalFormat("0.00");
        account_yue_no.setText(df.format(yue) + "W");
        account_keyongyue_no.setText(df.format(keyongyue) + "W");
        account_dongjieyue_no.setText(df.format(dongjieyue) + "W");
//        account_yue_no.setText(String.valueOf(yue) + "W");
//        account_keyongyue_no.setText(String.valueOf(keyongyue) + "W");
//        account_dongjieyue_no.setText(String.valueOf(dongjieyue) + "W");
        account_platformUserNo.setText(getResources().getString(R.string.yibao_platformUserNo) + " " + "jinzht_0000_" + getIntent().getStringExtra("uid"));
        account_platformPhone.setText(getResources().getString(R.string.phone) + " " + getIntent().getStringExtra("platformPhone"));
    }

    // 从网络获取数据赋值
    private void setDataFromNet() {
        switch (yibaoUserInfoBean.getBank()) {
            case "BOCO":
                account_bank_name.setText(bankNames[0]);
                break;
            case "CEB":
                account_bank_name.setText(bankNames[1]);
                break;
            case "SPDB":
                account_bank_name.setText(bankNames[2]);
                break;
            case "ABC":
                account_bank_name.setText(bankNames[3]);
                break;
            case "ECITIC":
                account_bank_name.setText(bankNames[4]);
                break;
            case "CCB":
                account_bank_name.setText(bankNames[5]);
                break;
            case "CMBC":
                account_bank_name.setText(bankNames[6]);
                break;
            case "SDB":
                account_bank_name.setText(bankNames[7]);
                break;
            case "PSBC":
                account_bank_name.setText(bankNames[8]);
                break;
            case "CMBCHINA":
                account_bank_name.setText(bankNames[9]);
                break;
            case "CIB":
                account_bank_name.setText(bankNames[10]);
                break;
            case "ICBC":
                account_bank_name.setText(bankNames[11]);
                break;
            case "BOC":
                account_bank_name.setText(bankNames[12]);
                break;
            case "BCCB":
                account_bank_name.setText(bankNames[13]);
                break;
            case "GDB":
                account_bank_name.setText(bankNames[14]);
                break;
            case "HX":
                account_bank_name.setText(bankNames[15]);
                break;
            case "XAYH":
                account_bank_name.setText(bankNames[16]);
                break;
            case "SHYH":
                account_bank_name.setText(bankNames[17]);
                break;
            case "TJYH":
                account_bank_name.setText(bankNames[18]);
                break;
            case "SZNCSYYH":
                account_bank_name.setText(bankNames[19]);
                break;
            case "BJNCSYYH":
                account_bank_name.setText(bankNames[20]);
                break;
            case "HZYH":
                account_bank_name.setText(bankNames[21]);
                break;
            case "KLYH":
                account_bank_name.setText(bankNames[22]);
                break;
            case "ZHENGZYH":
                account_bank_name.setText(bankNames[23]);
                break;
            case "WZYH":
                account_bank_name.setText(bankNames[24]);
                break;
            case "HKYH":
                account_bank_name.setText(bankNames[25]);
                break;
            case "NJYH":
                account_bank_name.setText(bankNames[26]);
                break;
            case "XMYH":
                account_bank_name.setText(bankNames[27]);
                break;
            case "NCYH":
                account_bank_name.setText(bankNames[28]);
                break;
            case "JISYH":
                account_bank_name.setText(bankNames[29]);
                break;
            case "HKBEA":
                account_bank_name.setText(bankNames[30]);
                break;
            case "CDYH":
                account_bank_name.setText(bankNames[31]);
                break;
            case "NBYH":
                account_bank_name.setText(bankNames[32]);
                break;
            case "CSYH":
                account_bank_name.setText(bankNames[33]);
                break;
            case "HBYH":
                account_bank_name.setText(bankNames[34]);
                break;
            case "GUAZYH":
                account_bank_name.setText(bankNames[35]);
                break;
        }
        account_bank_no.setText(yibaoUserInfoBean.getCardNo());
        double yue = Double.parseDouble(yibaoUserInfoBean.getBalance());
        yue = yue / 10000;
        double keyongyue = Double.parseDouble(yibaoUserInfoBean.getAvailableAmount());
        keyongyue = keyongyue / 10000;
        double dongjieyue = Double.parseDouble(yibaoUserInfoBean.getFreezeAmount());
        dongjieyue = dongjieyue / 10000;
        DecimalFormat df = new DecimalFormat("0.00");
        account_yue_no.setText(df.format(yue) + "W");
        account_keyongyue_no.setText(df.format(keyongyue) + "W");
        account_dongjieyue_no.setText(df.format(dongjieyue) + "W");
//        account_yue_no.setText(String.valueOf(yue) + "W");
//        account_keyongyue_no.setText(String.valueOf(keyongyue) + "W");
//        account_dongjieyue_no.setText(String.valueOf(dongjieyue) + "W");
        account_platformUserNo.setText(getResources().getString(R.string.yibao_platformUserNo) + " " + "jinzht_0000_" + getIntent().getStringExtra("uid"));
        account_platformPhone.setText(getResources().getString(R.string.phone) + " " + yibaoUserInfoBean.getBindMobileNo());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.account_btn_bind_2 || v.getId() == R.id.account_card) {// 去绑卡
            Intent intent = new Intent(mContext, YibaoBindCardWebViewActivity.class);
            intent.putExtra("uid", getIntent().getStringExtra("uid"));
            startActivity(intent);
            finish();
        }
        if (v.getId() == R.id.account_btn_withdraw) {// 提现
            Intent yibaoWithdrawWebViewActivity = new Intent(this, YibaoWithdrawWebViewActivity.class);
            yibaoWithdrawWebViewActivity.putExtra("uid", getIntent().getStringExtra("uid") + "");
            yibaoWithdrawWebViewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(yibaoWithdrawWebViewActivity);
        }
        if (v.getId() == R.id.account_btn_unbind) {// 解绑
            confirmUnbind();
        }
        if (v.getId() == R.id.account_btn_transfer) {// 转账
            if (yibaoUserInfoBean != null) {
                if (yibaoUserInfoBean.getAvailableAmount().equals("0.00")) {
                    DialogUtils.toastDialog(YibaoAccount2Activity.this, getResources().getString(R.string.no_money), getResources().getString(R.string.ok));
                } else {
                    Intent yibaoTransferWebViewActivity = new Intent(this, YibaoTransferWebViewActivity.class);
                    yibaoTransferWebViewActivity.putExtra("uid", getIntent().getStringExtra("uid") + "");
                    yibaoTransferWebViewActivity.putExtra("amount", yibaoUserInfoBean.getAvailableAmount());
                    yibaoTransferWebViewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(yibaoTransferWebViewActivity);
                }
            }
        }
    }

    // 查询用户信息的签名
    private class GetSignTask extends AsyncTask<Void, Void, GetSignBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
            request = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + getIntent().getStringExtra("uid") + "</platformUserNo></request>";
//            request = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + getIntent().getStringExtra("uid") + "33" + "</platformUserNo></request>";
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
                // 查询是否注册过
                IsRegisteredTask isRegisteredTask = new IsRegisteredTask();
                isRegisteredTask.execute();
            }
        }
    }

    // 查询易宝用户信息
    private class IsRegisteredTask extends AsyncTask<Void, Void, YibaoUserInfoBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected YibaoUserInfoBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doPost("service", Constant.YIBAOACCOUNTINFO, "req", request, "sign", sign, Constant.YIBAODIRECT);
                    XStream xStream = new XStream();
                    xStream.processAnnotations(YibaoUserInfoBean.class);// 指定对应的Bean
                    yibaoUserInfoBean = (YibaoUserInfoBean) xStream.fromXML(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return yibaoUserInfoBean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(YibaoUserInfoBean bean) {
            super.onPostExecute(bean);
            if (bean == null) {
                SuperToastUtils.showSuperToast(mContext, 1, R.string.no_wifi);
            } else {
                Log.i("易宝信息", bean.toString());
                if (!TextUtils.isEmpty(bean.getCardNo())) {
                    setDataFromNet();
                } else {
                    setEmptyData();
                }
            }
        }
    }

    // 填充空数据
    private void setEmptyData() {
        account_bank_name.setText(getResources().getString(R.string.yibao_xxxbankName));
        account_bank_no.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        account_bank_no.setText(getResources().getString(R.string.yibao_xxxbankNo));
        double yue = Double.parseDouble(yibaoUserInfoBean.getBalance());
        yue = yue / 10000;
        double keyongyue = Double.parseDouble(yibaoUserInfoBean.getAvailableAmount());
        keyongyue = keyongyue / 10000;
        double dongjieyue = Double.parseDouble(yibaoUserInfoBean.getFreezeAmount());
        dongjieyue = dongjieyue / 10000;
        DecimalFormat df = new DecimalFormat("0.00");
        account_yue_no.setText(df.format(yue) + "W");
        account_keyongyue_no.setText(df.format(keyongyue) + "W");
        account_dongjieyue_no.setText(df.format(dongjieyue) + "W");
//        account_yue_no.setText("0.00W");
//        account_keyongyue_no.setText("0.00W");
//        account_dongjieyue_no.setText("0.00W");
        account_platformUserNo.setText(getResources().getString(R.string.yibao_platformUserNo) + " " + "jinzht_0000_" + getIntent().getStringExtra("uid"));
        account_platformPhone.setText(getResources().getString(R.string.phone) + " " + getIntent().getStringExtra("platformPhone"));
    }

    // 解绑的签名
    private class GetUnbindSignTask extends AsyncTask<Void, Void, GetSignBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
            String requestNo = time + getIntent().getStringExtra("uid");
            unbindRequest = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + getIntent().getStringExtra("uid") + "</platformUserNo><requestNo>" + requestNo + "</requestNo></request>";
        }

        @Override
        protected GetSignBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SIGNVERIFY + "?method=sign&req=" + unbindRequest, mContext);
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
                unbindSign = body.getData().getSign();
                // 解绑
                UnbindTask unbindTask = new UnbindTask();
                unbindTask.execute();
            }
        }
    }

    // 解绑
    private class UnbindTask extends AsyncTask<Void, Void, YibaoUnbindCallbackInfoBean> {

        @Override
        protected YibaoUnbindCallbackInfoBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doPost("service", Constant.YIBAOUNBIND, "req", unbindRequest, "sign", unbindSign, Constant.YIBAODIRECT);
                    XStream xStream = new XStream();
                    xStream.processAnnotations(YibaoUnbindCallbackInfoBean.class);// 指定对应的Bean
                    yibaoUnbindCallbackInfoBean = (YibaoUnbindCallbackInfoBean) xStream.fromXML(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return yibaoUnbindCallbackInfoBean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(YibaoUnbindCallbackInfoBean bean) {
            super.onPostExecute(bean);
            if (bean == null) {
                SuperToastUtils.showSuperToast(mContext, 1, R.string.no_wifi);
            } else {
                Log.i(getResources().getString(R.string.yibao_unbind), bean.toString());
                DialogUtils.toastDialog(YibaoAccount2Activity.this, bean.getDescription(), getResources().getString(R.string.ok));
            }
        }
    }

    @Override
    public void errorPage() {
    }

    @Override
    public void blankPage() {
    }

    // 提示用户资金到账前不要解绑
    private void confirmUnbind() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_two_btn);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);// 确定按钮
        tv_sure.setText(getResources().getString(R.string.goon));
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);// 取消按钮
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_msg.setText(getResources().getString(R.string.yibao_unbind_confirm));
        final AlertDialog finalDialog = dialog;
        // 继续解绑
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.dismiss();
                GetUnbindSignTask getUnbindSignTask = new GetUnbindSignTask();
                getUnbindSignTask.execute();
            }
        });
        final AlertDialog finalDialog1 = dialog;
        // 取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog1.dismiss();
            }
        });
    }
}
