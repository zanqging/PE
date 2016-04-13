package com.jinzht.pro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.GetSignBean;
import com.jinzht.pro.model.InformationBean;
import com.jinzht.pro.model.InvestConfirmBean;
import com.jinzht.pro.model.InvestInfoBean;
import com.jinzht.pro.model.InvestorListBean;
import com.jinzht.pro.model.YibaoUserInfoBean;
import com.jinzht.pro.shimmer.Shimmer;
import com.jinzht.pro.shimmer.ShimmerTextView;
import com.jinzht.pro.utils.AndroidQuickStartUtils;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.thoughtworks.xstream.XStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * Ͷ�ʸ������
 *
 * @auther Mr.Jobs
 * @date 2015/5/23
 * @time 9:12
 */

public class WantInvestActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private Intent intent;
    private InformationBean informationBean;
    private String request;
    private String sign;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.back)
    LinearLayout back;

    private List<InvestorListBean> investorListBeanList = new ArrayList<>();// Ͷ�����б�
    private List<String> list = new ArrayList<>();// �б�
    private int width = 0;
    private int company_id = 0;// ��˾id
    private int flag = 1;// Ͷ�ʱ�ʶ����Ͷ1����Ͷ0
    private PopupWindow mPopupWindow;
    private ListView listView;// ��˾�б�?
    private ArrayAdapter arrayAdapter;// ��˾�б������������
    @Bind(R.id.title)
    TextView title;// ��ҪͶ�ʱ���
    Shimmer shimmer;// ����Ч��
    private InvestInfoBean investInfoBean;// Ͷ����Ϣ��
    private List<String> telephone_list = new ArrayList<>();
    @Bind(R.id.wan)
    ShimmerTextView wan;
    @Bind({R.id.investor_type_org, R.id.company_name_org, R.id.invest_area, R.id.invest_smaple, R.id.fund})
    List<TextView> organization_tv;//��֯��Ͷ�������ͣ���˾���ƣ�Ͷ������Ͷ�ʰ����������ģ
    @Bind({R.id.investor_type, R.id.name, R.id.telephone, R.id.company_name, R.id.your_area, R.id.your_position})
    List<TextView> people_tv;//���˵�Ͷ�������ͣ��������绰����˾���ƣ�����ְλ
    @Bind({R.id.ll_person, R.id.ll_organization})
    List<LinearLayout> ll_list;// �������ͺ���֯���͵Ĳ���
    private RadioGroup radio_title, group_investor;// ��Ͷ�͸�Ͷѡ��
    @Bind(R.id.choice_identity)
    TextView choice_identity;// ѡ��Ͷ�������
    @Bind(R.id.money_num)
    EditText money_num;// ��������
    @Bind(R.id.telephone_us)
    TextView telephone_us;

    // �绰��ϵ
    @OnClick(R.id.telephone_us)
    void telephone() {
        if (SharePreferencesUtils.getCoustomTelephot(mContext).equals("")) {
            return;
        }
        AndroidQuickStartUtils.toTelephone(mContext, SharePreferencesUtils.getCoustomTelephot(mContext));
    }

    // ���ѡ��Ͷ������ݣ�return
    @OnClick({R.id.choice_identity})
    void on(TextView textView) {
        switch (textView.getId()) {
            case R.id.choice_identity:
                break;
        }
    }

    // ���������������Ȳ����ύ��������������ȷ����ť����ת���ҵ�Ͷ����ҳ��
    @OnClick(R.id.sumbit_information)
    void on() {
        if (UiHelp.isFastClick()) {
            return;
        }
        if (StringUtils.isBlank(money_num.getText().toString())) {
            SuperToastUtils.showSuperToast(WantInvestActivity.this, 1, R.string.invest_max_hint);
        } else if (StringUtils.isBlank(choice_identity.getText().toString())) {
            SuperToastUtils.showSuperToast(WantInvestActivity.this, 1, R.string.choice_identity);
        } else if (getIntent().getIntExtra("minfund", 0) > Double.parseDouble(money_num.getText().toString())) {
            SuperToastUtils.showSuperToast(WantInvestActivity.this, 1, getResources().getString(R.string.yibao_minfund) + String.valueOf(getIntent().getIntExtra("minfund", 0)) + getResources().getString(R.string.wan));
        } else {
            // TODO: 2016/3/18 ��ȡ�û���Ϣ,�ж��Ƿ�����Ӫ��Ա
            GetInformationTask getInformationTasktask = new GetInformationTask();
            getInformationTasktask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_want_invest;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.want_invest));
        radio_title = (RadioGroup) findViewById(R.id.radioGroup_title);
        radio_title.setOnCheckedChangeListener(this);
        // ���ѡ��Ͷ������ݵ���ͼ���۲���
        ViewTreeObserver vto2 = choice_identity.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                choice_identity.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                width = choice_identity.getWidth();
            }
        });
        UiHelp.textBold(wan);
        shimmer = new Shimmer();
        shimmer.start(wan);
        shimmer.setDuration(2000).setStartDelay(100).setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        setPricePoint(money_num);
    }

    // ��֤��������ֻ��������λС��
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // �����Ͷ���Ͷ�ı�flag��ʶ
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.want_lingtou:
                //��Ͷ
                flag = 1;
                break;
            case R.id.want_gentou:
                //��ͷ
                flag = 0;
                break;

        }
    }

    // Ͷ�����б���
    private void showPopupWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_company_list, null);
        mPopupWindow = new PopupWindow(popupView, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        ����հ״���ʱ��PopupWindow����ʧ
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAsDropDown(choice_identity);// λ��ѡ��ѡ��Ͷ����������·�
        listView = (ListView) popupView.findViewById(R.id.company_list);
        arrayAdapter = new ArrayAdapter<String>(WantInvestActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (investorListBeanList == null || investorListBeanList.size() == 0) {

                } else {
                    choice_identity.setText(investorListBeanList.get(i).getCompany());
                    choice_identity.setTextColor(getResources().getColor(R.color.login_btn));
                    company_id = investorListBeanList.get(i).getId();
                    mPopupWindow.dismiss();
                    InvestorInfoTask investorInfoTask = new InvestorInfoTask(i);
                    investorInfoTask.execute();
                }
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

    // TODO: 2016/3/16 ��ȡ�û���Ϣ,�ж��Ƿ�����Ӫ��Ա
    private class GetInformationTask extends AsyncTask<Void, Void, InformationBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected InformationBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION + "?projectId=" + getIntent().getStringExtra("id"), mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, InformationBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InformationBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(mContext, 1, R.string.no_wifi);
            } else {
                informationBean = aVoid;
                if (aVoid.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                }
                if (aVoid.getCode() == 0) {
                    Log.i("��Ա��Ϣ", aVoid.getData().toString());
                    // ��Ӫ��Ա��ֱ���ύ
                    if (aVoid.getData().is_actived()) {
                        if (aVoid.getData().getVirtual_currency() < Double.valueOf(money_num.getText().toString())) {
                            SuperToastUtils.showSuperToast(mContext, 1, "�������㣬���ֵ����֧��");
                        } else {
                            InvestorSubmitTask task = new InvestorSubmitTask();
                            task.execute();
                        }
                        // ����Ӫ��Ա����ȡǩ���������ױ���ѯ�ӿڣ��ж��Ƿ�ע���
                    } else {
                        GetSignTask getSignTask = new GetSignTask();
                        getSignTask.execute();
                    }
                }
            }
        }
    }

    // TODO: 2016/3/18 ��ȡǩ��
    private class GetSignTask extends AsyncTask<Void, Void, GetSignBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
            request = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + informationBean.getData().getUid() + "</platformUserNo></request>";
//            request = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + informationBean.getData().getUid() + "34" + "</platformUserNo></request>";
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
                IsRegisteredTask isRegisteredTask = new IsRegisteredTask();
                isRegisteredTask.execute();
            }
        }
    }

    // TODO: 2016/3/18 �ж��Ƿ�ע���
    private class IsRegisteredTask extends AsyncTask<Void, Void, YibaoUserInfoBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected YibaoUserInfoBean doInBackground(Void... params) {
            String body = "";
            YibaoUserInfoBean yibaoUserInfoBean = null;
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doPost("service", Constant.YIBAOACCOUNTINFO, "req", request, "sign", sign, Constant.YIBAODIRECT);
                    XStream xStream = new XStream();
                    xStream.processAnnotations(YibaoUserInfoBean.class);// ָ����Ӧ��Bean
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
                Log.i("���صĲ�ѯ���", bean.toString());
                if ("1".equals(bean.getCode())) {
                    Log.i("������1", "ȥ��ֵ");
                    // �����ױ���ֵҳ��
                    Intent rechargeIntent = new Intent(mContext, YibaoRechargeWebViewActivity.class);
                    rechargeIntent.putExtra("id", getIntent().getStringExtra("id"));
                    rechargeIntent.putExtra("amount", money_num.getText().toString());
                    rechargeIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                    rechargeIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                    rechargeIntent.putExtra("image", getIntent().getStringExtra("image"));
                    rechargeIntent.putExtra("uid", informationBean.getData().getUid() + "");
                    rechargeIntent.putExtra("name", informationBean.getData().getName());
                    rechargeIntent.putExtra("idNo", informationBean.getData().getIdno());
                    rechargeIntent.putExtra("tel", informationBean.getData().getTel());
                    rechargeIntent.putExtra("email", informationBean.getData().getEmail());
                    rechargeIntent.putExtra("flag", flag + "");
                    rechargeIntent.putExtra("profile", getIntent().getStringExtra("profile"));
                    rechargeIntent.putExtra("image", getIntent().getStringExtra("image"));
                    rechargeIntent.putExtra("brrow_user_no", getIntent().getStringExtra("brrow_user_no"));
                    rechargeIntent.putExtra("profit", getIntent().getDoubleExtra("profit", 0));
                    rechargeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(rechargeIntent);
                    finish();
                } else if ("101".equals(bean.getCode())) {
                    Log.i("������101", "ȥע��");
                    // �����ױ�ע��ҳ��
                    Intent registerIntent = new Intent(mContext, YibaoRegisterWebViewActivity.class);
                    registerIntent.putExtra("activity", "WantInvestActivity");
                    registerIntent.putExtra("id", getIntent().getStringExtra("id"));
                    registerIntent.putExtra("amount", money_num.getText().toString());
                    registerIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                    registerIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                    registerIntent.putExtra("image", getIntent().getStringExtra("image"));
                    registerIntent.putExtra("uid", informationBean.getData().getUid() + "");
                    registerIntent.putExtra("name", informationBean.getData().getName());
                    registerIntent.putExtra("idNo", informationBean.getData().getIdno());
                    registerIntent.putExtra("tel", informationBean.getData().getTel());
                    registerIntent.putExtra("email", informationBean.getData().getEmail());
                    registerIntent.putExtra("flag", flag + "");
                    registerIntent.putExtra("profile", getIntent().getStringExtra("profile"));
                    registerIntent.putExtra("image", getIntent().getStringExtra("image"));
                    registerIntent.putExtra("brrow_user_no", getIntent().getStringExtra("brrow_user_no"));
                    registerIntent.putExtra("profit", getIntent().getDoubleExtra("profit", 0));
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(registerIntent);
                    finish();
                } else {
                    // ��ʾ�û�����
                    SuperToastUtils.showSuperToast(mContext, 1, bean.getDescription());
                }
            }
        }
    }

    // Ͷ������Ϣ
    private class InvestorInfoTask extends AsyncTask<Integer, Void, InvestInfoBean> {
        private int id;

        public InvestorInfoTask(int id) {
            this.id = id;
        }

        @Override
        protected InvestInfoBean doInBackground(Integer... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTORINFO + investorListBeanList.get(id).getId() + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    if (jsonObject1.optInt("investor_type") == 1) {
                        //����
                        String ss = "";
                        JSONArray jsonArray = jsonObject1.getJSONArray("industry");
                        if (jsonArray.length() == 0) {
                            ss = "";
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ss = ss + jsonArray.get(i) + "";
                            }
                        }
                        investInfoBean = new InvestInfoBean(jsonObject1.optInt("investor_type"), ss, jsonObject1.optString("fund_size_range"), jsonObject1.optString("company"));
                    } else if (jsonObject1.optInt("investor_type") == 0) {
                        //��Ȼ��
                        investInfoBean = new InvestInfoBean(jsonObject1.optInt("investor_type"), jsonObject1.optString("company"), jsonObject1.optString("province"), jsonObject1.optString("city"),
                                jsonObject1.optString("telephone"), jsonObject1.optString("position"), jsonObject1.optString("real_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return investInfoBean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InvestInfoBean common) {
            super.onPostExecute(common);
            if (common == null) {
                SuperToastUtils.showSuperToast(WantInvestActivity.this, 1, R.string.no_wifi);
                return;
            } else {
                if (investInfoBean.getInvestor_type() == 0) {
                    //��Ȼ��
                    ll_list.get(0).setVisibility(View.VISIBLE);
                    ll_list.get(1).setVisibility(View.GONE);
                    people_tv.get(0).setText(getResources().getString(R.string.person_investor));
                    people_tv.get(1).setText(common.getReal_name());
                    people_tv.get(2).setText(common.getTelephone());
                    people_tv.get(3).setText(common.getCompany());
                    people_tv.get(4).setText(common.getProvince() + common.getCity());
                    people_tv.get(5).setText(common.getPosition());
                } else {
                    //����
                    ll_list.get(1).setVisibility(View.VISIBLE);
                    ll_list.get(0).setVisibility(View.GONE);
                    organization_tv.get(0).setText(getResources().getString(R.string.organization_invest));
                    organization_tv.get(1).setText(common.getCompany());
                    organization_tv.get(2).setText(common.getIndustry());
//                    organization_tv.get(3).setText(common.getCompany());
                    organization_tv.get(4).setText(common.getFund_size_range());
                }
                investInfoBean = null;
            }
        }
    }

    // ���ȷ���ύ�󣬽����ʹ��ݵ�id�ύ��������������ȷ����ť����ת���ҵ�Ͷ����ҳ��
    private class InvestorSubmitTask extends AsyncTask<Void, Void, InvestConfirmBean> {
        private String requestNo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            time = time.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
            requestNo = time + informationBean.getData().getUid();
        }

        @Override
        protected InvestConfirmBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("amount", money_num.getText().toString(), "investCode", requestNo, Constant.BASE_URL + Constant.PHONE + Constant.WANTINCEST + getIntent().getStringExtra("id") + "/" + flag + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("֧���ɹ��󷵻ص�ȷ����Ϣ", body);
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
                    if (SharePreferencesUtils.getTelephone(WantInvestActivity.this).equals("") || SharePreferencesUtils.getPassWd(WantInvestActivity.this).equals("")) {
                        loginAgain();
                    } else {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    return;
                } else {
                    if (common.getCode() == 0) {
                        // ���븶��ɹ�����
                        Intent successIntent = new Intent(mContext, PaySucceedActivity.class);
                        successIntent.putExtra("id", getIntent().getStringExtra("id"));
                        successIntent.putExtra("amount", money_num.getText().toString());
                        successIntent.putExtra("companyName", getIntent().getStringExtra("companyName"));
                        successIntent.putExtra("abbrevName", getIntent().getStringExtra("abbrevName"));
                        successIntent.putExtra("image", getIntent().getStringExtra("image"));
                        finish();
                        startActivity(successIntent);

                        //DialogUtils.investPassDialog(WantInvestActivity.this, common.getMsg(), getResources().getString(R.string.submit), getIntent().getStringExtra("id"));
                    } else {
                        if (common.getData() == null) {
                            DialogUtils.isYoursDialog(WantInvestActivity.this, common.getMsg(), getResources().getString(R.string.submit));

                        } else {
//                            if (!UiHelp.isFastClick()){
                            SuperToastUtils.showSuperToast(WantInvestActivity.this, 1, common.getMsg());
//                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shimmer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}