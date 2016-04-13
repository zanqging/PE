package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.FancePlanBean;
import com.jinzht.pro.model.MoneyBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * 融资计划页面
 *
 * @auther Mr.Jobs
 * @date 2015/7/23
 * @time 10:08
 */

public class FinancingPlansActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    private List<FancePlanBean> list = new ArrayList<>();
    @Bind({R.id.financing_sum, R.id.financing_way, R.id.releasing_proportion, R.id.fund_use, R.id.exit_way})
    List<TextView> textViewList;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_financing_plans;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.money_plan));
        FinacePlanTask finacePlanTask = new FinacePlanTask();
        finacePlanTask.execute();
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

    private class FinacePlanTask extends AsyncTask<Void, Void, MoneyBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected MoneyBean doInBackground(Void... voids) {
            String body = "";
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.FINANCEPLAN + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return FastJsonTools.getBean(body, MoneyBean.class);
        }

        @Override
        protected void onPostExecute(MoneyBean fancePlanBeans) {
            super.onPostExecute(fancePlanBeans);
            dismissProgressDialog();
            if (fancePlanBeans != null) {
                if (fancePlanBeans.getData() == null) {
                    return;
                }
//                DecimalFormat df = new DecimalFormat("0.00");
                textViewList.get(0).setText(getResources().getString(R.string.financing_sum) + fancePlanBeans.getData().getPlanfinance() + getResources().getString(R.string.wan));
//                textViewList.get(1).setText(fancePlanBeans.getFinance_pattern());
                textViewList.get(2).setText(getResources().getString(R.string.releasing_proportion) + fancePlanBeans.getData().getShare2give() + "%");
                textViewList.get(3).setText(fancePlanBeans.getData().getUsage());
                textViewList.get(4).setText(getResources().getString(R.string.exit_way) + fancePlanBeans.getData().getQuitway());

            }
        }
    }
}