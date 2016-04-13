package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.InvestFinacingDetailsActivity;
import com.jinzht.pro.adapter.MyInvestAdapter;
import com.jinzht.pro.model.MyInvestBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * 我的投融资中我投资的项目
 *
 * @auther Mr.Jobs
 * @date 2015/8/15
 * @time 10:01
 */

public class MyInvestFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {


    private List<MyInvestBean.DataEntity> arrayList = new ArrayList<>();
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private int flag = 0;
    private boolean isEnd = false;
    MyInvestAdapter adapter;
    MyInvestorTask task;

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    // 点击进入各个具体的项目详情
    @OnItemClick(R.id.list)
    void setList(int position) {
        Intent intent = new Intent(mContext, InvestFinacingDetailsActivity.class);
        intent.putExtra("id", arrayList.get(position).getId() + "");
        intent.putExtra("title", arrayList.get(position).getCompany());
        intent.putExtra("companyName", arrayList.get(position).getCompany() + "");
        intent.putExtra("abbrevName", arrayList.get(position).getAbbrevcompany() + "");
        intent.putExtra("image", arrayList.get(position).getImg() + "");
        startActivity(intent);
    }

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_collect_normal;
    }

    @Override
    protected void onFirstUserVisible() {
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        task = new MyInvestorTask();
        task.execute();
        if (arrayList == null || arrayList.size() == 0) {
            mSwipyRefreshLayout.setVisibility(View.GONE);
        } else {
            mSwipyRefreshLayout.setVisibility(View.VISIBLE);
        }
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new MyInvestorTask();
                        task.execute();
                    }
                });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            //����ˢ��
            pageNO = 0;
            isFirst = true;
            isRefresh = true;
            isEnd = false;
            task = new MyInvestorTask();
            task.execute();

        } else {
            isFirst = false;
            index = list.getFirstVisiblePosition();
            View view1 = list.getChildAt(0);
            top = (view1 == null) ? 0 : view1.getTop();
            if (isEnd) {
                SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
            } else {
                pageNO = pageNO + 1;
                isRefresh = false;
                task = new MyInvestorTask();
                task.execute();
            }
        }
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
        task = new MyInvestorTask();
        task.execute();
    }

    // 获取我投资的项目数据并展示
    private class MyInvestorTask extends AsyncTask<Void, Void, MyInvestBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            mSwipyRefreshLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected MyInvestBean doInBackground(Void... voids) {
            String body = null;
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;

            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.MYINVEST + pageNO + "/", getActivity());
                    Log.i("我投资的项目", body);
                    if (isRefresh && arrayList.size() != 0) {
                        arrayList.clear();
                    }
                    if (FastJsonTools.getBean(body, MyInvestBean.class) != null && FastJsonTools.getBean(body, MyInvestBean.class).getData() != null) {
                        arrayList.addAll(FastJsonTools.getBean(body, MyInvestBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                    return null;
                }

            }
            return FastJsonTools.getBean(body, MyInvestBean.class);
        }

        @Override
        protected void onPostExecute(MyInvestBean waitList) {
            super.onPostExecute(waitList);
            mSwipyRefreshLayout.setRefreshing(false);
            if (waitList != null) {
                if (waitList.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (waitList.getCode() == 2 && arrayList.size() == 0) {
                    blankPage();
                }
                lastPage(waitList.getCode());
                mSwipyRefreshLayout.setVisibility(View.VISIBLE);
                adapter = new MyInvestAdapter(mContext, arrayList);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
            }
        }

        private void lastPage(int code) {
            if (code == 2) {
                isEnd = true;
            }
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
}