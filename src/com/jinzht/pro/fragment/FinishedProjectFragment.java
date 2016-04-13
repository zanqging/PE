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
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.adapter.FinishFinancingAdapter;
import com.jinzht.pro.model.FinishFinacingBean;
import com.jinzht.pro.model.ProjectWaitBean;
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
public class FinishedProjectFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private boolean isRefresh = true;
    private boolean isFinishRefresh = true;
    private boolean isEnd = false;
    private List<ProjectWaitBean.DataEntity> new_list = new ArrayList<>();
    private FinishFinancingAdapter finishAdapter;
    private FinishTask task;
    private int pageNo = 0;
    private Intent intent;
    private List<FinishFinacingBean.DataEntity> finish_list = new ArrayList<>();

    @OnItemClick(R.id.list)
    void ons(int pos) {
        /**������*/

        MainActivity.hideMenu();
        intent = new Intent(getActivity(), InvestFinacingDetailsActivity.class);
        intent.putExtra("id", finish_list.get(pos).getId() + "");
        intent.putExtra("companyName", finish_list.get(pos).getCompany() + "");
        intent.putExtra("abbrevName", finish_list.get(pos).getAbbrevcompany() + "");
        intent.putExtra("image", finish_list.get(pos).getImg() + "");
        startActivity(intent);
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

    private void refresh() {
        preRefresh();
        /**������*/
        task = new FinishTask();
        task.execute();
    }

    private void preRefresh() {
        index = 0;
        top = 0;
        isEnd = false;
        pageNo = 0;
        isRefresh = true;
    }

    private void updatePosition() {
        index = list.getFirstVisiblePosition();
        View view1 = list.getChildAt(0);
        top = (view1 == null) ? 0 : view1.getTop();
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

    }

    @Override
    public void blankPage() {

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            refresh();
        } else {
            updatePosition();
            if (isEnd) {
                if (!UiHelp.isLongFastClick()) {
                    SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                }
                mSwipyRefreshLayout.setRefreshing(false);
            } else {
                pageNo = pageNo + 1;
                isRefresh = false;
                task = new FinishTask();
                task.execute();
            }
        }
    }


    private class FinishTask extends AsyncTask<Void, Void, FinishFinacingBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipyRefreshLayout.setRefreshing(true);
        }

        @Override
        protected FinishFinacingBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PROJECT_TITLE + "3/" + pageNo + "/", getActivity());
                    Log.e(TAG, body);
                    aCache.put("FinishFinacingBean" + pageNo, body, 1 * ACache.TIME_DAY);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                body = aCache.getAsString("FinishFinacingBean" + pageNo);
                if (body == null) {
                    return null;
                }
            }
            if (isRefresh && finish_list.size() != 0) {
                finish_list.clear();
            }
            if (FastJsonTools.getBean(body, FinishFinacingBean.class) != null && FastJsonTools.getBean(body, FinishFinacingBean.class).getData() != null) {
                finish_list.addAll(FastJsonTools.getBean(body, FinishFinacingBean.class).getData());
            }
            return FastJsonTools.getBean(body, FinishFinacingBean.class);
        }

        @Override
        protected void onPostExecute(FinishFinacingBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getData() == null) {
                    return;
                }
                if (aVoid.getCode() == 2 && pageNo != 0) {
                    isEnd = true;
                }
                /**�������*/
                finishAdapter = new FinishFinancingAdapter(getActivity(), finish_list);
                list.setAdapter(finishAdapter);
                list.setSelectionFromTop(index, top);
                finishAdapter.notifyDataSetChanged();
            }
        }
    }
}