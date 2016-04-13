package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.InvestorDetailActivity;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.adapter.ThinkTankAdapter;
import com.jinzht.pro.model.ThinkTeamBean;
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
 * Created by Administrator on 2015/12/29.
 */
public class ThinkTankFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener{
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout) SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private boolean isRefresh =true;
    private boolean isEnd = false;
    private ThinkTankAdapter think_adapter;
    private ProjectTask task;
    private int pageNo = 0;
    private Intent intent;
    private List<ThinkTeamBean.DataEntity> wait_list = new ArrayList<>();
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @OnItemClick(R.id.list) void ons(int pos){
        /**������*/
        MainActivity.hideMenu();
        if (wait_list.size()!=0){
            intent = new Intent(getActivity(), InvestorDetailActivity.class);
            intent.putExtra("id",wait_list.get(pos).getId()+"");
            intent.putExtra("img",wait_list.get(pos).getPhoto());
            intent.putExtra("name",wait_list.get(pos).getName());
            intent.putExtra("position",wait_list.get(pos).getPosition());
            startActivity(intent);
        }
    }
    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_thinktask;
    }

    @Override
    protected void onFirstUserVisible() {
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        refresh();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        refresh();
                    }
                });
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
    private void updatePosition(){
        index = list.getFirstVisiblePosition();
        View view1 = list.getChildAt(0);
        top = (view1==null)?0:view1.getTop();
        Log.e(TAG, "index" + index + "top" + top);
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


    private class ProjectTask extends AsyncTask<Void,Void,ThinkTeamBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected ThinkTeamBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.THINK_TANK  + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    aCache.put("ThinkTeamBean" + pageNo, body, 1 * ACache.TIME_DAY);
                } catch (Exception e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                }

            }else{
                body = aCache.getAsString("ThinkTeamBean"+pageNo);
                if (body==null){
                    errorPage();
                    return null;
                }
            }
            if (isRefresh&&wait_list.size()!=0){
                wait_list.clear();
            }
            if (FastJsonTools.getBean(body, ThinkTeamBean.class)!=null&&FastJsonTools.getBean(body,ThinkTeamBean.class).getData()!=null){
                wait_list.addAll(FastJsonTools.getBean(body, ThinkTeamBean.class).getData());
            }
            return FastJsonTools.getBean(body, ThinkTeamBean.class);
        }
        @Override
        protected void onPostExecute(ThinkTeamBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd=true;
                }
                if (aVoid.getCode()==2&&wait_list.size()==0){
                    blankPage();
                }else {
                    /**������*/
                    think_adapter = new ThinkTankAdapter(mContext,wait_list);
                    list.setAdapter(think_adapter);
                    list.setSelectionFromTop(index, top);
                    think_adapter.notifyDataSetChanged();
                }
            }
        }
    }
}