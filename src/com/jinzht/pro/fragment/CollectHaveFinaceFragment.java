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
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * 我的收藏中已融资
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 21:27
 */
public class CollectHaveFinaceFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    CollectNormalAdapter adapter;
    private CollectFinacedTask task;
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private int flag = 0;
    private boolean isEnd = false;
    private List<CollectWaitFinaceBean.DataEntity> arrayList = new ArrayList<>();

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

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
        task = new CollectFinacedTask();
        task.execute();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new CollectFinacedTask();
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
            task = new CollectFinacedTask();
            task.execute();
        } else {
            isFirst = false;
            index = list.getFirstVisiblePosition();
            View view1 = list.getChildAt(0);
            top = (view1 == null) ? 0 : view1.getTop();
            if (isEnd) {
                UiHelp.ToastMessageShort(mContext, R.string.last_page);
                mSwipyRefreshLayout.setRefreshing(false);
            } else {
                pageNO = pageNO + 1;
                isRefresh = false;
                task = new CollectFinacedTask();
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
        task = new CollectFinacedTask();
        task.execute();
    }

    // 获取收藏的已融资列表数据并展示
    private class CollectFinacedTask extends AsyncTask<Void, Void, CollectWaitFinaceBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            super.onPreExecute();
        }

        @Override
        protected CollectWaitFinaceBean doInBackground(Void... voids) {
            String body = "";
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.COLLECTFINACED + pageNO + "/", getActivity());
                    Log.i("收藏的已融资列表", body);
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
                if (waitList.getCode() == 2) {
                    isEnd = true;
                }
                if (waitList.getCode() == 2 && arrayList.size() == 0) {
                    blankPage();
                }
                adapter = new CollectNormalAdapter(getActivity(), arrayList, 2);
                list.setAdapter(adapter);
                list.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
            }
        }
    }
}