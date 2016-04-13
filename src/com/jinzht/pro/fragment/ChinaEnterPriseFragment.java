package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.OnItemClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.activity.ThreeWebviewActivity;
import com.jinzht.pro.adapter.ThreeBoardAdapter;
import com.jinzht.pro.badgeview.interfaces.IAnimationListener;
import com.jinzht.pro.model.NewsBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.*;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/7/16
 * @time 22:06
 */
public class ChinaEnterPriseFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener, IAnimationListener {
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private boolean isEnd = false;
    private List<NewsBean.DataEntity> new_list = new ArrayList<>();
    private ThreeBoardAdapter adapter;
    private TagTask tagTask;
    private boolean loadOver = false;

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_china_enterprise;
    }

    @Override
    protected void onFirstUserVisible() {
        int ss = ThreeBoardFragment.vp_three.getCurrentItem();
        Log.e(TAG, ss + "postion");
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        firstLoad();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!loadOver) {
                            tagTask = new TagTask();
                            tagTask.execute();
                        }
                    }
                });
            }
        }, 1000);

    }

    @OnItemClick(R.id.list)
    void item(int position) {
        if (!UiHelp.isFastClick()) {
            if (new_list != null || new_list.size() != 0) {
                MainActivity.hideMenu();
                Intent intent = new Intent(getActivity(), ThreeWebviewActivity.class);
                intent.putExtra("title", new_list.get(position).getTitle());
                intent.putExtra("url", new_list.get(position).getUrl());
                intent.putExtra("id", new_list.get(position).getId() + "");
                startActivity(intent);
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

    private void preRefresh() {
        index = 0;
        top = 0;
        isFirst = true;
        isEnd = false;
        pageNO = 0;
        isRefresh = true;
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            //����ˢ��
            preRefresh();
            if (!loadOver) {
                tagTask = new TagTask();
                tagTask.execute();
            } else {
                mSwipyRefreshLayout.setRefreshing(false);
            }
        } else {
            index = list.getFirstVisiblePosition();
            View view1 = list.getChildAt(0);
            top = (view1 == null) ? 0 : view1.getTop();
            if (isEnd) {
                UiHelp.canToast(getActivity());
                mSwipyRefreshLayout.setRefreshing(false);
            } else {
                pageNO = pageNO + 1;
                isRefresh = false;
                tagTask = new TagTask();
                tagTask.execute();
            }
        }
    }

    private void prePage() {
        mSwipyRefreshLayout.setVisibility(View.VISIBLE);
//        ll_no_context.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationEnd() {
    }

    private void lastPage(NewsBean map) {
        try {
            if (map.getCode() == 2 && pageNO != 0) {
                SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                isEnd = true;
            }
        } catch (Exception e) {

        }
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

    private class TagTask extends AsyncTask<Void, Void, NewsBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipyRefreshLayout.setRefreshing(true);
        }

        @Override
        protected NewsBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.NEWSLIST +
                            ThreeBoardFragment.list_title.get(ThreeBoardFragment.vp_three.getCurrentItem()).getKey() + "/" + pageNO + "/", getActivity());
                    aCache.put("NewsBean" + ThreeBoardFragment.list_title.get(ThreeBoardFragment.vp_three.getCurrentItem()).getKey() + pageNO, body, 1 * ACache.TIME_DAY);
                    Log.e(TAG, new_list.size() + "ss");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage() + "");
                    return null;
                }
                Log.e(TAG, body);
            } else {
                if (body == null) {
                    return null;
                }
            }
            preLoad(body);
            return FastJsonTools.getBean(body, NewsBean.class);
        }

        @Override
        protected void onPostExecute(NewsBean aVoid) {
            super.onPostExecute(aVoid);
            loadOver(aVoid);
        }
    }

    private void loadOver(NewsBean aVoid) {
        if (aVoid == null) {
            mSwipyRefreshLayout.setRefreshing(false);
        } else if (new_list == null || new_list.size() == 0) {
            mSwipyRefreshLayout.setRefreshing(false);
            lastPage(aVoid);
        } else {
            loadOver = true;
            mSwipyRefreshLayout.setRefreshing(false);
            adapter = new ThreeBoardAdapter(getActivity(), new_list);
            list.setAdapter(adapter);
            list.setSelectionFromTop(index, top);
            adapter.notifyDataSetChanged();
            lastPage(aVoid);
        }
    }

    private void preLoad(String body) {
        if (isRefresh && new_list.size() != 0) {
            new_list.clear();
        }
        if (FastJsonTools.getBean(body, NewsBean.class) != null && FastJsonTools.getBean(body, NewsBean.class).getData() != null) {
            new_list.addAll(FastJsonTools.getBean(body, NewsBean.class).getData());
        }
    }

    private void firstLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String body = null;
//                ACache aCache = ACache.get(getActivity());
                body = aCache.getAsString("NewsBean" + ThreeBoardFragment.list_title.get(ThreeBoardFragment.vp_three.getCurrentItem()).getKey() + pageNO);
                if (body == null) {
                    return;
                }
                preLoad(body);
                final String finalBody = body;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadOver(FastJsonTools.getBean(finalBody, NewsBean.class));
                    }
                });
            }
        }).start();
    }
}