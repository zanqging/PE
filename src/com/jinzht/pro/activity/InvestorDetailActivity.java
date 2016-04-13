package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.expandabletextview.ExpandableTextView;
import com.jinzht.pro.model.InvestorDetailBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.view.MultiStateView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * 智囊团中个人详情界面
 *
 * @auther Mr.Jobs
 * @date 2015/8/15
 * @time 8:53
 */

public class InvestorDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.person_record)
    ExpandableTextView expandableTextViews;
    @Bind({R.id.tv_one, R.id.tv_two, R.id.tv_three})
    List<TextView> textViews;
    InvestorDetailBean bean;
    @Bind(R.id.iv_think_detail)
    ImageView iv_think_detail;

    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_investor_detail;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        UpLoadImageUtils.getImage(getIntent().getStringExtra("img"), iv_think_detail);
        InvestListTask investListTask = new InvestListTask();
        investListTask.execute();
        if (getIntent().getExtras() != null) {
            textViews.get(0).setText(getIntent().getStringExtra("position"));
        }
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        /**ˢ��������*/
                        InvestListTask investListTask = new InvestListTask();
                        investListTask.execute();
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
        /**重新拉取数据*/
        InvestListTask investListTask = new InvestListTask();
        investListTask.execute();
    }

    private class InvestListTask extends AsyncTask<Void, Void, InvestorDetailBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected InvestorDetailBean doInBackground(Void... voids) {
            String body = null;
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                errorPage();
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.THINKTANKDETAIL + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, body);
                    if (FastJsonTools.getBean(body, InvestorDetailBean.class) != null) {
                        bean = FastJsonTools.getBean(body, InvestorDetailBean.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                    return null;
                }
            }
            return FastJsonTools.getBean(body, InvestorDetailBean.class);
        }

        @Override
        protected void onPostExecute(InvestorDetailBean investListBeanList) {
            super.onPostExecute(investListBeanList);
            dismissProgressDialog();
            if (investListBeanList != null) {
                if (investListBeanList.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (investListBeanList.getCode() == 0) {
                    expandableTextViews.setText(investListBeanList.getData().getExperience());
                    textViews.get(1).setText(investListBeanList.getData().getSignature());
                    textViews.get(2).setText(investListBeanList.getData().getCases());
                }
            }

        }
    }
}