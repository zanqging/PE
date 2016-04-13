package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.OnItemClick;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.InvestFinacingDetailsActivity;
import com.jinzht.pro.adapter.FinishFinancingAdapter;
import com.jinzht.pro.adapter.ProjectWaitAdapter;
import com.jinzht.pro.model.FinishFinacingBean;
import com.jinzht.pro.model.ProjectWaitBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class ProjectFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//刷新
    private boolean isRefresh =true;
    private boolean isFinishRefresh = true;
    private boolean isEnd = false;
    private List<ProjectWaitBean.DataEntity> new_list = new ArrayList<>();
    private ProjectWaitAdapter adapter;
    private FinishFinancingAdapter finishAdapter;
    private ProjectTask task;
    private int project_flag=0;
    private int pageNo = 0;
    private Intent intent;
    private List<ProjectWaitBean.DataEntity> wait_list = new ArrayList<>();
    private List<FinishFinacingBean.DataEntity> finish_list = new ArrayList<>();
    private UploadTask uploadTask;
    FinishTask finishTask;
    private WaitFinaceTask waitTask;
    @OnItemClick(R.id.list) void ons(int pos){
        if (InvestAndFinancingFragment.viewPager.getCurrentItem()==0){
            /**待融资*/
            intent = new Intent(getActivity(), InvestFinacingDetailsActivity.class);
            intent.putExtra("id",wait_list.get(pos).getId()+"");
            intent.putExtra("companyName", finish_list.get(pos).getCompany() + "");
            intent.putExtra("abbrevName", finish_list.get(pos).getAbbrevcompany() + "");
            intent.putExtra("image", finish_list.get(pos).getImg() + "");
            startActivity(intent);
        }else if (InvestAndFinancingFragment.viewPager.getCurrentItem()==1){
            /**已融资*/
            intent = new Intent(getActivity(), InvestFinacingDetailsActivity.class);
            intent.putExtra("id",finish_list.get(pos).getId()+"");
            intent.putExtra("companyName", finish_list.get(pos).getCompany() + "");
            intent.putExtra("abbrevName", finish_list.get(pos).getAbbrevcompany() + "");
            intent.putExtra("image", finish_list.get(pos).getImg() + "");
            startActivity(intent);
        }else if (InvestAndFinancingFragment.viewPager.getCurrentItem()==2){
            /**预选项目*/
            intent = new Intent(getActivity(), InvestFinacingDetailsActivity.class);
            intent.putExtra("id",finish_list.get(pos).getId()+"");
            intent.putExtra("companyName", finish_list.get(pos).getCompany() + "");
            intent.putExtra("abbrevName", finish_list.get(pos).getAbbrevcompany() + "");
            intent.putExtra("image", finish_list.get(pos).getImg() + "");
            startActivity(intent);
        }else {

        }
    }
    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_project;
    }
    @Override
    protected void onFirstUserVisible() {
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        refresh();
    }
    @Override
    protected void onUserInvisible() {
    }
    @Override
    protected void onFirstUserInvisble() {

    }
    @Override
    protected void onUserVisble() {
    }
    @Override
    protected void init() {
    }
    private void preRefresh(){
        index=0;
        top=0;
        isEnd = false;
        pageNo =0;
        isRefresh = true;
    }
    private void refresh(){
        preRefresh();
        if (InvestAndFinancingFragment.viewPager.getCurrentItem()==0){
            /**待融资*/
            task =  new ProjectTask();
            task.execute();
        }else if (InvestAndFinancingFragment.viewPager.getCurrentItem()==1){
            /**已融资*/
            waitTask =  new WaitFinaceTask();
            waitTask.execute();
        }else if (InvestAndFinancingFragment.viewPager.getCurrentItem()==2){
            /**预选项目*/
            finishTask =  new FinishTask();
            finishTask.execute();
        }else {
            uploadTask = new UploadTask();
            uploadTask.execute();
        }
    }
    private void updatePosition(){
        index = list.getFirstVisiblePosition();
        View view1 = list.getChildAt(0);
        top = (view1==null)?0:view1.getTop();
        Log.e(TAG, "index" + index + "top" + top);
    }
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction==SwipyRefreshLayoutDirection.TOP){
            refresh();
        }else {
            updatePosition();
            if (InvestAndFinancingFragment.viewPager.getCurrentItem()==0){
                if (isEnd){
                    if (!UiHelp.isLongFastClick()){
                        SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                    }
                    mSwipyRefreshLayout.setRefreshing(false);
                }else {
                    pageNo = pageNo+1;
                    isRefresh = false;
                    task =  new ProjectTask();
                    task.execute();
                }
            }else if (InvestAndFinancingFragment.viewPager.getCurrentItem()==1){
                if (isEnd){
                    if (!UiHelp.isLongFastClick()){
                        SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                    }
                    mSwipyRefreshLayout.setRefreshing(false);
                }else {
                    pageNo = pageNo+1;
                    isRefresh = false;
                    waitTask =  new WaitFinaceTask();
                    waitTask.execute();
                }
            }else if (InvestAndFinancingFragment.viewPager.getCurrentItem()==2){
                if (isEnd){
                    if (!UiHelp.isLongFastClick()){
                        SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                    }
                    mSwipyRefreshLayout.setRefreshing(false);
                }else {
                    pageNo = pageNo+1;
                    isRefresh = false;
                    finishTask =  new FinishTask();
                    finishTask.execute();
                }
            }else {
                if (!UiHelp.isLongFastClick()){
                    mSwipyRefreshLayout.setRefreshing(false);
                    SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                }
            }

        }
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

    private class ProjectTask extends AsyncTask<Void,Void,ProjectWaitBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected ProjectWaitBean doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PROJECT_TITLE + (InvestAndFinancingFragment.viewPager.getCurrentItem()+1) + "/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    if (isRefresh&&wait_list.size()!=0){
                        wait_list.clear();
                    }
                    wait_list.addAll(FastJsonTools.getBean(body, ProjectWaitBean.class).getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, ProjectWaitBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(ProjectWaitBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }
                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd=true;
                }
                /**待融资*/
                adapter = new ProjectWaitAdapter(getActivity(),wait_list);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();

            }
        }
    }
    private class FinishTask extends AsyncTask<Void,Void,FinishFinacingBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipyRefreshLayout.setRefreshing(true);
        }

        @Override
        protected FinishFinacingBean doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PROJECT_TITLE + (InvestAndFinancingFragment.viewPager.getCurrentItem()+1) + "/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    if (isRefresh&&finish_list.size()!=0){
                        finish_list.clear();
                    }
                    finish_list.addAll(FastJsonTools.getBean(body, FinishFinacingBean.class).getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, FinishFinacingBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(FinishFinacingBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }
                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd=true;
                }
                /**融资完毕*/
                 finishAdapter = new FinishFinancingAdapter(getActivity(),finish_list);
                 list.setAdapter(finishAdapter);
                 list.setSelectionFromTop(index, top);
                 finishAdapter.notifyDataSetChanged();
            }
        }
    }

    private class WaitFinaceTask extends AsyncTask<Void,Void,FinishFinacingBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipyRefreshLayout.setRefreshing(true);
        }

        @Override
        protected FinishFinacingBean doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PROJECT_TITLE + (InvestAndFinancingFragment.viewPager.getCurrentItem()+1) + "/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    if (isRefresh&&finish_list.size()!=0){
                        finish_list.clear();
                    }
                    finish_list.addAll(FastJsonTools.getBean(body, FinishFinacingBean.class).getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, FinishFinacingBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(FinishFinacingBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }
                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd=true;
                }
                finishAdapter = new FinishFinancingAdapter(getActivity(),finish_list);
                list.setAdapter(finishAdapter);
                list.setSelectionFromTop(index, top);
                finishAdapter.notifyDataSetChanged();
            }
        }
    }
    private class UploadTask extends AsyncTask<Void,Void,ProjectWaitBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected ProjectWaitBean doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PROJECT_TITLE + (InvestAndFinancingFragment.viewPager.getCurrentItem()+1) + "/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    if (isRefresh&&wait_list.size()!=0){
                        wait_list.clear();
                    }
                    wait_list.addAll(FastJsonTools.getBean(body, ProjectWaitBean.class).getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, ProjectWaitBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(ProjectWaitBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }
                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd=true;
                }
                /**待融资*/
                adapter = new ProjectWaitAdapter(getActivity(),wait_list);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();

            }
        }
    }
}