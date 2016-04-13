package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.CoreTeamAdapter;
import com.jinzht.pro.model.CoreTeamBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 核心团队页面，访问网络，加载数据，点击列表条目跳转至详情界面CoreTeamDetailActivity
 *
 * @auther Mr.Jobs
 * @date 2015/7/16
 * @time 14:37
 */

public class CoreTeamActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener {

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    private List<CoreTeamBean.DataEntity> list = new ArrayList<>();

    private boolean isEnd = false;
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNo = 0;// 页码
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//刷新
    private int index = 0;
    private int top = 0;
    @Bind(R.id.list)
    ListView listView;
    private Intent intent;
    private CoreTeamTask task;
    private CoreTeamAdapter adapter;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;// 状态页

    // ListView的条目点击，跳转至具体的核心团队详情页面CoreTeamDetailActivity
    @OnItemClick(R.id.list)
    void on(int postion) {
        if (list.size() != 0) {
            intent = new Intent(CoreTeamActivity.this, CoreTeamDetailActivity.class);
            intent.putExtra("id", list.get(postion).getId());
            intent.putExtra("name", list.get(postion).getName());
            intent.putExtra("postion", list.get(postion).getPosition());
            intent.putExtra("image", list.get(postion).getPhoto());
            intent.putExtra("profile", list.get(postion).getProfile());
            startActivity(intent);
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_core_team;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        title.setText(getResources().getString(R.string.core_team));

        // 加载数据
        CoreTeamTask coreTeamTask = new CoreTeamTask();
        coreTeamTask.execute();

        // 错误页面点击重新加载
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        CoreTeamTask coreTeamTask = new CoreTeamTask();
                        coreTeamTask.execute();
                    }
                });
    }

    // 初始状态
    private void preRefresh() {
        isFirst = true;
        isEnd = false;
        pageNo = 0;
        isRefresh = true;
        top = 0;
        index = 0;
    }

    // 在页面顶端时下来刷新
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            //下拉刷新
            preRefresh();
            task = new CoreTeamTask();
            task.execute();
        } else {
            mSwipyRefreshLayout.setRefreshing(false);
            if (!UiHelp.isFastClick()) {
                UiHelp.canToast(CoreTeamActivity.this);
            }
        }
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
        preRefresh();
        task = new CoreTeamTask();
        task.execute();
    }

    // 访问网络，获取核心团队数据，保存到CoreTeamBean，展示到LIstView中
    private class CoreTeamTask extends AsyncTask<Void, Void, CoreTeamBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipyRefreshLayout.setRefreshing(true);
        }

        @Override
        protected CoreTeamBean doInBackground(Void... voids) {
            String body = null;
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.CORETEAM + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, body);
                    if (isRefresh && list.size() != 0) {
                        list.clear();
                    }
                    if (FastJsonTools.getBean(body, CoreTeamBean.class) != null && FastJsonTools.getBean(body, CoreTeamBean.class).getData() != null) {
                        list.addAll(FastJsonTools.getBean(body, CoreTeamBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return FastJsonTools.getBean(body, CoreTeamBean.class);
        }

        @Override
        protected void onPostExecute(CoreTeamBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (list.size() == 0 && aVoid.getCode() == 2) {
                    blankPage();
                }
                mSwipyRefreshLayout.setRefreshing(false);
                adapter = new CoreTeamAdapter(CoreTeamActivity.this, list);
                listView.setAdapter(adapter);
                listView.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
            }
        }
    }

}