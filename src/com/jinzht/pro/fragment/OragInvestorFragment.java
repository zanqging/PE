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
import com.jinzht.pro.activity.OriInvestorDetailActivity;
import com.jinzht.pro.adapter.OiganizeAdapter;
import com.jinzht.pro.model.InvestorOriBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.ACache;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2015/11/23.
 */
public class OragInvestorFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {
    private int pageNo = 0;
    private boolean isRefresh =true;
    private boolean isEnd = false;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private List<InvestorOriBean.DataEntity> investor_or = new ArrayList<>();
    private OrganizeTask task;
    private OiganizeAdapter adapter;
    private Intent intent;
    @OnItemClick(R.id.list) void pos(int pos){
        if (investor_or.size()==0){
            return;
        }
        MainActivity.hideMenu();
        intent = new Intent(getActivity(), OriInvestorDetailActivity.class);
        intent.putExtra("id",investor_or.get(pos).getId()+"");
        intent.putExtra("type",Constant.ORI_INVESTOR);
        intent.putExtra("name",investor_or.get(pos).getName());
        intent.putExtra("addr",investor_or.get(pos).getAddr());
        intent.putExtra("img",investor_or.get(pos).getLogo());
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
        task = new OrganizeTask();
        task.execute();
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
        super.successRefresh();
        task = new OrganizeTask();
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
                task =  new OrganizeTask();
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
        task =  new OrganizeTask();
        task.execute();
    }
    private void preRefresh(){
        index=0;
        top=0;
        isEnd = false;
        pageNo =0;
        isRefresh = true;
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
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTOR_INVESTOR + "2/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    aCache.put("InvestorOriBean" + pageNo, body, 1 * ACache.TIME_DAY);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                body = aCache.getAsString("InvestorOriBean"+pageNo);
                if (body==null){
                    return null;
                }
            }
            if (isRefresh&&investor_or.size()!=0){
                investor_or.clear();
            }
            if (FastJsonTools.getBean(body, InvestorOriBean.class)!=null&&FastJsonTools.getBean(body, InvestorOriBean.class).getData()!=null){
                investor_or.addAll(FastJsonTools.getBean(body,InvestorOriBean.class).getData());
            }
            return FastJsonTools.getBean(body, InvestorOriBean.class);
        }
        @Override
        protected void onPostExecute(InvestorOriBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (aVoid.getData()==null){
                    return;
                }

                if (aVoid.getCode()==2&&pageNo!=0){
                    isEnd=true;
                }
                adapter = new OiganizeAdapter(getActivity(),investor_or);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
            }
        }
    }
}