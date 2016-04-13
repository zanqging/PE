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
import com.jinzht.pro.adapter.CollectNormalAdapter;
import com.jinzht.pro.adapter.FinaceInvestorAdapter;
import com.jinzht.pro.adapter.OiganizeAdapter;
import com.jinzht.pro.adapter.ProjectWaitAdapter;
import com.jinzht.pro.model.InvestBean;
import com.jinzht.pro.model.InvestorOriBean;
import com.jinzht.pro.model.ProjectWaitBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.UiHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 21:22
 */
public class InvestorFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener{


    private int project_flag=0;
    private int pageNo = 0;
    private boolean isRefresh =true;
    private boolean isFinishRefresh = true;
    private boolean isEnd = false;
    @Bind(R.id.list)
    ListView list;
     @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    ProjectTask task ;
    private List<InvestBean.DataEntity> investor_person = new ArrayList<>();
    private List<InvestorOriBean.DataEntity> investor_or = new ArrayList<>();
    private OrganizeTask organizeTask;
    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_collect_normal;
    }

    @Override
    protected void onFirstUserVisible() {
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        if (InvestAndFinancingFragment.investor.getCurrentItem()==0){
            task = new ProjectTask();
            task.execute();
        }else {
            organizeTask = new OrganizeTask();
            organizeTask.execute();
        }
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

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    @Override
    public void successRefresh() {

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction==SwipyRefreshLayoutDirection.TOP){
            //����ˢ��
            pageNo =0;
            isEnd = false;
            isRefresh= true;

        }else {
            index = list.getFirstVisiblePosition();
            View view1 = list.getChildAt(0);
            top = (view1==null)?0:view1.getTop();
            isRefresh = false;
            if (isEnd){
                UiHelp.ToastMessageShort(mContext, R.string.last_page);
                mSwipyRefreshLayout.setRefreshing(false);
            }else {
                pageNo = pageNo+1;
                isRefresh = false;
            }
        }
    }


    private class ProjectTask extends AsyncTask<Void,Void,InvestBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }
        @Override
        protected InvestBean doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTOR_INVESTOR + 1 + "/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    if (investor_person.size()!=0){
                        investor_person.clear();
                    }
                    if (FastJsonTools.getBean(body,InvestBean.class)!=null){
                        investor_person.addAll(FastJsonTools.getBean(body,InvestBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, InvestBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(InvestBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }
                FinaceInvestorAdapter adapter = new FinaceInvestorAdapter(getActivity(),investor_person);
                list.setAdapter(adapter);

            }
        }
    }


    private class OrganizeTask extends AsyncTask<Void,Void,InvestorOriBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }
        @Override
        protected InvestorOriBean doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTOR_INVESTOR + 2 + "/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    if (investor_or.size()!=0){
                        investor_or.clear();
                    }
                    if (FastJsonTools.getBean(body,InvestorOriBean.class)!=null){
                        investor_or.addAll(FastJsonTools.getBean(body,InvestorOriBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, InvestorOriBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(InvestorOriBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }
                OiganizeAdapter adapter = new OiganizeAdapter(getActivity(),investor_or);
                list.setAdapter(adapter);

            }
        }
    }
}