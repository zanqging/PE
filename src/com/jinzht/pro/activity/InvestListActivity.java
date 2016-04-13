package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.InvestListAdapter;
import com.jinzht.pro.model.AuthBean;
import com.jinzht.pro.model.InvestListBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.view.MultiStateView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 投资列表界面
 *
 * @auther Mr.Jobs
 * @date 2015/7/23
 * @time 11:02
 */

public class InvestListActivity extends BaseActivity {

    List<InvestListBean.DataEntity> investorBeanList = new ArrayList<>();

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.list)
    ListView invest_list;
    private InvestListAdapter adapter;

    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @Bind(R.id.back)
    LinearLayout back;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_invest_list;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.invest_list));
        IsInvestTask isInvestTask = new IsInvestTask();
        isInvestTask.execute();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        IsInvestTask isInvestTask = new IsInvestTask();
                        isInvestTask.execute();
                    }
                });
    }

    @Override
    public void errorPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    @Override
    public void blankPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    @Override
    public void successRefresh() {
        IsInvestTask isInvestTask = new IsInvestTask();
        isInvestTask.execute();
    }

    private class IsInvestTask extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, AuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common != null) {
                if (common.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (common.getCode() == 0) {
                    Log.e(TAG, "ismyproject");
                    if (StringUtils.isEquals(common.getData().getAuth() + "", "true")) {
                        InvestListTask investListTask = new InvestListTask(true);
                        investListTask.execute();
                    } else {
                        InvestListTask investListTask = new InvestListTask(false);
                        investListTask.execute();
                    }
                }
            }
        }
    }

    private class InvestListTask extends AsyncTask<Void, Void, InvestListBean> {
        private boolean isAuth;

        public InvestListTask(boolean isAuth) {
            this.isAuth = isAuth;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected InvestListBean doInBackground(Void... voids) {
            String body = null;
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTORLIST + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, body);
                    if (FastJsonTools.getBean(body, InvestListBean.class) != null && FastJsonTools.getBean(body, InvestListBean.class).getData() != null) {
                        investorBeanList.addAll(FastJsonTools.getBean(body, InvestListBean.class).getData());
                    }
                } catch (IOException e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                    return null;
                }

            }
            return FastJsonTools.getBean(body, InvestListBean.class);
        }

        @Override
        protected void onPostExecute(InvestListBean investListBeanList) {
            super.onPostExecute(investListBeanList);
            dismissProgressDialog();
            if (investListBeanList != null) {
                if (investListBeanList.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                try {
                    if (investorBeanList.size() == 0 && investListBeanList.getCode() == 2) {
                        blankPage();
                    }
                    adapter = new InvestListAdapter(investorBeanList, mContext, isAuth);
                    invest_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }
        }
    }
}