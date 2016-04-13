package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.ThreeBoardAdapter;
import com.jinzht.pro.materialsearchview.MaterialSearchView;
import com.jinzht.pro.model.NewsBean;
import com.jinzht.pro.model.SearchBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2015/9/12.
 *
 * 点击新三板右上角的搜索进入到的搜索页面
 */
public class ThreeSearchDetailActivity extends BaseActivity  implements SwipyRefreshLayout.OnRefreshListener{
    @OnClick(R.id.rl_back) void back(){
        this.finish();
    }
    private int index =0;
    private int top = 0;
    private boolean isRefresh =true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private boolean isEnd = false;
    @Bind(R.id.rl_back) RelativeLayout rl_back;
    private List<SearchBean> searchBeanList = new ArrayList<>();
    @Bind(R.id.rl_search) RelativeLayout rl_search;
    private Intent intent;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    private List<NewsBean.DataEntity> new_list = new ArrayList<>();
    @Bind(R.id.swipyrefreshlayout) SwipyRefreshLayout mSwipyRefreshLayout;//刷新
    @Bind(R.id.list) ListView list;
    private ThreeBoardAdapter adapter;
    private SearchTitleTask task;
    private String msg="";
    @OnItemClick(R.id.list) void list(int pos){
        intent = new Intent(mContext,ThreeWebviewActivity.class);
        intent.putExtra("id",new_list.get(pos).getId()+"");
        intent.putExtra("url",new_list.get(pos).getUrl());
        startActivity(intent);
    }
    private  boolean is_error = false;
    @Override
    protected int getResourcesId() {
        return R.layout.activity_three_search_detail;
    }
    @Override
    protected void init() {
        mSwipyRefreshLayout.setOnRefreshListener(this);
        RippleUtils.rippleNormal(rl_back);
        searchView.showView(rl_search);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
//        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, query);
                /**新三板的搜索*/
                msg = query;
                SearchTitleTask task = new SearchTitleTask(query);
                task.execute();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }

        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                rl_search.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                rl_search.setVisibility(View.VISIBLE);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    }
                });
    }
    private void preRefresh(){
        isFirst= true;
        isEnd = false;
        pageNO =0;
        isRefresh = true;
        index=0;
        top=0;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                searchView.autoShow();
            }
        }
    };
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction==SwipyRefreshLayoutDirection.TOP){
            //下拉刷新
            preRefresh();
            task = new SearchTitleTask(msg);
            task.execute();
        }else {
            index = list.getFirstVisiblePosition();
            View view1 = list.getChildAt(0);
            top = (view1==null)?0:view1.getTop();
            if (isEnd){
                SuperToastUtils.showSuperToast(ThreeSearchDetailActivity.this, 1, R.string.last_page);
                mSwipyRefreshLayout.setRefreshing(false);
            }else {
                pageNO = pageNO+1;
                isRefresh = false;
                task = new SearchTitleTask(msg);
                task.execute();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            finish();
        } else {
            finish();
            super.onBackPressed();
        }
    }
    private void prePage(){
        mSwipyRefreshLayout.setVisibility(View.VISIBLE);
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

    }

    private class SearchTitleTask extends AsyncTask<Void,Void,NewsBean> {
        private String msg;

        public SearchTitleTask(String msg) {
            this.msg = msg;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prePage();
            mSwipyRefreshLayout.setRefreshing(true);
        }
        @Override
        protected NewsBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = AsynOkUtils.doPushPost( "wd",msg,Constant.BASE_URL + Constant.PHONE + Constant.SEARCH_TITLE + pageNO + "/", ThreeSearchDetailActivity.this);
                    if (isRefresh&&new_list.size()!=0){
                        new_list.clear();
                    }
                    if (FastJsonTools.getBean(body, NewsBean.class)!=null&&FastJsonTools.getBean(body, NewsBean.class).getData()!=null){
                        new_list.addAll(FastJsonTools.getBean(body,NewsBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    is_error = true;
                    okHttpException.httpException(e);
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, NewsBean.class);
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(NewsBean mapss) {
            super.onPostExecute(mapss);
            if (mapss!=null){
                if (mapss.getCode()==-1){
                    return;
                }
                if (!is_error&&new_list.size()==0){
                    blankPage();
                }
                lastPage(mapss);
                mSwipyRefreshLayout.setRefreshing(false);
                adapter = new ThreeBoardAdapter(ThreeSearchDetailActivity.this.getApplicationContext(),new_list);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
            }
            isFirst = false;
        }
    }

    private void lastPage(NewsBean map){
        if (map.getCode()==2){
            isEnd = true;
        }
    }
}