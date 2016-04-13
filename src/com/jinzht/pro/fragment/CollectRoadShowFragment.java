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
import com.jinzht.pro.adapter.CollectNormalAdapter;
import com.jinzht.pro.model.CollectWaitFinaceBean;
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
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * 我的收藏中待融资的项目
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 21:24
 */
public class CollectRoadShowFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    private List<CollectWaitFinaceBean.DataEntity> arrayList = new ArrayList<>();// 收藏的待融资的项目集合
    CollectNormalAdapter adapter;
    private MyRoadTask task;
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private int flag = 0;
    private boolean isEnd = false;// 是否是最后的条目

    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��

    // 点击条目进入对应的项目详情
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
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        task = new MyRoadTask();
        task.execute();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new MyRoadTask();
                        task.execute();
                    }
                });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            pageNO = 0;
            isFirst = true;
            isRefresh = true;
            isEnd = false;
            task = new MyRoadTask();
            task.execute();
        } else {
            isFirst = false;
            index = list.getFirstVisiblePosition();
            View view1 = list.getChildAt(0);
            top = (view1 == null) ? 0 : view1.getTop();
            if (isEnd) {
                SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                mSwipyRefreshLayout.setRefreshing(false);
            } else {
                pageNO = pageNO + 1;
                isRefresh = false;
                task = new MyRoadTask();
                task.execute();
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
        pageNO = 0;
        isFirst = true;
        isRefresh = true;
        isEnd = false;
        task = new MyRoadTask();
        task.execute();
    }

    // 获取收藏的待融资列表数据并展示
    private class MyRoadTask extends AsyncTask<Void, Void, CollectWaitFinaceBean> {
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected CollectWaitFinaceBean doInBackground(Void... voids) {
            String body = null;
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.MYROADSHOW + pageNO + "/", getActivity());
                    Log.i("收藏的待融资列表", body);
                    if (isRefresh && arrayList.size() != 0) {
                        arrayList.clear();
                    }
                    if (FastJsonTools.getBean(body, CollectWaitFinaceBean.class) != null && FastJsonTools.getBean(body, CollectWaitFinaceBean.class).getData() != null) {
                        arrayList.addAll(FastJsonTools.getBean(body, CollectWaitFinaceBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                    return null;
                }
            }
            return FastJsonTools.getBean(body, CollectWaitFinaceBean.class);
        }

        @Override
        protected void onPostExecute(CollectWaitFinaceBean waitList) {
            super.onPostExecute(waitList);
            mSwipyRefreshLayout.setRefreshing(false);
            if (waitList != null) {
                if (waitList.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (arrayList.size() == 0 && waitList.getCode() == 2) {
                    blankPage();
                }
                adapter = new CollectNormalAdapter(getActivity(), arrayList, 0);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);// 设置条目位置
                adapter.notifyDataSetChanged();
            }
        }
    }
}