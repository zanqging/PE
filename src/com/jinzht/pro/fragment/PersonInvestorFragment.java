package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.activity.PersonInvestorDetailActivity;
import com.jinzht.pro.adapter.FinaceInvestorAdapter;
import com.jinzht.pro.model.InvestBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.ACache;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2015/11/23.
 */
public class PersonInvestorFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener{

    private int pageNo = 0;
    private boolean isRefresh =true;
    private boolean isEnd = false;
    @Bind(R.id.list) ListView list;
    @Bind(R.id.swipyrefreshlayout) SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    ProjectTask task ;
    private List<InvestBean.DataEntity> investor_person = new ArrayList<>();
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    FinaceInvestorAdapter adapter;
    private Intent intent;
    @OnItemClick(R.id.list) void pos(int pos){
        if (investor_person.size()==0){
            return;
        }

        MainActivity.hideMenu();
        intent = new Intent(getActivity(), PersonInvestorDetailActivity.class);
        intent.putExtra("id",investor_person.get(pos).getId()+"");
        intent.putExtra("img",investor_person.get(pos).getPhoto());
        intent.putExtra("type",Constant.PERSON_INVESTOR);
        startActivity(intent);
    }
    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_collect_normal;
    }

    @Override
    protected void onFirstUserVisible() {
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        task = new ProjectTask();
        task.execute();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new ProjectTask();
                        task.execute();
                    }
                });
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    @Override
    public void blankPage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    @Override
    public void successRefresh() {
        super.successRefresh();
        task = new ProjectTask();
        task.execute();
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction==SwipyRefreshLayoutDirection.TOP){
            refresh();
        }else {
            updatePosition();
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
        }
    }
    private void updatePosition(){
        index = list.getFirstVisiblePosition();
        View view1 = list.getChildAt(0);
        top = (view1==null)?0:view1.getTop();
        Log.e(TAG, "index" + index + "top" + top);
    }
    private void refresh(){
        preRefresh();
        /**������*/
        task =  new ProjectTask();
        task.execute();
    }
    private void preRefresh(){
        index=0;
        top=0;
        isEnd = false;
        pageNo =0;
        isRefresh = true;
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
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTOR_INVESTOR + "1/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    aCache.put("InvestBean" + pageNo, body, 1 * ACache.TIME_DAY);
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                }
            }else{
                body = aCache.getAsString("InvestBean"+pageNo);
                if (body==null){
                    return null;
                }
            }
            if (isRefresh&&investor_person.size()!=0){
                investor_person.clear();
            }
            if (FastJsonTools.getBean(body, InvestBean.class)!=null&&FastJsonTools.getBean(body, InvestBean.class).getData()!=null){
                investor_person.addAll(FastJsonTools.getBean(body,InvestBean.class).getData());
            }
            return FastJsonTools.getBean(body, InvestBean.class);
        }
        @Override
        protected void onPostExecute(InvestBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd = true;
                }
                if (aVoid.getCode()==2&&investor_person.size()==0){
                    blankPage();
                }else {
                    /**������*/
                    adapter = new FinaceInvestorAdapter(getActivity(),investor_person);
                    list.setAdapter(adapter);
                    list.setSelectionFromTop(index, top);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }


}