package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.expandabletextview.ExpandableTextView;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.PreDetailBean;
import com.jinzht.pro.model.TelephoneBean;
import com.jinzht.pro.utils.AndroidQuickStartUtils;
import com.jinzht.pro.utils.Animator;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * 待融资项目详情页面
 *
 * @auther Mr Jobs
 * @date 2016/1/12
 * @time 21:21
 */
public class PreProjectDetailActivity extends BaseActivity {

    InvestDetailTask task;
    private int collect_flag =-1;
    private int like_flag = -1;
    @Bind({R.id.collect,R.id.love}) List<CheckBox> checkBoxes;
    @Bind({R.id.company_intro,R.id.main_business,R.id.business_model})
    List<ExpandableTextView> expandableTextViews;
    @Bind({R.id.iv_collect,R.id.iv_love}) List<ImageView> imageViews;
    private PreDetailBean preDetailBean;
    @Bind(R.id.tv_plan_money)
    TextView tv_plan_money;
    @Bind(R.id.project_image)
    ImageView project_image;
    @OnClick(R.id.back) void back(){
        finish();
    }
    @Bind(R.id.back) LinearLayout back;
    @Bind(R.id.title) TextView title;
    @OnClick(R.id.play_btn) void ons(){
        if (preDetailBean!=null&& (!StringUtils.isEmpty(preDetailBean.getData().getVcr()))){
            Intent intent = new Intent(mContext, com.jinzht.pro.video.MainActivity.class);
            intent.putExtra("path",preDetailBean.getData().getVcr());
            startActivity(intent);
        }else {
            if (!UiHelp.isFastClick()){
                SuperToastUtils.showSuperToast(PreProjectDetailActivity.this,1,R.string.no_video);
            }
        }
    }
    @OnClick(R.id.ll_tel) void ss(){
        if (SharePreferencesUtils.getCoustomTelephot(mContext).equals("")){
            return;
        }
        AndroidQuickStartUtils.toTelephone(mContext,SharePreferencesUtils.getCoustomTelephot(mContext));
    }
    @Override
    protected int getResourcesId() {
        return R.layout.activity_pre_project_detail;
    }

    @OnClick({R.id.ll_collect,R.id.ll_love}) void line(LinearLayout linearLayout){
        switch (linearLayout.getId()){
            case R.id.ll_collect:
                if (checkBoxes.get(0).isChecked()){
                    //�Ѿ�����
                    checkBoxes.get(0).setChecked(false);
                    imageViews.get(0).setImageResource(R.drawable.collect);
                    checkBoxes.get(0).setText(preDetailBean.getData().getCollect()+"");
                    Animator.CollectScale(imageViews.get(0));
                    collect_flag = 1;
//                    CollectTask task = new CollectTask(collect_flag);
//                    task.execute();
                }else {
                    checkBoxes.get(0).setChecked(true);
                    checkBoxes.get(0).setText(preDetailBean.getData().getCollect() + 1+"");
                    imageViews.get(0).setImageResource(R.drawable.collect_select);
                    Animator.CollectScale(imageViews.get(0));
                    collect_flag = 0;
                    CollectTask task = new CollectTask(collect_flag);
                    task.execute();
                }
                break;
            case R.id.ll_love:
                if (checkBoxes.get(1).isChecked()){
                    //�Ѿ��ղ�
                    like_flag = 1;
//                    LinkTask task = new LinkTask(like_flag);
//                    task.execute();
                    checkBoxes.get(1).setChecked(false);
                    imageViews.get(1).setImageResource(R.drawable.love);
                    checkBoxes.get(1).setText(preDetailBean.getData().getLike()+"");
                    Animator.CollectScale(imageViews.get(1));
                }else {
                    //Ϊ�ղ�
                    like_flag = 0;
                    LinkTask task = new LinkTask(like_flag);
                    task.execute();
                    checkBoxes.get(1).setChecked(true);
                    imageViews.get(1).setImageResource(R.drawable.love_select);
                    checkBoxes.get(1).setText(preDetailBean.getData().getLike()+1+"");
                    Animator.CollectScale(imageViews.get(1));
                }
                break;
        }
    }
    @Override
    protected void init() {
        if (getIntent().getExtras()!=null){
            title.setText(getIntent().getExtras().getString("title"));
        }
        task = new InvestDetailTask();
        task.execute();

        if (SharePreferencesUtils.getCoustomTelephot(mContext).equals("")){
            TelephoneTask telephoneTask = new TelephoneTask();
            telephoneTask.execute();
        }
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    private class InvestDetailTask extends AsyncTask<Void,Void,PreDetailBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected PreDetailBean doInBackground(Void... voids) {
            String body = null;
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PRE_DETAIL+getIntent().getStringExtra("id")+"/", mContext);
                    Log.e(TAG,Constant.PROGECTDETAILS+getIntent().getStringExtra("id"));
                    Log.e(TAG, body);
                    preDetailBean= FastJsonTools.getBean(body,PreDetailBean.class);
                } catch (Exception e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                    return null;
                }
            }else {
                errorPage();
                return null;
            }
            return FastJsonTools.getBean(body,PreDetailBean.class);
        }
        @Override
        protected void onPostExecute(PreDetailBean peCompleteDetailsBean) {
            super.onPostExecute(peCompleteDetailsBean);
            dismissProgressDialog();
            if (peCompleteDetailsBean !=null){
                if (peCompleteDetailsBean.getCode()==-1){
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (peCompleteDetailsBean.getData().isIs_like()){
                    checkBoxes.get(1).setChecked(true);
                    imageViews.get(1).setBackgroundResource(R.drawable.love_select);
                }else {
                    checkBoxes.get(1).setChecked(false);
                    imageViews.get(1).setBackgroundResource(R.drawable.love);
                }
                if (peCompleteDetailsBean.getData().isIs_collect()){
                    checkBoxes.get(0).setChecked(true);
                    imageViews.get(0).setBackgroundResource(R.drawable.collect_select);
                }else {
                    checkBoxes.get(0).setChecked(false);
                    imageViews.get(0).setBackgroundResource(R.drawable.collect);
                }
                GlideUtils.loadImg(getApplicationContext(),getIntent().getStringExtra("img"),project_image);
                expandableTextViews.get(0).setText(peCompleteDetailsBean.getData().getProfile());
                expandableTextViews.get(1).setText(peCompleteDetailsBean.getData().getBusiness());
                expandableTextViews.get(2).setText(peCompleteDetailsBean.getData().getModel());
                checkBoxes.get(0).setText(peCompleteDetailsBean.getData().getCollect() +"");
                checkBoxes.get(1).setText(peCompleteDetailsBean.getData().getLike() + "");
                if (StringUtils.isEquals(peCompleteDetailsBean.getData().getPlanfinance(),"")){
                    tv_plan_money.setText(getResources().getString(R.string.project_plan)+getResources().getString(R.string.money_wait));
                }else
                    tv_plan_money.setText(getResources().getString(R.string.project_plan)+peCompleteDetailsBean.getData().getPlanfinance()+getResources().getString(R.string.wan));
            }
        }
    }
    private class CollectTask extends AsyncTask<Void,Void,CommonBean> {
        private int id;

        public CollectTask(int id) {
            this.id = id;
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PRE_COLLECT + getIntent().getStringExtra("id") + "/"+id+"/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);
                return FastJsonTools.getBean(body,CommonBean.class);
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common==null){
                return;
            }
        }
    }
    private class LinkTask extends AsyncTask<Void,Void,CommonBean> {
        private int id;

        public LinkTask(int id) {
            this.id = id;
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PRE_LIKE + getIntent().getStringExtra("id") + "/" + id + "/", mContext);
                    Log.e(TAG,getIntent().getStringExtra("id")+"id");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);
                return FastJsonTools.getBean(body,CommonBean.class);
            }else {
                return null;
            }
        }
        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common==null){
                SuperToastUtils.showSuperToast(PreProjectDetailActivity.this, 1, R.string.no_wifi);
            }
        }
    }


    private class TelephoneTask extends AsyncTask<Void,Void,TelephoneBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TelephoneBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(PreProjectDetailActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.MAIN_TELEPHONE,PreProjectDetailActivity.this);
                    Log.e(TAG,body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body,TelephoneBean.class);
            }else {
                return null;
            }
        }
        @Override
        protected void onPostExecute(TelephoneBean common) {
            super.onPostExecute(common);
            if (common!=null){
                if (common.getCode()==-1){
                    return;
                }
                if (common.getCode()==0){
//                    Log.e(TAG,"ismyproject");
                    SharePreferencesUtils.setCoustomTelephone(PreProjectDetailActivity.this, common.getData().get(0).getValue());
                }
            }
        }
    }
}