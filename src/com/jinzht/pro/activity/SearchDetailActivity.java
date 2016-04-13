package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.SearchListAdapter;
import com.jinzht.pro.model.SearchBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * ��������������ҳ��
 *
 * @auther Mr.Jobs
 * @date 2015/6/3
 * @time 16:54
 */

public class SearchDetailActivity extends BaseActivity implements View.OnFocusChangeListener,SwipyRefreshLayout.OnRefreshListener{


    @OnClick(R.id.rl_back) void back(){
        this.finish();
    }
    @OnClick(R.id.search_edit) void fin(){
        finish();
    }
    @Bind(R.id
    .rl_back)
    RelativeLayout rl_back;
    @Bind(R.id.search_edit) EditText search_edit;
    private int top =0;
    private int index =0;
    private List<SearchBean.DataEntity> searchBeanList = new ArrayList<>();
    private Intent intent;
    private boolean isRefresh =true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private int flag = 2;
    private boolean isEnd = false;
    private SearchTask searchTask;
    @Bind(R.id.swipyrefreshlayout) SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
     @Bind(R.id.list) ListView listView;
     @OnItemClick(R.id.list) void list(int pos){
         if (!UiHelp.isLongFastClick()){
             intent = new Intent(mContext,InvestFinacingDetailsActivity.class);
             intent.putExtra("id",searchBeanList.get(pos).getId()+"");
             intent.putExtra("companyName", searchBeanList.get(pos).getCompany() + "");
             intent.putExtra("image", searchBeanList.get(pos).getImg() + "");
             startActivity(intent);
         }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_search_detail;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(rl_back);
        search_edit.setOnFocusChangeListener(this);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        searchTask = new SearchTask();
        searchTask.execute();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            finish();
        }
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction==SwipyRefreshLayoutDirection.TOP){
            //����ˢ��
            preRefresh();
            searchTask = new SearchTask();
            searchTask.execute();
        }else {
            index = listView.getFirstVisiblePosition();
            View view = listView.getChildAt(0);
            top = (view==null)?0:view.getTop();
            if (isEnd){
                lastToast();
                mSwipyRefreshLayout.setRefreshing(false);
            }else {
                pageNO = pageNO+1;
                isRefresh = false;
                Log.e(TAG,"pageno"+pageNO);
                searchTask = new SearchTask();
                searchTask.execute();
            }
        }
    }

    private void lastToast(){
        if (!UiHelp.isLongFastClick()){
            SuperToastUtils.showSuperToast(SearchDetailActivity.this, 1, R.string.last_page);
        }
    }
    private void preRefresh(){
        isFirst= true;
        isEnd = false;
        pageNO =0;
        isRefresh = true;
        top=0;
        index=0;
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

    private class SearchTask extends AsyncTask<Void,Void,SearchBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected SearchBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //����
                try {
                        body = AsynOkUtils.doPushPost("wd", getIntent().getStringExtra("word"), Constant.BASE_URL + Constant.PHONE + Constant.SEARCH + pageNO + "/", mContext);

                    Log.e(TAG, body);
                    if (isRefresh&&searchBeanList.size()!=0){
                        searchBeanList.clear();
                    }
                    if (FastJsonTools.getBean(body,SearchBean.class)!=null){
                        searchBeanList.addAll(FastJsonTools.getBean(body,SearchBean.class).getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return FastJsonTools.getBean(body,SearchBean.class);
            }else{
                return null;
            }
        }
        @Override
        protected void onPostExecute(SearchBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid==null){
                mSwipyRefreshLayout.setRefreshing(false);
                dismissProgressDialog();
            }else {
                mSwipyRefreshLayout.setRefreshing(false);
                mSwipyRefreshLayout.setVisibility(View.VISIBLE);
                SearchListAdapter adapter = new SearchListAdapter(mContext,searchBeanList);
                listView.setAdapter(adapter);
                listView.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
                dismissProgressDialog();
            }
        }
    }
}