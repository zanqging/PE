package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.InvestorCaseAdapter;
import com.jinzht.pro.model.OriInvestorDetailBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.view.HorSpaceItemDecoration;
import com.jinzht.pro.view.MultiStateView;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/12/29.
 *
 * 机构投资者详情页面
 */
public class OriInvestorDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private OriTask task;
    @Bind({R.id.tv_ori_names, R.id.tv_ori_time, R.id.tv_ori_adress, R.id.tv_ori_http})
    List<TextView> textViews;
    @Bind({R.id.ll_person_send_word, R.id.ll_person_case})
    List<LinearLayout> linearLayouts;
    @Bind(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.tv_one)
    TextView tv_profile;
    @Bind(R.id.iv_logo)
    ImageView iv_logo;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_ori_investor_detail;
    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new HorSpaceItemDecoration(15));
        if (getIntent().getExtras() != null) {
            title.setText(getIntent().getStringExtra("name"));
            GlideUtils.loadImg(mContext, getIntent().getStringExtra("img"), iv_logo);
        }
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new OriTask();
                        task.execute();
                    }
                });
        task = new OriTask();
        task.execute();
    }

    private class OriTask extends AsyncTask<Void, Void, OriInvestorDetailBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected OriInvestorDetailBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(OriInvestorDetailActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ORI_DETAIL + getIntent().getExtras().get("id") + "/", mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                }
                return FastJsonTools.getBean(body, OriInvestorDetailBean.class);
            } else {
                errorPage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(OriInvestorDetailBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getData() != null && aVoid.getCode() == 0) {
                    textViews.get(0).setText(getIntent().getStringExtra("name"));
                    textViews.get(1).setText(getResources().getString(R.string.detail_one) + aVoid.getData().getFoundingtime());
                    textViews.get(2).setText(getResources().getString(R.string.detail_two) + getIntent().getStringExtra("addr"));
                    textViews.get(3).setText(getResources().getString(R.string.detail_three) + aVoid.getData().getHomepage());
                    tv_profile.setText(aVoid.getData().getProfile());
//                    GlideUtils.loadImg(mContext,aVoid.getData().getThumbnail(),iv_logo);
                    if (aVoid.getData().getInvestcase() != null && aVoid.getData().getInvestcase().size() != 0) {
                        InvestorCaseAdapter adapter = new InvestorCaseAdapter(aVoid.getData().getInvestcase(), getApplicationContext());
                        recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
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
}