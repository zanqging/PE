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
import com.jinzht.pro.model.PersonInvestorDetailBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.view.MultiStateView;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/12/29.
 *
 * 个人投资者详情介绍页面
 */
public class PersonInvestorDetailActivity extends BaseActivity {

    @Bind(R.id.back) LinearLayout back;
    @OnClick(R.id.back) void back(){
        finish();
    }
    @Bind(R.id.title) TextView title;
    InvestorDetailBean bean;
    @Bind(R.id.person_record) ExpandableTextView expandableTextViews;
    @Bind(R.id.iv_person_bg) ImageView detail_image;
    @Bind({R.id.tv_one,R.id.tv_two,R.id.tv_three}) List<TextView> textViews;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private  PersonTask task;
    @Bind({R.id.ll_person_send_word,R.id.ll_person_case}) List<LinearLayout> linearLayouts;
    @Override
    protected int getResourcesId() {
        return R.layout.activity_person_investor_detail;
    }

    @Override
    protected void init() {
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        /**ˢ��������*/
                        task = new PersonTask();
                        task.execute();
                    }
                });
                task = new PersonTask();
                task.execute();
        if (getIntent().getExtras()!=null){
            GlideUtils.loadImg(mContext,getIntent().getStringExtra("img"),detail_image);
        }
    }

    private class PersonTask extends AsyncTask<Void,Void,PersonInvestorDetailBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }
        @Override
        protected PersonInvestorDetailBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(PersonInvestorDetailActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PERSON_DETAIL+getIntent().getExtras().get("id")+"/", mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                }
                return FastJsonTools.getBean(body, PersonInvestorDetailBean.class);
            }else{
                errorPage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(PersonInvestorDetailBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid!=null){
                if (aVoid.getCode()==0){
                    if (!StringUtils.isEmpty(aVoid.getData().getSignature())){
                        linearLayouts.get(0).setVisibility(View.VISIBLE);
                        textViews.get(0).setText(aVoid.getData().getSignature());
                    }
                    if (!StringUtils.isEmpty(aVoid.getData().getInvestcase())){
                        linearLayouts.get(1).setVisibility(View.VISIBLE);
                        textViews.get(2).setText(aVoid.getData().getInvestcase());
                    }
//                    UpLoadImageUtils.getImage(aVoid.getData().getProfile(),detail_image);
                    textViews.get(1).setText(aVoid.getData().getInvestplan());
                    expandableTextViews.setText(aVoid.getData().getProfile());
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